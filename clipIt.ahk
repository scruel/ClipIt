/*
github: https://github.com/scruel/ClipIt
by scruel
*/

^!V::
	RunWait, %comspec% /c "java -jar clipIt.jar upload" ,, hide
return

^!K::
	RunWait, %comspec% /c "java -jar clipIt.jar sendtokindle" ,, hide
return
