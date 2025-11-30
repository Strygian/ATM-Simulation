@echo off
cd /d "%~dp0"
rem run.bat if you have it, otherwise run the AppMain class directly
if exist run.bat (
  .\run.bat < demo_inputs.txt > session_output.txt
) else (
  java -cp "bin;lib\*" AppMain < demo_inputs.txt > session_output.txt
)
echo Session saved to session_output.txt
pause
