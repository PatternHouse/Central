#!/bin/bash

set -e

echo "openAOD Central Build Script"
echo "Copyright (C) 2022, MIT License"
echo

TARGET=""
DEVELOPMENT_FLAG="false"

fetch_source_code()
{
    git clone --depth 1 https://github.com/openAOD/Python-PatternHouse.git >/dev/null 2>&1
    git clone --depth 1 https://github.com/openAOD/Java-PatternHouse.git >/dev/null 2>&1
    git clone --depth 1 https://github.com/openAOD/C-PatternHouse.git >/dev/null 2>&1
    git clone --depth 1 https://github.com/openAOD/CPlusPlus-PatternHouse.git >/dev/null 2>&1
    git clone --depth 1 https://github.com/openAOD/CSharp-PatternHouse.git >/dev/null 2>&1
    git clone --depth 1 https://github.com/openAOD/JavaScript-PatternHouse.git >/dev/null 2>&1

    mv ./Python-PatternHouse ./py
    mv ./Java-PatternHouse ./java
    mv ./C-PatternHouse ./c
    mv ./CPlusPlus-PatternHouse ./cpp
    mv ./CSharp-PatternHouse ./cs
    mv ./JavaScript-PatternHouse ./js

    mv "./py/Alphabetic Patterns" ./py/alphabetic
    mv "./py/Numeric Patterns" ./py/numeric
    mv "./py/Pyramid Patterns" ./py/pyramid
    mv "./py/Series Patterns" ./py/series
    mv "./py/Spiral Patterns" ./py/spiral
    mv "./py/String Patterns" ./py/string
    mv "./py/Symbol Patterns" ./py/symbol
    mv "./py/Wave Patterns" ./py/wave

    mv "./java/Alphabetic Patterns" ./java/alphabetic
    mv "./java/Numeric Patterns" ./java/numeric
    mv "./java/Pyramid Patterns" ./java/pyramid
    mv "./java/Series Patterns" ./java/series
    mv "./java/Spiral Patterns" ./java/spiral
    mv "./java/String Patterns" ./java/string
    mv "./java/Symbol Patterns" ./java/symbol
    mv "./java/Wave Patterns" ./java/wave

    mv "./c/Alphabetic Patterns" ./c/alphabetic
    mv "./c/Numeric Patterns" ./c/numeric
    mv "./c/Pyramid Patterns" ./c/pyramid
    mv "./c/Series Patterns" ./c/series
    mv "./c/Spiral Patterns" ./c/spiral
    mv "./c/String Patterns" ./c/string
    mv "./c/Symbol Patterns" ./c/symbol
    mv "./c/Wave Patterns" ./c/wave

    mv "./cpp/Alphabetic Patterns" ./cpp/alphabetic
    mv "./cpp/Numeric Patterns" ./cpp/numeric
    mv "./cpp/Pyramid Patterns" ./cpp/pyramid
    mv "./cpp/Series Patterns" ./cpp/series
    mv "./cpp/Spiral Patterns" ./cpp/spiral
    mv "./cpp/String Patterns" ./cpp/string
    mv "./cpp/Symbol Patterns" ./cpp/symbol
    mv "./cpp/Wave Patterns" ./cpp/wave

    mv "./cs/Alphabetic Patterns" ./cs/alphabetic
    mv "./cs/Numeric Patterns" ./cs/numeric
    mv "./cs/Pyramid Patterns" ./cs/pyramid
    mv "./cs/Series Patterns" ./cs/series
    mv "./cs/Spiral Patterns" ./cs/spiral
    mv "./cs/String Patterns" ./cs/string
    mv "./cs/Symbol Patterns" ./cs/symbol
    mv "./cs/Wave Patterns" ./cs/wave

    mv "./js/Alphabetic Patterns" ./js/alphabetic
    mv "./js/Numeric Patterns" ./js/numeric
    mv "./js/Pyramid Patterns" ./js/pyramid
    mv "./js/Series Patterns" ./js/series
    mv "./js/Spiral Patterns" ./js/spiral
    mv "./js/String Patterns" ./js/string
    mv "./js/Symbol Patterns" ./js/symbol
    mv "./js/Wave Patterns" ./js/wave
}

mk_clean_build_dir()
{
    if [ -d "./Build" ]; then
        echo "[WARN] Build directory already exists. Possible residue from previous build. Deleting the directory..."
        rm -rf ./Build
    fi

    mkdir ./Build
}

