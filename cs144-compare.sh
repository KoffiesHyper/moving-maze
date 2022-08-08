#!/bin/bash

function myecho {
    echo -e "\e[32m$1\e[0m"
}

myecho "cs144-compare.sh (test case $1)"

# Exit the script when an error is encountered.
set -e

# Delete all .class files, so that compilation is always from the latest version of the source code
rm -rf *.class

# Compile all .java files
myecho "Compiling..."
javac -encoding utf8 *.java
myecho "Compiled successfully."

myecho "Start of output comparison (your program's output is on the left, the correct output is on the right)"

# Run the main file with the arguments of the provided test case, and compare its output with the true output
java MovingMaze boards/board$1.txt text < moves/moves$1.txt | diff -Z -s --strip-trailing-cr --label "Your output" - outputs/output$1.txt
