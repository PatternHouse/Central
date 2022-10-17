#!/bin/bash

echo "openAOD PatternHouse Testing Script"

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

EXIT_CODE='0'

compile_c_source()
{
    FILE=$1
    NAME="${FILE%%.*}"
    OUT="$NAME.out"

    if gcc $FILE -o $OUT -lm >/dev/null 2>&1; then
        echo -e "   $GREEN[PASS]$NC $NAME"
    else
        echo -e "   $RED[FAIL]$NC $NAME"
        EXIT_CODE='1'
    fi
}

compile_cpp_source()
{
    FILE=$1
    NAME="${FILE%%.*}"
    OUT="$NAME.out"

    if g++ $FILE -o $OUT -lm >/dev/null 2>&1; then
        echo -e "   $GREEN[PASS]$NC $NAME"
    else
        echo -e "   $RED[FAIL]$NC $NAME"
        EXIT_CODE='1'
    fi
}

if [ "$1" = "c" ]; then
 
    echo "Starting Tests ... "

    for d in */ ; do
        cd "$d"
        for f in $(ls | grep .c) ; do
            compile_c_source $f
        done
        cd ..
    done

    exit $EXIT_CODE
fi
