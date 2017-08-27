/*
github: https://github.com/scruel/markdownClipUpload
by scruel
*/

^!V::
	RunWait, %comspec% /c "java -jar clipIt.jar upload" ,, hide
return

^!K::
	RunWait, %comspec% /c "java -jar clipIt.jar sendtokindle" ,, hide
return
