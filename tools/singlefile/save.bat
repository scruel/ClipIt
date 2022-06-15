
cd cli
CLS

REM single-file https://www.wikipedia.org "wikipedia.html" --back-end=webdriver-gecko --browser-headless=false --browser-args "[\"-profile C:\\Users\\scruel\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\ynotzi2h.default-release\"]"
single-file %1 --filename-template="{url-href-digest-sha-1} - {page-title}.html" --back-end=webdriver-gecko  --browser-headless=false

pause
