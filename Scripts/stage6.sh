#!/bin/bash

set -e

echo
echo "Stage [6] INFO : Cleaning unnecessary source and binary files ... "
pushd ./Build > /dev/null 2>&1
rm -rf ./Assets
rm -rf ./Sources
rm -rf ./Templates
rm -rf ./Bin
rm -rf ./cdn-o-o
popd > /dev/null 2>&1
