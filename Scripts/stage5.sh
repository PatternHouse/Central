#!/bin/bash

set -e

echo
echo "Stage [5] INFO : Running FIRE scipts ... "
pushd ./Build > /dev/null 2>&1
java -jar ./Bin/Fire-1.0.jar > ./fire.log 2>&1
echo "Stage [5] INFO : Completed FIRE Build. Check build log in Build/fire.log"
popd > /dev/null 2>&1
