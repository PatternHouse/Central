#!/bin/bash

set -e

echo "Stage [1] INFO : Preparing Build Directories ..."

if [ -d "./Build" ]; then
    echo "Stage [1] WARN : Build directory already exists. Possible residue from previous build. Deleting the directory..."
    rm -rf ./Build
fi

mkdir ./Build
mkdir ./Build/Assets
mkdir ./Build/Sources
mkdir ./Build/Templates
mkdir ./Build/Tools
mkdir ./Build/Bin
mkdir ./Build/Portal
mkdir ./Build/Portal/portal

echo "Stage [1] INFO : Populating build directories ... "
cp -R ./Assets/* ./Build/Assets
cp -R ./Sources/* ./Build/Sources
mkdir ./Build/Sources/Code
cp -R ./Templates/* ./Build/Templates
cp -R ./Tools/* ./Build/Tools
