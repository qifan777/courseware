### 介绍

一个可以上传课件,卖课件,并且在线预览课件(ppt,doc,pdf等)的小程序。接入了微信支付,小程序登录。代码量少，涉及的内容较多,适合新手学习。

### 涉及技术

##### 后端:

 `springboot`,`mybatis-plus`, `shiro+JWT`,`mapstruct`,`阿里云OSS`,`docker`，`redis`,`微信支付`

##### 小程序:

 `uni-app`,`uView UI`,`vuex`

##### 后台管理:

 `vue-cl`,`vuex`,`vue-router`,`element ui`,`阿里云oss`

##### 课件在线预览:

课件在线预览功能是用这个开源项目`https://gitee.com/kekingcn/file-online-preview.git`

如何运行,里面有教程(基本上就是下载完就能马上运行了)。

### 项目结构
##### 后端:

​	`mbg`模块->`mybatis-generator`的缩写。里面只要放了一些公用的东西如dao接口,实体类,注解。

​	`shiro`模块->安全认证模块,`JWT`也在里面

​	`	courseware`模块->主要的内务

##### 小程序->`uni-app-courseware`

##### 后台管理->`vue-courseware`

### 安装教程

##### 后端：

1.  `lombok`插件，`maven`
2.  导入sql文件，修改`courseware`模块下的resource中的`application-dev.yml`，配置好`mysql`和`redis`连接地址,阿里云oss，微信支付，微信小程序等（后面两个可不配,相关功能不可用）。

##### 小程序：

1. 在`manifest.json`中配置微信小程序`appid`,在`util/common.js`中的地址换成自己的oss地址
2. 在`hbuilder`导航栏的工具中选安装插件,安装scss/sass编译插件。
3. 导入`hbuilder`,运行到小程序。在微信开发者工具的右上角详情->本地设置中勾选不校验合域名

##### 后台管理：

1. yarn install

	2. 在`util/common.js`中的地址换成自己的oss地址,在main.js中配置oss信息
 	3. yarn serve

### 使用说明

1. 后端接口`swagger`文档地址`localhost:5000/doc.html`
2. 后台管理员 用户名admin,密码12345678
3. 不配置小程序的密钥和`appid`无法微信登录

#### 联系

1.  QQ: 1507906763
2.  WX:  ljc666max
4.  代做各种小程序,网站,app(h5)
4.  我写的一个代取快递，帮忙的app(https://www.jarcheng.top/flash_help/)。F12手机端打开
