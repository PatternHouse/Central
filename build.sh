#!/bin/bash

set -e 

echo "openAOD PatternHouse DB Build Script"
echo

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

echo
echo "Stage [2] INFO : Entering the tools build directory ... "
pushd ./Build/Tools/Fire > /dev/null 2>&1
echo "Stage [2] INFO : Building the tools ... "
mvn package > /dev/null 2>&1
echo "Stage [2] INFO : Copying the built tools ... "
cp ./target/*.jar ../../Bin 
echo "Stage [2] INFO : Exiting out of tools build directory ... "
popd > /dev/null 2>&1
echo "Stage [2] INFO : Cleaning build residues ... "
pushd ./Build/ > /dev/null 2>&1
rm -rf ./Tools/ 
popd > /dev/null 2>&1

echo
echo "Stage [3] INFO : Populating static websites ... "
pushd ./Build > /dev/null 2>&1
cp -R ./Sources/Static/* ./Portal/
echo "Stage [3] INFO : Populating static website assets ... "
cp -R ./Assets/* ./Portal/
popd > /dev/null 2>&1

echo 
echo "Stage [4] INFO : Fetching source code from GitHub repositories ..."
pushd ./Build/Sources/Code > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/Python-PatternHouse.git > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/Java-PatternHouse.git > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/C-PatternHouse.git > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/CPlusPlus-PatternHouse.git > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/CSharp-PatternHouse.git > /dev/null 2>&1
echo "Stage [4] INFO : Formatting directory names ..."
mv ./Python-PatternHouse ./python
mv ./Java-PatternHouse ./java
mv ./C-PatternHouse ./c
mv ./CPlusPlus-PatternHouse ./cpp
mv ./CSharp-PatternHouse ./cs
popd > /dev/null 2>&1

echo
echo "Stage [5] INFO : Running FIRE scipts ... "
pushd ./Build > /dev/null 2>&1
java -jar ./Bin/Fire-1.0.jar > ./fire.log 2>&1
echo "Stage [5] INFO : Completed FIRE Build. Check build log in Build/fire.log"
popd > /dev/null 2>&1

echo
echo "Stage [6] INFO : Cleaning unnecessary source and binary files ... "
pushd ./Build > /dev/null 2>&1
rm -rf ./Assets
rm -rf ./Sources
rm -rf ./Templates
rm -rf ./Bin
popd > /dev/null 2>&1

echo
echo "Build completed. Final build can be found in ./Build/Portal"