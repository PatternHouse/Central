#!/bin/bash

set -e 

echo "openAOD Central Build Script"
echo

sh ./Scripts/stage1.sh
sh ./Scripts/stage2.sh
sh ./Scripts/stage2_cleanup.sh
sh ./Scripts/stage3.sh
sh ./Scripts/stage4.sh
sh ./Scripts/stage5.sh
sh ./Scripts/stage6.sh

echo
echo "Build completed. Final build can be found in ./Build/Portal"