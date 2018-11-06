ClipIt
======
ClipIt 是基于 Java 的一个剪贴板小工具，不仅限于剪贴板云上传，未来遇到需要的剪贴板功能也会加上。目的主要为自用，啥都强大的功能都没有\~有需要再说。 

主要功能
--------
1. 自动解析剪贴板内容(支持任意文件)并上传到七牛云，返回连接到剪贴板以供粘贴使用，支持自定义前缀后缀等，可用于 MarkDown 编辑。
2. 自动转换 Kindle 支持的文件并推送 .mobi 后缀文件到指定的 Kindle 邮箱。
3. 调用百度 OCR 识别，根据剪贴板图片进行 OCR 识别，返回识别文本到剪贴板以供粘贴使用。

功能配置
---------
1. 云上传：
    1. 注册[七牛云(邀请)](https://portal.qiniu.com/signup?code=3l8nu0r19ocia)/[七牛云(无邀请)](https://portal.qiniu.com/signup)
    2. [获取 accessKey secretKey](https://portal.qiniu.com/user/key)
    3. [创建 bucket，获取 bucket 名称及测试地址](https://portal.qiniu.com/bucket)
    4. 将以上信息填入根目录的 config.properties 中的对应字段，也可根据需要，修改返回的前缀，为空则无前缀
    5. 截图 / 复制一个文件(任意文件)
    6. 快捷键 Ctrl + Alt + V 自动上传  
    7. 自动粘贴/手动粘贴返回的链接
       
2. 图片文字识别（基于百度 OCR API）：
    1. https://console.bce.baidu.com/ai/#/ai/ocr/overview/index 获取 apiId、apiKey、secretKey
    2. 将以上信息填入根目录的 config.properties 中的对应字段
    3. 截图 / 复制图片文件到剪贴板
    4. 快捷键 Ctrl + Alt + O OCR 识别  
    5. 自动粘贴/手动粘贴返回的 OCR 识别文本
    
3.Kindle 推送:  
    1. 配置 config.properties 中的收发邮箱(#sendToKindle下)
    2. Ctrl + V 复制一个 Kindle 支持的文件
    3. 快捷键 Ctrl + Alt + K 自动转换并推送
    
(jar 文件请自行打包)

使用说明
--------
下载：[百度网盘](https://pan.baidu.com/s/1fpbJkI4RQI5HxJ17YXg41A) (提取码 172m)

1. 安装及配置 JDK 环境(自行搜索)  
（注：网盘中已上传 JDK8，只需添加环境变量即可。）
2. 根据“配置及功能”节进行配置
3. 双击运行 clipIt.bat

许可协议
---------
[MIT License](https://github.com/scruel/ClipIt/blob/master/LICENSE)

如不特殊注明，所有模块都以此协议授权使用。若有意愿软件化本项目，欢迎联系。

GitHub: https://github.com/scruel/ClipIt

作者: Scruel

联系邮箱: scruel@vip.qq.com
