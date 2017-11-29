ClipIt
======
ClipIt 是基于 Java 和 AutoHotKey 的一个剪贴板小工具，不仅限于剪贴板云上传，未来遇到需要的剪贴板功能也会加上。目的主要为自用，啥都强大的功能都没有\~有需要再说。 

主要功能
--------
1. 自动解析剪贴板内容(支持任意文件)并上传到七牛云，返回连接到剪贴板以供粘贴使用，支持自定义前缀后缀等，可用于 MarkDown 编辑。
2. 自动转换 Kindle (.azw(3)后缀)文件并将生成后的 .mobi 后缀文件发送到指定的 Kindle 邮箱。


使用说明
--------
1. 安装及配置 JDK 环境(自行搜索)
2. 运行 clipIt-32.exe (clipIt-64.exe)
3. 功能分类步骤：  
    功能1(云上传)：  
    1. https://portal.qiniu.com/user/key 获取 accessKey secretKey
    2. https://portal.qiniu.com/bucket 创建 bucket，获取 bucket 名称及测试地址
    3. 将以上信息填入根目录的 config.properties 中的对应字段，也可根据需要，修改返回的前缀，为空则无前缀
    5. 截图 / 复制一个文件(任意文件)
    6. 快捷键 Ctrl + Alt + V 自动上传  
    7. 自动粘贴/手动粘贴返回的链接
          
    功能2(Kindle):  
    1. 配置 config.properties 中的邮箱设置(#sendToKindle下)
    2. Ctrl + V 复制一个 Kindle(.azw(3)后缀) 文件
    3. 快捷键 Ctrl + Alt + K 自动转换并上传


(jar 文件请自行打包)
完整可运行：
https://pan.baidu.com/s/1mikNAUc