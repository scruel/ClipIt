## ClipIt
更名为ClipIt，不仅限于云上传，以后遇到需要的剪贴板功能都会加上。 目的自用，啥都强大的功能都没有\~有需要再说。 

用途：
1. 自动解析剪贴板内容(支持任意文件)并上传到七牛云，返回连接以便快速使用连接。  
2. 自动转换Kindle文件并将生成后的mobi文件发送到Kindle邮箱


使用步骤：  
1. 首先需配置 JDK 环境（自行配置）
2. 运行 clipboardUpload.exe
3. 目前实现的功能：
    1. 自动解析剪贴板内容(支持任意文件)并上传到七牛云，返回连接以便快速使用连接：
        1. https://portal.qiniu.com/user/key 获取 accessKey secretKey
        2. https://portal.qiniu.com/bucket 创建 bucket，获取 bucket 名称及测试地址
        3. 将以上信息填入根目录的 config.properties 中的对应字段，也可根据需要，修改返回的前缀，为空则无前缀
        5. 截图 / 复制一个文件(任意文件)
        6. 快捷键 Ctrl + Alt + V 自动上传
    2. 自动转换Kindle文件并将生成后的mobi文件发送到指定的Kindle邮箱
        1. 配置 config.properties 中的邮箱设置(#sendToKindle下)
        2. Ctrl + V 复制一个kindle文件
        3. 快捷键 Ctrl + Alt + K 自动上传


(jar文件请自行打包)
完整可运行：
https://pan.baidu.com/s/1mikNAUc