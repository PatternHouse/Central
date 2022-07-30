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
echo "Stage [4] INFO : Formatting directory names ..."
mv ./Python-PatternHouse ./python
mv ./Java-PatternHouse ./java
mv ./C-PatternHouse ./c
mv ./CPlusPlus-PatternHouse ./cpp
mv ./CSharp-PatternHouse ./cs

mv "./python/Alphabetic Patterns" ./python/alphabetic
mv "./python/Numeric Patterns" ./python/numeric
mv "./python/Pyramid Patterns" ./python/pyramid
mv "./python/Series Patterns" ./python/series
mv "./python/Spiral Patterns" ./python/spiral
mv "./python/String Patterns" ./python/string
mv "./python/Symbol Patterns" ./python/symbol
mv "./python/Wave Patterns" ./python/wave

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

popd > /dev/null 2>&1