build_main()
{
    echo "[INFO] Building target 'main' ..."
    mkdir ./Build/main/
    cp -R ./Sources/StaticWeb/* ./Build/main/
    
    if [[ "$DEVELOPMENT_FLAG" = "true" ]]; then
        echo "[INFO] Applying development patches ... "
        patch -u ./Build/main/index.html -i ./Patches/dev_static_web.patch >/dev/null
    fi

    echo "[INFO] Target 'main' built."
    echo
}

build_webui()
{
    echo "[INFO] Building target 'webui' ..."

    mkdir ./Build/patternhouse/
    mkdir ./Build/assets/
    mkdir ./Build/Templates/

    echo "[INFO] Preparing static assets ... "
    cp -R ./Assets/* ./Build/patternhouse
    cp -R ./Templates/* ./Build/Templates
    cp ./Sources/StaticPortal/index.html ./Build/patternhouse/

    echo "[INFO] Copying tools to build directory ... "
    cp -R ./Tools ./Build

    echo "[INFO] Compiling the tools ... "
    pushd ./Build/Tools/Fire >/dev/null
    mvn package >/dev/null
    popd >/dev/null

    echo "[INFO] Copying tools binaries ... "
    mkdir ./Build/bin
    cp ./Build/Tools/Fire/target/*.jar ./Build/bin/

    echo "[INFO] Fetching CDN assets ... "
    pushd ./Build >/dev/null
    git clone --depth 1 https://github.com/openAOD/cdn-o-o.git >/dev/null 2>&1
    mkdir ./assets/patterns
    cp -R ./cdn-o-o/assets/img/patterns/* ./assets/patterns
    mkdir ./patternhouse/patterns/
    cp -R ./assets/patterns/* ./patternhouse/patterns/
    rm -rf ./cdn-o-o
    popd >/dev/null

    echo "[INFO] Fetching source code ..."
    mkdir ./Build/code
    pushd ./Build/code >/dev/null
    fetch_source_code
    popd >/dev/null

    if [[ "$DEVELOPMENT_FLAG" = "true" ]]; then
        echo "[INFO] Applying development patches ..."
        mkdir ./Build/Patches
        cp -R ./Patches/* ./Build/Patches/
        pushd ./Build >/dev/null
        patch -u ./patternhouse/index.html -i ./Patches/dev_static_portal.patch >/dev/null
        patch -u ./patternhouse/patternhouse-webui/css/nav_display.css -i ./Patches/dev_preview_css.patch >/dev/null
        patch -u ./Templates/PortalDisplay/Template.html -i ./Patches/dev_portal_template.patch >/dev/null
        patch -u ./Templates/SourceDisplay/Template.html -i ./Patches/dev_source_template.patch >/dev/null
        rm -rf ./Patches
        popd >/dev/null
    fi

    echo "[INFO] Running the build scripts ... "
    pushd ./Build >/dev/null
    mkdir ./patternhouse/portal
    java -jar ./bin/Fire-1.0.jar > fire.log
    popd >/dev/null

    if [[ "$DEVELOPMENT_FLAG" = "false" ]]; then
        echo "[INFO] Cleaning unnecessary files ..."
        rm -rf ./Build/{code,assets,bin,Templates,tools}
        rm -rf ./Build/fire.log
    fi

    echo "[INFO] Target 'webui' is built."
    echo
}

build()
{
    if [[ "$TARGET" = "" ]]; then
        exit 0
    fi

    echo "------------------------------------------------"
    echo "Selected Target(s): $TARGET"
    echo "Development Mode: $DEVELOPMENT_FLAG"
    echo "------------------------------------------------"
    echo

    echo "[INFO] Preparing the Build directory ... "
    mk_clean_build_dir

    if [[ "$TARGET" = *"webui;"* ]]; then
        build_webui
    fi

    if [[ "$TARGET" = *"main;"* ]]; then
        build_main
    fi
        
}

if [[ "$1" = "webui" ]]; then
    echo "WebUI"
    TARGET="webui;"

elif [[ "$1" = "webui-dev" ]]; then
    echo "WebUI Development Build"
    TARGET="webui;"
    DEVELOPMENT_FLAG="true"

elif [[ "$1" = "main" ]]; then
    echo "Main"
    TARGET="main;"

elif [[ "$1" = "main-dev" ]]; then
    echo "Main Development Build"
    TARGET="main;"
    DEVELOPMENT_FLAG="true"

elif [[ "$1" = "all" ]]; then
    echo "All Targets"
    TARGET="main;webui;"

elif [[ "$1" = "all-dev" ]]; then
    echo "All Targets -- Development Build"
    TARGET="main;webui;"
    DEVELOPMENT_FLAG="true"

else
    echo "Invalid/Unspecified Build Target"
    exit -1

fi

build