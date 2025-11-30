# Read session_output.txt and save sections as PNG images
# Usage: powershell -ExecutionPolicy Bypass -File .\save_session_screenshots.ps1

$projectDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$inputFile = Join-Path $projectDir 'session_output.txt'
$outDir = Join-Path $projectDir 'screenshots'
if (!(Test-Path $outDir)) { New-Item -ItemType Directory -Path $outDir | Out-Null }

if (!(Test-Path $inputFile)) { Write-Error "session_output.txt not found at $inputFile"; exit 1 }

# Read file lines
$lines = Get-Content $inputFile -Raw -Encoding UTF8 -ErrorAction Stop

# Define markers and filenames (order matters)
$sections = @(
    @{name='main_menu'; marker='WELCOME TO ATM SIMULATION SYSTEM'},
    @{name='login_prompt'; marker='========== LOGIN =========='},
    @{name='operations_menu'; marker='ATM OPERATIONS MENU'},
    @{name='balance_before'; marker='========== BALANCE INQUIRY =========='},
    @{name='deposit_after'; marker='========== CASH DEPOSIT =========='},
    @{name='withdrawal_after'; marker='========== CASH WITHDRAWAL =========='},
    @{name='transactions'; marker='========== TRANSACTION HISTORY =========='}
)

# Find start indices for each marker
$indices = @()
foreach ($s in $sections) {
    $idx = ($lines.IndexOf($s.marker))
    if ($idx -ge 0) { $indices += @{name=$s.name; pos=$idx} }
}

# If IndexOf not available on raw string, fallback to manual search by lines
if ($indices.Count -eq 0) {
    $lineArray = Get-Content $inputFile -Encoding UTF8
    for ($i=0; $i -lt $lineArray.Length; $i++) {
        foreach ($s in $sections) {
            if ($lineArray[$i].Contains($s.marker)) { $indices += @{name=$s.name; pos=$i} }
        }
    }
    $linesArr = $lineArray
} else {
    # Split raw into lines for processing
    $linesArr = $lines -split "\r?\n"
}

if ($indices.Count -eq 0) { Write-Warning "No markers found; creating one full screenshot." }

# Helper to draw text to PNG
function Save-TextImage([string]$text, [string]$path) {
    Add-Type -AssemblyName System.Drawing
    $font = New-Object System.Drawing.Font('Consolas',11)
    $lines = $text -split "\r?\n"
    $maxLen = ($lines | ForEach-Object { $_.Length } | Measure-Object -Maximum).Maximum
    if (-not $maxLen) { $maxLen = 40 }
    $charWidth = 7 # approx, depends on font/size
    $lineHeight = [int]([Math]::Ceiling($font.GetHeight() + 6))
    $bmpWidth = [int]($maxLen * $charWidth + 40)
    $bmpHeight = [int]($lines.Length * $lineHeight + 40)
    $bmp = New-Object System.Drawing.Bitmap $bmpWidth, $bmpHeight
    $g = [System.Drawing.Graphics]::FromImage($bmp)
    $g.Clear([System.Drawing.Color]::White)
    $brush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::Black)
    $x = 10
    $y = 10
    foreach ($ln in $lines) {
        $g.DrawString($ln, $font, $brush, $x, $y)
        $y += $lineHeight
    }
    $bmp.Save($path, [System.Drawing.Imaging.ImageFormat]::Png)
    $g.Dispose(); $bmp.Dispose(); $brush.Dispose(); $font.Dispose()
}

# If we found markers, capture slices around each marker (a few lines before/after)
if ($indices.Count -gt 0) {
    $indices = $indices | Sort-Object pos
    for ($i=0; $i -lt $indices.Count; $i++) {
        $start = [int]$indices[$i].pos
        $end = if ($i -lt $indices.Count-1) { [int]$indices[$i+1].pos - 1 } else { $linesArr.Length - 1 }
        $before = [Math]::Max(0, $start - 3)
        $after = [Math]::Min($linesArr.Length - 1, $end + 3)
        $slice = $linesArr[$before..$after] -join "`r`n"
        $filename = Join-Path $outDir ("{0}.png" -f $indices[$i].name)
        Save-TextImage $slice $filename
        Write-Host "Saved: $filename"
    }
} else {
    # No markers: save whole file
    $fullname = Join-Path $outDir 'session_full.png'
    Save-TextImage ($linesArr -join "`r`n") $fullname
    Write-Host "Saved full session: $fullname"
}

Write-Host "Done. Screenshots are in: $outDir"
