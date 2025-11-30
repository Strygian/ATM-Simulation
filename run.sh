#!/bin/bash
# ATM Simulation - Quick Run Script for Unix/Linux/Mac
# This script compiles and runs the ATM system

cd "C:\atm-simulation" || cd "$(dirname "$0")" || exit 1

echo "Compiling ATM Simulation System..."
javac -cp "lib/*" -d bin src/main/java/model/*.java src/main/java/dao/*.java src/main/java/service/*.java src/main/java/ui/*.java src/main/java/util/*.java src/main/java/AppMain.java

if [ $? -ne 0 ]; then
    echo ""
    echo "Compilation FAILED!"
    exit 1
fi

echo "Compilation successful!"
echo ""
echo "Starting ATM Simulation System..."
echo ""

java -cp "bin:lib/*" AppMain
