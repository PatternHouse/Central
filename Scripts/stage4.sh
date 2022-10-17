#!/bin/bash

set -e

echo
echo "Stage [4] INFO : Fetching source code from GitHub repositories ..."
pushd ./Build/Sources/Code > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/Python-PatternHouse.git > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/Java-PatternHouse.git > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/C-PatternHouse.git > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/CPlusPlus-PatternHouse.git > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/CSharp-PatternHouse.git > /dev/null 2>&1
git clone --depth 1 https://github.com/openAOD/JavaScript-PatternHouse.git > /dev/null 2>&1
echo "Stage [4] INFO : Formatting directory names ..."
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

popd > /dev/null 2>&1
