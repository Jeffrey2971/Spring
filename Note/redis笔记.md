# 今日内容
- redis
    - 概念
    - 下载/安装
    - 命令操作
        - 数据结构
    - 持久化
    - 使用Java客户端操作redis
    
## redis
- 概念：redis是一款高性能的NoSQL系列非关系型数据库
- 下载安装
    - sudo apt-get install redis
    - /etc/redis/redis.conf：配置文件
    - redis-cli：redis的客户端
    - redis-server：redis的服务器端
- 命令操作
    - redis存储的是：key, value格式的数据，其中key都是字符串，value有五中不同的数据结构
        - value的数据结构
            - 字符串类型：string
            - 哈希类型：hash map格式
            - 列表类型：list linkedlist格式，支持重复元素
            - 集合类型：set：不允许重复元素
            - 有序集合类型：sortedset 不允许重复，且元素有序排序
            
    - 字符串类型 string
        - 存储：set key value
        - 获取：get key value
            - hget key field：获取指定的field对应的值
            - hgetall key：获取所有的field和value
        - 删除：del key field
    
    - 哈希类型 hash
        - 存储：hset key field value
        - 获取：hget key field
        - 删除：hdel key field
        
    - 列表类型 list：可以添加一个元素到列表的头部(左边)或者尾部右边
            - 添加
                - lpush key value：将元素添加到列表左侧
                - rpush key value：将元素添加到列表右侧 
            - 获取：lrange key start end：范围获取
            - 删除
                - lpop key：删除列表最左侧的元素，并将元素返回
                - rpop key：删除列表最右侧的元素，并将元素返回
    - 集合类型 set：不允许重复元素
        - 存储：sadd key value1 value2...
        - 获取：smembers key：获取set集合中所有的元素
        - 删除：srem key value：删除set集合中某个元素
    - 有序集合类型：sortedset，不允许重复，且元素有序排序
        - 存储：zadd key score value
        - 获取：zrange key start end
        - 删除：zrem key value
    
- 通用命令
    - keys *：查询所有的键
    - type key：获取键对应的value类型
    - del key：删除指定的key value

- 持久化
    - redis是一个内存数据库，当redis服务器重启或电脑重启，数据会丢失，可以将redis内存中的数据持久化保存到硬盘的文件中
    - redis持久化机制
        - RDB：默认方式，不需要进行配置，默认使用这种机制
            - 在一定的间隔时间中，检测key的变化情况，然后持久化数据
            - 编辑/etc/redis/redis.config配置文件
                 #   after 900 sec (15 min) if at least 1 key changed
                  save 900 1
                 #   after 300 sec (5 min) if at least 10 keys changed
                  save 300 10
                 #   after 60 sec if at least 10000 keys changed
                 save 60 10000
             - 重新启动redis服务器，并制定配置文件名称
                - redis-server /etc/redis/redis.config
        - AOF：日志记录方式，可以记录每一条命令的操作，每一次命令操作后持久化数据
            - 编辑/etc/redis/redis.config文件
                - appendonly no：默认为关闭(关闭aof) --> appendonly yes(开启aof)
                
                - appendfsync always：每一次操作都执行持久化
                - appendfsync everysec：每隔一秒操作一次都执行持久化
                - appendonly no：不持久化
- Java客户端 Jedis
    - Jedis：一款Java操作redis数据库的工具
    - 使用步骤
        - 下载jedis的相关jar包
        - 使用
            // 获取连接
            Jedis jedis = new Jedis("localhost", 6379);
            // 操作
            jedis.set("username", "jeffrey");
            // 关闭连接
            jedis.close();
            
        - Jedis操作各种redis中的数据结构
            - 字符串类型：string
                - set
                - get
            - 哈希类型：hash map格式
                - hset
                - hget
                - hgetAll
            - 列表类型：list linkedlist格式，支持重复元素
                - lpush
                - rpush
                - lpop / rpop
                - lrange start end
            - 集合类型：set：不允许重复元素
                - sadd
                - smembers：获取所有元素
            - 有序集合类型：sortedset 不允许重复，且元素有序排序
                - zadd
    - Jedis连接池：JedisPool
        - 使用
            - 创建JedisPool连接池对象
            - 调用方法getResource()方法获取Jedis连接
            
## 案例
- 案例需求：
  	1. 提供index.html页面，页面中有一个省份 下拉列表
  	2. 当 页面加载完成后 发送ajax请求，加载所有省份
- 注意事项：使用redis缓存一些不经常变化的数据，例如，城市，姓名，年龄等
    - 数据库的数据一旦发生改变，则需要更新缓存
        - 数据库的表执行了一些增删改相关的操作，需要将redis缓存数据清空再次并再次存入
        - 在service对应的增删改方法中，将redis数据
  	