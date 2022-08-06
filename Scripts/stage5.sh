#!/bin/bash

set -e

pushd ./Build > /dev/null 2>&1
java -jar ./Bin/Fire-1.0.jar
popd > /dev/null 2>&1
