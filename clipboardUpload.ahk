/*
github: https://github.com/scruel/markdownClipUpload
*/


^!V::
		RunWait, %comspec% /c "java -jar clipboardUpload-1.0-SNAPSHOT.jar" ,, hide
	Send ^V
return



