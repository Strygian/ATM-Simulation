@echo off
REM ATM Simulation - Quick Run Script
REM This script compiles and runs the ATM system

cd /d C:\atm-simulation

echo Compiling ATM Simulation System...
javac -cp "lib\*" -d bin src\main\java\model\*.java src\main\java\dao\*.java src\main\java\service\*.java src\main\java\ui\*.java src\main\java\util\*.java src\main\java\AppMain.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Compilation FAILED!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Starting ATM Simulation System...
echo.

java -cp "bin;lib\*" AppMain

pause
