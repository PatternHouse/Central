#!/bin/bash

set -e

echo
echo "Stage [2] INFO : Entering the tools build directory ... "
pushd ./Build/Tools/Fire > /dev/null 2>&1
echo "Stage [2] INFO : Building the tools ... "
mvn package > /dev/null 2>&1
echo "Stage [2] INFO : Copying the built tools ... "
cp ./target/*.jar ../../Bin
echo "Stage [2] INFO : Exiting out of tools build directory ... "
popd > /dev/null 2>&1
