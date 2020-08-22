# 今日内容

- 数据库的基本概念

- MYSQL数据库软件
    - 安装
    - 卸载
    - 配置

- SQL

## 数据库的基本概念
- 数据库的英文单词：database 简称为：db
- 数据库
    - 用于存储和管理数据的仓库

- 数据库的特点
    - 持久化存储数据，就是一个文件系统
    - 方便存储和管理数据
    - 使用了统一的方式操作数据库 -- SQL
    
- 常见的数据库软件
    - MYSQL
    - ORACLE
    - ... ...
# MYSQL数据库软件
- ubuntu
    - 安装
        - sudo apt-get install mysql-server mysql-client

- 查看安装端口情况
    - sudo netstat -tap | grep mysql

- 配置文件位置
    - sudo vim /etc/mysql/my.cnf

- 打开关闭服务
    - /etc/init.d/mysql start/stop
    
- 登入方式
    - mysql -u账户 -p密码
    - mysql -hIP地址 -uroot -p连接目标的密码
    -  mysql --host=IP地址 --user=root --password=连接目标密码

- 设置本机MYSQL可以被局域网所访问
    - mysql -uroot -p root
    - mysql->use mysql
    - mysql->update user set host = '%' where user ='root';
    - mysql->grant all privileges on *.* to 'root'@'%' with grant option;
    - mysql->flush privileges;
    - mysql->exit;
    - sudo /etc/init.d/mysql restart


- 其它文件默认位置
    - /usr/bin                  客户端程序和脚本  
    - /usr/sbin                 mysqld 服务器  
    - /var/lib/mysql            日志文件，数据库  ［重点要知道这个］  
    - /usr/share/doc/packages   文档  
    - /usr/include/mysql        包含( 头) 文件  
    - /usr/lib/mysql            库  
    - /usr/share/mysql          错误消息和字符集文件  
    - /usr/share/sql-bench      基准程序  
    

- MYSQL目录结构
    - MYSQL安装目录
        - 配置文件：/etc/my.cnf
    - MYSQL数据目录
        - 几个概念
            - 数据库：文件夹
            - 数据：文件
            - 表：文件里存储的数据
            
# SQL
- SQL全称：structured query language 表示结构化查询语言，定义了操作所有关系型数据库的规则
    - 每一种数据库操作方式都存在不一样的地方，称为“方言”，但整体大部分一致
    
- SQL通用语法：
    - SQL语句可以单行或多行书写，以分号为结尾
    - 可使用空格和缩进来增强语句的可读性
    - MYSQL数据库的SQL语句不区分大小写，关键字建议使用大写
    - 三种注释
        - 单行注释：
            - -- 注释内容
            - # 注释内容（MYSQL特有）
        - 多行注释：
            - /* 注释内容 */
            
- SQL分类：
    - DML：增删改表中的数据
    - DDL：操作数据库、表
    - DQL：查询表中的数据
    - DCL：数据库中授权设置


    - Data Definition Language (DDL 数据定义语言) 如:建库,建表
    - Data Manipulation Language(DML 数据操纵语言),如:对表中的记录操作增删改
    - Data Query Language(DQL 数据查询语言),如:对表中的查询操作
    - Data Control Language(DCL 数据控制语言),如:对用户权限的设置
    
        
## DDL 操作数据库、表
- 操作数据库：CRUD
    - C（Create）：创建
        - 创建数据库
            - create database 数据库名称
        - 创建数据库且指定字符集
            - create database 数据库名称 charset 指定的字符集编码
        - 创建数据库并判断不存在，再创建
            - create database if not exists 数据库名称
        - 创建名为test数据库，判断是否存在且指定字符集为GBK
            - CREATE DATABASE if not exists test charset utf8
    - R（Retrieve）：查询
        - 查询所有数据库的名称
            - show databases;
        - 查看某个数据库的字符集：查看某个数据库的创建语句
            - show create database 数据库的名称;
    - U（Update）：修改
        - 修改数据库的字符集
            - alter database 数据库名称 charset 字符集名称
    - D (Delete)
        - 删除数据库
            - drop database 数据库名称;
        - 先判断数据库存在，再删除
            - drop database if exists 数据库名称;
    - 使用数据库
        - 查询当前正在使用的数据库名称
            - select database();
        - 使用数据库
            - use 数据库名称;
            
- 操作表
    - C（Create）：创建
        - 语法
            - create table 表名(
                列名1 数据类型1,
                列名2 数据类型2,
                ... ...
                列名n 数据类型3
            );
                - 注意：最后一列数据不需要添加逗号
                
        - 数据库类型
            - int(integer)：整数类型
                - age int,
            - double：小数类型
                - score double(5,2)
            - date：日期类型，只包含年月日的日期
                - yyyy-MM-dd
            - datetime：日期，包含了年月日时分秒
                - yyyy--MM-dd HH:mm:ss
            - timestamp：时间戳类型，包含了年月日时分秒
                - yyyy--MM-dd HH:mm:ss
                - 如果将来不给这个字段赋值，赋值为null，则默认使用当前的系统时间来自动赋值
            - varchar：字符串类型
                - name varchar(20)
                - 表示了姓名最大20个字符
                - 例如：zhangsan 八个字符，张三 两个字符，如果超出了指定的长度则会报错
        - 创建表
            - create table student(
                id int,
                name varchar(32),
                age int,
                score double(4,1),
                birthday date,
                insertTime timestamp
            );
            
        - 备份（复制）表
            - create table 新表名 like 原表名; 
            
               
    - R（Retrieve）：查询
        - 查询某个数据库中所有表的名称
            - show tables;
        - 查询表结构
            - desc 表名称;
    - U（Update）：修改
        - 修改表明
            - alter table 表名 rename to 新表名;
        - 修改表的字符集
            - 首先查看表名
                - show create table 表名称;
            - 修改字符集
                - alter table 表名称 charset 指定的字符集
        - 添加一列
            - alter table 表名称 add 列名 数据类型;
        
        - 修改列的名及类型
            - alter table 表名 change 列名 新列名 新数据类型();
            - alter table 表名 modify 列名 新数据类型();
        - 删除列
            - alter table 表名 drop 列名;
    - D (Delete)：删除
        - 删除表
            - drop table 表名称;
        - 判断表存在，再删除
            - drop table if exists 表名称;
    

