$tmpDir = New-TemporaryFile | %{ rm $_; mkdir $_ }

$driversDir = "$PSScriptRoot/win"
Remove-Item -Force -Recurse $driversDir -ErrorAction Ignore
New-Item -ItemType Directory $driversDir | Out-Null

Write-Host "* Gecko driver"
$response = Invoke-WebRequest -UseBasicParsing -MaximumRedirection 0 -ErrorAction Ignore https://github.com/mozilla/geckodriver/releases/latest
$geckoVersion = $response.Headers.Location | Select-String -Pattern 'tag\/(.+)$' | % { $_.matches.groups[1].Value }
Write-Host "Downloading latest version: $geckoVersion"
Invoke-WebRequest -Uri https://github.com/mozilla/geckodriver/releases/download/$geckoVersion/geckodriver-$geckoVersion-win32.zip -OutFile $tmpDir/geckodriver-$geckoVersion-win32.zip
Expand-Archive -Path $tmpDir/geckodriver-$geckoVersion-win32.zip -DestinationPath $driversDir

Write-Host "* Chrome driver"
$response = Invoke-WebRequest -ContentType text/html -UseBasicParsing -MaximumRedirection 0 -ErrorAction Ignore https://chromedriver.storage.googleapis.com/LATEST_RELEASE
$chromeVersion = [System.Text.Encoding]::ASCII.GetString($response.Content)
$chromeVersion = $chromeVersion.Substring(0, $chromeVersion.Length -1)
Write-Host "Downloading latest version: $chromeVersion"
Invoke-WebRequest -Uri https://chromedriver.storage.googleapis.com/$chromeVersion/chromedriver_win32.zip -OutFile $tmpDir/chromedriver_win32.zip
Expand-Archive -Path $tmpDir/chromedriver_win32.zip -DestinationPath $driversDir

Write-Host "* Edge driver"
$response = Invoke-WebRequest -UseBasicParsing -MaximumRedirection 0 -ErrorAction Ignore https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/
$latest = ($response.Links | Where {$_.'aria-label' -like "WebDriver for release number*" -and $_.href -like "*MicrosoftWebDriver.exe"})[0]
$edgeVersion = [regex]::Match($latest.'aria-label', '.*?([\d]+)').captures.groups[1].value
Write-Host "Downloading latest version: $edgeVersion"
Invoke-WebRequest -Uri $latest.href -OutFile $driversDir/MicrosoftWebDriver.exe

Remove-Item -Force -Recurse $tmpDir