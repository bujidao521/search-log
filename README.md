# Slog-多服务器批量日志查询系统

Slog主要用于多个linux服务器、多文件，并行批量日志查询，项目基于java+vue（elementUI），服务简单、高效，超轻量化

觉得好用记得帮忙点个星星，谢谢大佬

有什么建议可以加我微信提给我：zhaoyu3668

## 访问地址：http://162.14.116.178:9900

因为一些特殊原因，演示环境 不能增加/删除/更新操作，日志文件也是后台写固定的；

为了方便不太熟悉前端的人使用，已经把前端代码编译好，放在了java代码的static下；

项目下载启动java代码即可访问；

## Slog解决的问题：

很多公司查询日志的特别不方便，10多个服务器，需要一个一个查，影响效率，所以写了Slog来解决这种查询的麻烦

### 1、为什么不用elk？

有的系统没有接入elk，而且elk查询日志一般只能固定（大多配置单行）行查询，使用不是特别友好；

### 2、为什么不直接在linux上查询？

linux查询文件查询行数太多，很难找自己想找的东西，而且服务部署多个机器后，每个服务都需要查询一次日志，影响工作效率；

### 3、Slog的优势：

可以基于关键字进行多行查询，如关键字上下N行；

可以多服务器、多文件同时查询；


项目目前还存在一些问题需要后续优化：

1、搜索无校验；

2、搜索命令没有加命令过滤；

项目计划：

1、手机查询日志页面

2、增加权限管理和不带权限管理两个版本

## Slog使用介绍

模块：应用，实例，日志查询，全文查询
### 1.应用管理

应用界面：

![image-20220122213141072](README.assets/image-20220122213141072.png)

新建应用：

![image-20220122213213199](README.assets/image-20220122213213199.png)

查看实例->新建实例：

注：如果是密匙登录，则密码为空即可

![image-20220122213312925](README.assets/image-20220122213312925.png)

### 2.日志查询：

应用：之前创建的应用

文件：系统的文件名称

行数：文件的后N行

关键字：要查询的文字，也支持正则

命令：Cn行，上下n行，An，后n行，Bn前n行，参考linux命令

查询后对应的输入字段都会做缓存；以便于下次查询比较方便；

![image-20220122213443388](README.assets/image-20220122213443388.png)

选择服务->查询文件：

为了方便 输入文件，可以先点击查文件

注：点击文件，上面文件输入框即可自动带入

![image-20220122213518957](README.assets/image-20220122213518957.png)

点击文件->查询日志：

文件名称：info.search-log.log

关键词：执行结束

命令：C2

![image-20220122213721873](README.assets/image-20220122213721873.png)

![image-20220122213755561](README.assets/image-20220122213755561.png)

### 3.日志全文查询：

为了方便查询最新日志，所以提供后n行的查询

![image-20220122213817749](README.assets/image-20220122213817749.png)