- 客户端图形化工具
    - Nautica
    
- DML：增删改表中数据
    - 添加数据
        - 语法
            - inster into 表名(列名1,列名2,...,列名n) value (值1,值2,...值n);
        - 注意事项
            - 列名和值要一一对应
            - 如果表名后不定义列名则默认给所有列添加值(检化的书写格式)
                - inster into 表名 value (值1,值2,...值n);
            - 除了数字类型，其他类型需要使用引号（单引号或双引号都可以）引起来
                
    - 删除数据
        - 语法
            - delete from 表名 [where 条件];
        - 注意事项
            - 如果不加条件则删除表中所有记录
            - 如果需要删除所有记录
                - delete from 表名; -- 不推荐使用，有多少条数据就会执行多少次删除操作
                - truncate table 表名; -- 推荐使用，效率更高，先删除表然后再创建一张一模一样的表
    - 修改数据
        - 语法
            - update 表名 set 列名1 = 值1,列名2=值2,...[where 条件]
        - 注意事项
            - 如果不加任何条件则会将表中所有的记录全部修改
            
    
- DQL：查询表中的记录
    - select * from 表名
    
    - 语法
         select
            字段列表
         from
            表明列表
         where
            条件列表
         group by
            分组字段
         having
            分组之后的条件
         order by
            排序
         limit
            分页限定
    
    - 基础查询
        - 多个字段的查询
            - sekect 字段名1,字段名2,...字段名n from  表名称;
            - 注意事项：
                - 如果查询所有字段，则可以使用代替字段列表
        - 去除重复
            - distinct关键字
            - 注意事项
                - 只有字段都相同才会去重
        - 计算列
            - 一般可以使用四则运算来计算列的值（一般只会进行数值型的计算）
            - 注意事项
                - ifnull(表达式1，表达式2)：null参数的运算，结果都为null
                    - 表达式1：那个字段需要判断是否为null
                    - 表达式2：如果该字段为null后的替换值
        - 起别名
            - as关键字（可省略）
    
    - 条件查询
        - where 字句后面跟条件
        - 运算符
            - > < <= >= = <> 
            - BETWEEN..AND
            - IN(集合)   
            - LIKE：模糊查询
                - 占位符
                    - _ 表示了单个任意字符
                    - % 表示任意多个字符
            - IFNULL
            - AND 或 &&
            - or 或 ||
            - not 或 ！
            


### test
CREATE TABLE student (
id int,
-- 编号
name varchar(20), -- 姓名
age int, -- 年龄
sex varchar(5),
-- 性别
address varchar(100),
-- 地址
math int, -- 数学
english int -- 英语
);
INSERT INTO student(id,NAME,age,sex,address,math,english) VALUES (1,'马云',55,'男','杭州',66,78),(2,'马化腾',45,'女','深圳',98,87),(3,'马景涛',55,'男','香港',56,77),(4,'柳岩',20,'女','湖南',76,65),(5,'柳青',20,'男','湖南',86,NULL),(6,'刘德华',57,'男','香港',99,99),(7,'马德',22,'女','香港',99,99),(8,'德玛西亚',18,'男','南京',56,65);


select * from student;



-- 查询 姓名和年龄
select 
        name, -- 姓名
        age -- 年龄
from 
        student; -- 学生表

select address from student;

-- 去除重复的结果集
select distinct address from student;
select DISTINCT name, address from student;

-- 计算math和English分数之和

select name,math,english, math+english from student;
-- 不合理，如果有null参与的运算，计算结果都为null
select name,math,english, math+ifnull(english, 0) from student;

-- 起别名
select name as 姓名,math as 数学,english as 英语, math+ifnull(english, 0) as 总分 from student;

select * from student;

-- 查询年龄大于20岁
SELECT * from student where age > 20;
SELECT * from student where age >= 20;
SELECT * from student where age < 20;
SELECT * from student where age <= 20;
SELECT * from student where age = 20;

-- 查询年龄不等于于20岁
SELECT * from student where age != 20;
SELECT * from student where age <> 20;

-- 查询年龄大于等于20小于等于30
SELECT * from student WHERE age >= 20 and age <= 30;
SELECT * from student WHERE age BETWEEN 20 and 30;

-- 查询年龄为20岁19岁25岁的信息
select * from student where age = 18 or age = 20 or age = 22;
SELECT * from student where age IN (22,20,18);

-- 查询英语成绩为null
-- SELECT * from student where english = null; -- 错误写法，null值不能使用=或！=判断
SELECT * from student where english is null;
SELECT * from student where english is not null;

-- 查询班级姓马的有哪些
select * from student where name LIKE '马%';

-- 查询第二个字是化的人
select * from student where name LIKE "_化%";

-- 查询姓名是三个字的人
select * from student where name LIKE '___';

-- 查询姓名中包含马的人
select * from student where name LIKE '%德%'