#!/bin/bash

function greenecho {
    echo -e "\e[32m$1\e[0m"
}

function blueecho {
    echo -e "\e[34m$1\e[0m"
}

greenecho "cs144-testall.sh"

# Exit the script when an error is encountered in removing old class files and in compilation
set -e

# Delete all .class files, so that compilation is always from the latest version of the source code
rm -rf *.class

# Compile all .java files
greenecho "Compiling..."
javac *.java
greenecho "Compiled successfully."

# Don't exit if a single test case has issues
set +e

function runtest {

    blueecho "Test #$1:"

    # Run the main file with the arguments of the indicated test case, compare it to the correct output
    java MovingMaze boards/board$1.txt text < moves/moves$1.txt | diff --brief -s --strip-trailing-cr --label "Your output" - outputs/output$1.txt
}

greenecho "======================"
greenecho "First hand-in"
for i in $(seq 20); do runtest $i; done

greenecho "======================"
greenecho "Second hand-in"
for i in $(seq 21 23); do runtest $i; done

greenecho "======================"
greenecho "Third hand-in"
for i in $(seq 24 26); do runtest $i; done

