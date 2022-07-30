#!/bin/bash

set -e


echo "Stage [2] INFO : Cleaning build residues ... "
pushd ./Build/ > /dev/null 2>&1
rm -rf ./Tools/
popd > /dev/null 2>&1
