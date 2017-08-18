## markdownClipUpload
用途：自动解析剪贴板内容并上传到七牛云，返回连接以便快速使用连接。  
  
并未加入界面，并未加入jar自带快捷键，并未加入其它上传方式支持。并未。。。总之，暂时搭配AutoHotKey自用，啥都没有\~有需要再说。。。  
上传内容支持图片也支持文件\~\~\~  

使用步骤：
1. 配置jdk环境（自行搜索）
1. https://portal.qiniu.com/user/key 获取accessKey secretKey
2. https://portal.qiniu.com/bucket 创建bucket，获取bucket名称及测试地址
3. 将以上信息探入config.properties中，也可以根据需要修改返回的前缀，为空则无前缀
4. 运行clipboardUpload.exe
5. 复制一张图片
6. 默认Ctrl + Alt + V 置剪贴板并粘贴获取到的地址（可以对ahk编辑然后自己去用AutoHotKey）

只是使用的话，三个文件即可：
1. config.properties
2. clipboardUpload.exe
3. clipboardUpload-1.0-SNAPSHOT.jar（mvn clean package）

