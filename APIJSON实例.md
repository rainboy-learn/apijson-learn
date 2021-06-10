数组&分页

```json
{
    "User[]":{
        "page":0,
        "count":1,
        "User":{
            "sex":0  //过滤条件
        }
    }
}
```
```json
{
    "User[]":{
        "page":0,
        "count":1,
        "User":{
            "sex":0  //过滤条件
        }
        "Moment":{} //添加了这个条件 为什么会产生这样的效果呢？
    }
}
```

注意 ：
```json
{
    "[]":{ //与上面的返回结果其时不一样
        "page":0,
        "count":1,
        "User":{
            "sex":0  //过滤条件
        }
        "Moment":{} //添加了这个条件 为什么会产生这样的效果呢？
    }
}

```

## 匹配选项范围

`"key{}":[]`

```json
{
    "User[]": {
        "page": 0,
        "count": 3,
        "User": {
            "id{}": [
                38710,
                82001,
                70793
            ]
        }
    }
}
```

- 这是如何解析，解析到最后的sql语句是什么？


##  匹配条件范围1

```json
{
    "User[]": {
        "count": 3,
        "User": {
            "id{}": "<=80000,>90000"
        }
    }
}

```

与条件
```json
{
    "User[]": {
        "count": 3,
        "User": {
            "id&{}": ">=80000,<90000"
        }
    }
}

```

## 包含选项范围

```json
{
    "User[]": {
        "count": 3,
        "User": {
            "contactIdList<>": 38710
        }
    }
}

```

## 判断是否存在

```json
{
    "User": {
        "id}{@": {
            "from": "Comment",
            "Comment": {
                "momentId": 15
            }
        }
    }
}

sql: WHERE EXISTS(SELECT * FROM Comment WHERE momentId=15)
```


## 子查询

```json
{
    "User": {
        "id@": {
            "from": "Comment",
            "Comment": {
                "@column": "min(userId)"
            }
        }
    }
}

sql:WHERE id=(SELECT min(userId) FROM Comment)
```

sql中子查询 ？ 不会

## 模糊查询

```json
{
    "User[]": {
        "count": 3,
        "User": {
            "name$": "%m%"
        }
    }
}

解释：对应SQL是name LIKE '%m%'，查询name包含"m"的一个User数组
```

## 连续范围
“key%”:“start,end” => “key%”:[“start,end”]，其中 start 和 end 都只能为 Boolean, Number, String 中的一种，如 “2017-01-01,2019-01-01” ，[“1,90000”, “82001,100000”] ，可用于连续范围内的筛选


```json
{
    "User[]": {
        "count": 3,
        "User": {
            "date%": "2017-10-01,2018-10-01"
        }
    }
}
```

解释：对应SQL是date BETWEEN '2017-10-01' AND '2018-10-01'，
查询在2017-10-01和2018-10-01期间注册的用户的一个User数组


## 逻辑运算
```json
{
    "User[]": {
        "User": {
            "id!{}": [
                82001,
                38710
            ]
        }
    }
}
```

## join关联查询
