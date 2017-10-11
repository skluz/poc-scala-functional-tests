#!/bin/bash

tmpDir="$(pwd)/tmp"
rm -rf $tmpDir
mkdir $tmpDir

driversDir="$(pwd)/drivers"
rm -rf $driversDir
mkdir $driversDir

echo '** Downloading firefox driver'
firefoxVersion=$(curl -s https://github.com/mozilla/geckodriver/releases/latest | sed 's/.*tag\/\(.*\)\".*/\1/')
echo "Latest version: ${firefoxVersion}"
wget -q --show-progress -O $tmpDir/geckodriver.tar.gz "https://github.com/mozilla/geckodriver/releases/download/${firefoxVersion}/geckodriver-${firefoxVersion}-linux64.tar.gz"
tar -zxvf $tmpDir/geckodriver.tar.gz -C $driversDir

echo '** Downloading chrome driver'
chromeVersion=$(curl -s https://chromedriver.storage.googleapis.com/LATEST_RELEASE)
echo "Latest version: ${chromeVersion}"
wget -q --show-progress -O $tmpDir/chromedriver.zip "https://chromedriver.storage.googleapis.com/${chromeVersion}/chromedriver_linux64.zip"
unzip $tmpDir/chromedriver.zip -d $driversDir


rm -rf $tmpDir