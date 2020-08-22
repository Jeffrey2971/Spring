# 今日内容
- 数据库连接池
    - 概念：其实就是一个容器(集合)，用于存放数据库连接的容器，当系统初始化好后，容器被创建，
    容器会申请一些连接对象，当用户访问数据库的时候，就从容器中获取连接对象，用户访问完毕之后连接就会归还给容器
    
    - 好处
        - 节约资源
        - 用户访问高效
        
    - 实现
        - 标准的接口：DataSource  javax.sql包下
        
    - 方法
        - 获取连接：getConnection()
        - 归还连接：Connection.close() 如果连接对象Connection是从连接池中获取的，那么调用Connection.close()方法，则不会再关闭连接，而是归还连接
        
    - 一般不去实现它，由数据库厂商来实现
        - C3P0：数据库连接池的技术
        - Druid：数据库连接池实现技术，由阿里巴巴提供
       
- D3P0：使用步骤
    - 步骤
        - 导入两个jar包 c3p0-0.9.5.2.jar 和 依赖jar包 mchange-commons-java-0.2.12.jar
            - 注意需导入数据库驱动jar包
        - 定义配置文件：
            - 名称：c3p0.properties 或者 c3p0-config.xml
            - 路径：直接将文件放在src目录下即可
        - 创建核心对象 数据库连接池对象 ComboPooledDataSource
        - 获取连接：getConnection

- Druid：数据库连接池实现技术，由阿里巴巴提供
    - 步骤
        - 导入jar包 druid-1.0.9.jar
        - 定义配置文件
            - Properties形式
            - 可以叫任意名称，可放在任意的目录下
            - 需手动加载
        - 加载配置文件
            - Properties
        - 获取数据库连接池对象
            - 通过一个工厂类来获取：DruidDataSourceFactory
        - 获取连接
            - getConnection
    - 定义工具类
        - 定义一个类 jdbcUtils
        - 提供静态代码块加载配置文件，初始化连接池对象
        - 提供方法
            - 获取连接方法：通过数据库连接池获取连接
            - 释放资源
            - 获取连接池方法
            
##
- Spring JDBC：JDBC Template
    - Spring框架对JDBC简单封装，提供了JDBCTemplate对象来简化JDBC的开发
    - 步骤
        - 导入jar包
        - 创建JDBCTemplate对象，依赖于数据源DataSource
            - JDBCTemplate template = new JdbcTemplate(ds);
            
        - 调用JDBCTemplate的方法来完成CRUD
            - update()：执行DML语句，增删改语句
            - queryForMap()：查询结果将结果集封装为map集合
                - 注意事项：这个方法查询的结果集长度只能是1，将列名作为key，将值座位value，这条记录会封装到map集合
            - queryForList()：查询结果将结果集封装为list集合
                - 注意事项：将每一条记录封装为一个Map集合后再将多个Map集合装载到List集合中
            - queryForObject()：查询结果，将结果封装为对象
                - 一般用于聚合函数的查询
            - query()：查询结果，将结果封装为JavaBean对象
                - query的参数：RowMapper
                                - 一般使用BeanPropertyMapper实现类，可以完成数据到JavaBean的自动封装
                                - new BeanPropertyRowMapper<类型>(类型.class)
    - 练习
        - 需求：更改emp表
            - 修改1号数据的salary的工资为10000
            - 添加一条记录
            - 删除刚才添加的记录
            - 查询id为1的记录，将其封装为Map集合
            - 查询所有的记录，将其封装为List集合
            - 查询所有的记录，将其封装为Emp对象的List集合
            - 查询总的记录数
            
