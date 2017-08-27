@echo off
cd temp
CLS

for %%i in (*.azw3) do (
	..\kindleunpack -i "%%i"
	MOVE "%%~ni\mobi8\%%~ni.epub" "."
	RD /S /Q "%%~ni"
)
for %%i in (*.azw) do (
	..\kindleunpack -i "%%i"
	MOVE "%%~ni\mobi8\%%~ni.epub" "."
	RD /S /Q "%%~ni"
)
for %%i in (*.epub) do (
	..\kindlegen "%%i"
	REM ..\kindlestrip.exe "%%~ni.mobi" "..\result\%%~ni.mobi"
	..\kindlestrip.exe "%%~ni.mobi" "%%~ni.mobi"
)
exit
