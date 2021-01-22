# apijson简单使用

## 介绍

> APIJSON 是一种专为 API 而生的 JSON 网络传输协议 以及 基于这套协议实现的 ORM 库。为简单的增删改查、复杂的查询、简单的事务操作 提供了完全自动化的万能 API。能大幅降低开发和沟通成本，简化开发流程，缩短开发周期。适合中小型前后端分离的项目，尤其是 BaaS、Serverless、互联网创业项目和企业自用项目。

Gitee：https://gitee.com/Tencent/APIJSON



## 示例

Java端：https://gitee.com/greyzeng/api-json-boot



## 运行

### 准备数据库

DemoSQLConfig.java这个文件中提供了数据库的配置信息

需要配置：

- 数据库的Schema
- 数据库Version
- 数据库连接的URI
- 数据库的用户名密码

将/sql目录下的脚本文件导入数据库中。

### 增加依赖

将/libs目录下的jar包增加到项目的classpath中



### 启动项目

运行DemoApplication



### 测试

在Postman中新增一个POST请求，请求的URL是：

http://localhost:8080/get

请求的Body是：

```json
{
  "Moment": {
    "id": 12
  }
}
```

返回的结果是：

```json
{
  "Moment": {
    "id": 12,
    "userId": 70793,
    "date": "2017-02-08 16:06:11.0",
    "content": "APIJSON,let interfaces and documents go to hell !",
    "praiseUserIdList": [
      70793,
      93793,
      82044,
      82040,
      82055,
      90814,
      38710,
      82002,
      82006,
      1508072105320,
      82001
    ],
    "pictureList": [
      "http://static.oschina.net/uploads/img/201604/22172508_eGDi.jpg",
      "http://static.oschina.net/uploads/img/201604/22172507_rrZ5.jpg",
      "https://camo.githubusercontent.com/788c0a7e11a4f5aadef3c886f028c79b4808613a/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f3636303036372f3230313630342f3636303036372d32303136303431343232343932353935372d313732303737333630382e6a7067",
      "http://static.oschina.net/uploads/img/201604/22172507_Pz9Y.png",
      "https://camo.githubusercontent.com/c98b1c86af136745cc4626c6ece830f76de9ee83/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f3636303036372f3230313630342f3636303036372d32303136303431343232343930383036362d313837323233393236352e6a7067",
      "https://camo.githubusercontent.com/f513fa631bd780dc0ec3cf2663777e356dc3664f/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f3636303036372f3230313630342f3636303036372d32303136303431343232343733323232332d3337333933303233322e6a7067",
      "https://camo.githubusercontent.com/c98b1c86af136745cc4626c6ece830f76de9ee83/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f3636303036372f3230313630342f3636303036372d32303136303431343232343930383036362d313837323233393236352e6a7067",
      "https://camo.githubusercontent.com/f513fa631bd780dc0ec3cf2663777e356dc3664f/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f3636303036372f3230313630342f3636303036372d32303136303431343232343733323232332d3337333933303233322e6a7067"
    ]
  },
  "ok": true,
  "code": 200,
  "msg": "success",
  "sql:generate|cache|execute|maxExecute": "1|0|1|200",
  "depth:count|max": "1|5",
  "time:start|duration|end": "1611279884442|12|1611279884454"
}
```

更多的接口功能和查询语法见：

[接口功能](https://vincentcheng.github.io/apijson-doc/zh/all.html#%E6%8E%A5%E5%8F%A3%E5%8A%9F%E8%83%BD)

[功能符](https://vincentcheng.github.io/apijson-doc/zh/all.html#%E5%8A%9F%E8%83%BD%E7%AC%A6)



### 新增一个接口

需求：假设我们需要新增一张数据表，并把数据表的数据快速发布出来

假设要增加的数据表如下：

```sql
-- 原石
CREATE TABLE `b_stone` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `cost` int(10) NULL COMMENT '成本',
                           `price` int(10) NULL COMMENT '卖价',
                           `length` int(10) NULL,
                           `width`  int(10) NULL,
                           `height` int(10) NULL,
                           `weight` float(8,1) NULL,
  `creationdate` datetime default CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifydate` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modifier` varchar(80) NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
```



我们需要在model包下增加一个类：

```java
package apijson.demo.model;

import apijson.MethodAccess;

@MethodAccess(
        POST = {UNKNOWN, ADMIN},
        DELETE = {ADMIN}
)
public class Stone {
}
```

在DemoSQLConfig中增加：

```java
TABLE_KEY_MAP.put(Stone.class.getSimpleName(),"b_store");
```

配置表和实体类的映射

还需要在这个类中注册权限：

```java
AbstractVerifier.ACCESS_MAP.put(Stone.class.getSimpleName(),getAccessMap(Stone.class.getAnnotation(MethodAccess.class)));
```

为了防止登录错误，我们可以提前先增加如下代码，DemoParser中，在每个构造方法执行完super()后增加：

```java
setNeedVerify(false);
```

重启应用，POST请求：http://localhost:8080/get

body

```json
{
  "Stone": {
    "id": 1
  }
}
```

返回

```json
{
  "Stone": {
    "id": 1,
    "cost": 2,
    "price": 3,
    "length": 4,
    "width": 5,
    "height": 6,
    "weight": 7.0,
    "creationdate": "2021-01-22 10:00:56.0",
    "modifydate": "2021-01-22 10:01:00.0",
    "modifier": "8"
  },
  "ok": true,
  "code": 200,
  "msg": "success",
  "sql:generate|cache|execute|maxExecute": "1|0|1|200",
  "depth:count|max": "1|5",
  "time:start|duration|end": "1611282106759|10|1611282106769"
}
```

### 参考资料

[apijson-doc](https://vincentcheng.github.io/apijson-doc/zh/)
[APIJSON-boot](https://github.com/APIJSON/APIJSON-Demo/tree/master/APIJSON-Java-Server/APIJSONBoot)
