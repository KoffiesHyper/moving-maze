javac -encoding utf8 *.java
function runtest {
    java MovingMaze boards/board$1.txt text < moves/moves$1.txt > $1.txt
}
for i in $(seq 23); do runtest $i; done