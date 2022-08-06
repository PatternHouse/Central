#!/bin/bash

set -e 

echo "openAOD Central Build Script"
echo

sh ./Scripts/stage1.sh
sh ./Scripts/stage2.sh
sh ./Scripts/stage2_cleanup.sh
sh ./Scripts/stage3.sh
sh ./Scripts/stage4.sh

echo
echo "Stage [5] INFO : Running FIRE scipts ... "
echo "Stage [5] INFO : Completed FIRE Build. Check build log in Build/fire.log"

sh ./Scripts/stage5.sh > /dev/null 2>&1
sh ./Scripts/stage6.sh

echo
echo "Build completed. Final build can be found in ./Build/Portal"
