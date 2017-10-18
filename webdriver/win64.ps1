$tmpDir = New-TemporaryFile | %{ rm $_; mkdir $_ }

$driversDir = "$PSScriptRoot/win32"
Remove-Item -Force -Recurse $driversDir
New-Item -ItemType Directory $driversDir | Out-Null

Write-Host "* Firefox driver"
$response = Invoke-WebRequest -UseBasicParsing -MaximumRedirection 0 -ErrorAction Ignore https://github.com/mozilla/geckodriver/releases/latest
$firefoxVersion = $response.Headers.Location | Select-String -Pattern 'tag\/(.+)$' | % { $_.matches.groups[1].Value }
Write-Host "Downloading latest version: $firefoxVersion"
Invoke-WebRequest -Uri https://github.com/mozilla/geckodriver/releases/download/$firefoxVersion/geckodriver-$firefoxVersion-win64.zip -OutFile $tmpDir/geckodriver-$firefoxVersion-win64.zip
Expand-Archive -Path $tmpDir/geckodriver-$firefoxVersion-win64.zip -DestinationPath $driversDir


Write-Host "* Chrome driver"
$response = Invoke-WebRequest -ContentType text/html -UseBasicParsing -MaximumRedirection 0 -ErrorAction Ignore https://chromedriver.storage.googleapis.com/LATEST_RELEASE
$chromeVersion = [System.Text.Encoding]::ASCII.GetString($response.Content)
$chromeVersion = $chromeVersion.Substring(0, $chromeVersion.Length -1)
Write-Host "Downloading latest version: $chromeVersion"
Invoke-WebRequest -Uri https://chromedriver.storage.googleapis.com/$chromeVersion/chromedriver_win32.zip -OutFile $tmpDir/chromedriver_mac64.zip
Expand-Archive -Path $tmpDir/chromedriver_mac64.zip -DestinationPath $driversDir

Write-Host "* Edge driver"
$response = Invoke-WebRequest -UseBasicParsing -MaximumRedirection 0 -ErrorAction Ignore https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/
$link = ($response.Links | Where {$_.'aria-label' -like "WebDriver for release number*" -and $_.href -like "*MicrosoftWebDriver.exe"})[0].href
Invoke-WebRequest -Uri $link -OutFile $driversDir/MicrosoftWebDriver.exe

Remove-Item -Force -Recurse $tmpDir