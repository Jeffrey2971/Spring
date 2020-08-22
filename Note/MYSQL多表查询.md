# 今日内容

- 多表查询
- 事物
- DCL


## 多表查询
- 查询语法
    - select 
        列名列表
    from 
        表名列表
    where
        ... ...
    
    - 准备SQL
        
        CREATE TABLE dept(
            id INT PRIMARY KEY AUTO_INCREMENT,
            NAME VARCHAR(20)
        );
        INSERT INTO dept (NAME) VALUES ('开发部'),('市场部'),('财务部');

        CREATE TABLE emp (
            id INT PRIMARY KEY AUTO_INCREMENT,
            NAME VARCHAR(10),
            gender CHAR(1), -- 性别
            salary DOUBLE, -- 工资
            join_date DATE, -- 入职日期
            dept_id INT,
            FOREIGN KEY (dept_id) REFERENCES dept(id) -- 外键，关联部门表(部门表的主键)
        );
        
        INSERT INTO emp(NAME,gender,salary,join_date,dept_id) VALUES('孙悟空','男',7200,'2013-02-24',1);
        INSERT INTO emp(NAME,gender,salary,join_date,dept_id) VALUES('猪八戒','男',3600,'2010-12-02',2);
        INSERT INTO emp(NAME,gender,salary,join_date,dept_id) VALUES('唐僧','男',9000,'2008-08-08',2);
        INSERT INTO emp(NAME,gender,salary,join_date,dept_id) VALUES('白骨精','女',5000,'2015-10-07',3);
        INSERT INTO emp(NAME,gender,salary,join_date,dept_id) VALUES('蜘蛛精','女',4500,'2011-03-14',1);
     
     - 笛卡尔积
        - 有两个集合A和B，取这两个集合的所有组成情况
        - 要完成多表查询，需要消除无用的数据
     
    - 多表查询的分类
        - 内连接查询
            - 隐式内连接
                - 使用where条件来消除无用的数据
                - 例如：
                    SELECT
                        t1.`NAME`, -- 员工表姓名
                        t1.gender, -- 员工表性别
                        t2.`NAME` -- 部门表的名称
                      FROM
                        emp t1,
                        dept t2
                      WHERE
                        t1.dept_id = t2.id;
                        
            - 从哪些表中查询数据
            - 查询条件是什么
            - 查询哪些字段
            
            - 显式内连接
                - 语法：
                    - select 字段列表 from 表名1 inner join 表名2 on 加入的条件
                - 例如：
                    SELECT
                    	*
                    FROM
                    	emp
                    [INNER] JOIN dept ON emp.dept_id = dept.id;
                
        - 外链接查询
            - 左外连接
                - 语法：
                    - select 字段列表 from 表1 left [outer] join 表2 on 条件;
                - 查询的是左表所有数据以及其交集部分
            - 右外连接
                - 语法
                    - select 字段列表 from 表1 right [outer] join 表2 on 条件;
            - 查询的是右表所有数据以及其交集部分
        
        - 子查询
            - 概念：查询中嵌套查询，称嵌套的查询为子查询
            - 例如
                - 查询员工工资最高的信息
                    - select max(salary) from emp;
                
                - 查询员工工资等于9000
                    - select * from emp.salary = 9000;
                
                - 一条语句完成
                    - select * from emp where emp.salary = (select max(salary) from emp);
                
            - 子查询不同情况
                - 子查询的结果是单行单列的：
                    - 子查询可以作为条件，使用运算符判断。运算符：> >= < <= = 等
                    - 例如
                        - 查询员工工资小于平均工资的人
                        - select * from emp where emp.salary < (select avg(salary) from emp);
                - 子查询的结果是多行单列或单行多列的：
                    - 子查询可以作为条件，使用运算符in来判断
                    例如
                        - 查询 财务部 和 市场部 的所有员工信息
                        - select * from emp where dept_id in (select id from dept where name = "财务部" or name = "市场部");
                - 子查询的结果是多行多列的：
                    - 子查询可作为一张虚拟表参与查询
                    -例如
                        - 查询员工入职日期是2011-11-11之后的信息和部门信息
                        - 子查询
                            - select * from dept t1,(select * from emp where emp.join_date > '2011-11-11') t2 where t1.id= t2.dept_id;
                        - 普通查询
                            - select * from emp t1,dept t2 where t1.dept_id = t2.id and t1.join_date > '2011-11-11';
            
    - 多表查询练习
        select t1.id,         -- 员工编号
               t1.ename,      -- 员工姓名
               t1.salary,     -- 工资
               t2.jname,      -- 职务
               t2.description -- 职务描述
        from emp t1,
             job t2
        where t1.job_id = t2.id;
        
        -- ----------------------------------
        select t1.id,          -- 员工编号
               t1.ename,       -- 员工姓名
               t1.salary,      -- 工资
               t2.jname,       -- 职务
               t2.description, -- 职务描述
               t3.dname,
               t3.loc
        from emp t1,
             job t2,
             dept t3
        where t1.job_id = t2.id
          and t1.dept_id = t3.id;
        -- ---------------------------------
        select t1.ename,
               t1.salary,
               t2.grade
        from emp t1,
             salarygrade t2
        where t1.salary >= t2.losalary
          and t1.salary <= t2.hisalary;
        -- ---------------------------------查询员工姓名，工资，职务名称，职务描述，部门名称，部门位置，工资等级
        select t1.ename,
               t1.salary,
               t2.jname,
               t2.description,
               t3.dname,
               t3.loc,
               t4.grade
        from emp t1,
             job t2,
             dept t3,
             salarygrade t4
        where t1.job_id = t2.id  -- 员工和职位
          and t1.dept_id = t3.id -- 员工和部门
          and t1.salary between t4.losalary and t4.hisalary;
        -- --------------------------
        select t1.id,
               t1.dname,
               t1.loc,
               t2.tota
        from dept t1,
             (select dept_id,
                     count(id) as tota
              from emp
              group by dept_id) t2
        where t1.id = t2.dept_id;
        -- ------------------------
        select
        t1.ename,
        t1.mgr,
        t2.id,
               t2.ename
        from emp t1
        left join emp t2
        on t1.mgr = t2.id
        

## 事务
- 事务基本介绍
    - 概念
        - 如果一个包含多个步骤的业务操作，被事务管理，那么这些操作，要么同时成功，要么同时失败
    - 操作
        - 开启事务
            - start transaction;
        - 回滚
            - rollback
        - 提交
            - commit
        - 在MYSQL数据库中事务默认自动提交
            - 事务提交的两种方式
                - 自动提交
                    - MYSQL自动提交
                    - 一条DML（增删改）语句会自动提交一次事务
                - 手动提交
                    - Oracle数据库默认是手动提交事务
                    - 需要先开启事务后再提交
        - 修改事务的默认提交方式
            - 查看事务的默认提交方式
                - select @@autocommit; -- 1代表自动提交，0代表手动提交
            - 修改默认提交方式
                - set @@autocommit = 0;
            
    - 例如：
        CREATE TABLE account (
        				id INT PRIMARY KEY AUTO_INCREMENT,
        				NAME VARCHAR(10),
        				balance DOUBLE
        			);
        			-- 添加数据
        			INSERT INTO account (NAME, balance) VALUES ('zhangsan', 1000), ('lisi', 1000);
        			
        			
        			SELECT * FROM account;
        			UPDATE account SET balance = 1000;
        			-- 张三给李四转账 500 元
        			
        			-- 0. 开启事务
        			START TRANSACTION;
        			-- 1. 张三账户 -500
        			
        			UPDATE account SET balance = balance - 500 WHERE NAME = 'zhangsan';
        			-- 2. 李四账户 +500
        			-- 出错了...
        			UPDATE account SET balance = balance + 500 WHERE NAME = 'lisi';
        			
        			-- 发现执行没有问题，提交事务
        			COMMIT;
        			
        			-- 发现出问题了，回滚事务
        			ROLLBACK;
        

- 事务的四大特征
    - 原子性：
        - 是不可分割的最小操作单位，要么同时成功，要么同时失败
    - 持久性：
        - 当事务提交或回滚后，数据库会持久化的保存数据。
    - 隔离性：
        = 多个事务之间，相互独立
    - 一致性：
        - 事务操作前后，数据总量不变
- 事务的隔离级别           
    - 概念：多个事务之间是隔离的，独立的，但是如果多个事务操作同一批数据，则会引发一些问题，设置不同的隔离级别就可以解决这些问题
    - 存在的问题：
        - 脏读
            - 一个事务，读取到另一个事务中的没有提交的数据
        - 不可重复读（虚读）
            - 在同一个事务中，两次读取到的数据不一样
        - 幻读         
            - 一个事务操作（DML）数据表中所有的记录，另一个事务添加了一条数据，则第一个事务查询不到自己的修改
    - 隔离级别
        - read uncommitted：读未提交 
            - 产生的问题：
                - 脏读，不可重复读，幻读
                
        - read committed：读已提交（Oracle）
            - 产生的问题：
                - 不可重复读，幻读
        - repeatable read：可重复读（MYSQL默认）
            - 产生的问题
                - 幻读
        - serializable：串行化
            - 锁表
            - 可解决所有的问题
        - 注意事项：隔离级别从小到大，安全性越来越高，但是效率越来越低
        
    - 数据库查询隔离级别
        - select @@tx_isolation;
    - 数据库设置隔离级别
        - set global transaction isolation level 级别字符串;
        
    - 演示：
        set global transaction isolation level read uncommitted;
        start transaction;
        -- 转账操作
        update account set balance = balance - 500 where id = 1;
        update account set balance = balance + 500 where id = 2;

## DCL
- SQL分类
    - DDL：操作数据库和表
    - DML：操作增删改表中的数据
    - DQL：查询表中的数据
    - DCL：管理用户，授权

- DBA：数据库管理员

- DCL：管理用户，授权
    - 管理用户
        - 添加用户
            - 语法
                - create user '用户名'@'主机名' identified by '密码'
        - 删除用户
            - 语法
                - drop user '用户名'@'主机名';
        - 修改用户密码
             - 语法
                - set password for '用户名'@'主机名' = password('新密码');
                - update password set 密码字段 = password('新密码') where user='用户名'
                
             - 在MYSQL中如果忘记了root用户密码
                - 停止MYSQL服务
                - 启动无验证方式启动MYSQL服务
                    - mysqld --skip-grant-tables
                - 打开新的终端直接输入mysql命令
                - use mysql;
                - 修改密码
                - 关闭mysqld服务
                - 启动MYSQL服务并使用新密码登录

        - 查询用户
            - 切换到MYSQL数据库
                - use mysql;
            - 查询user表
                - select * from user;
          
            - 通配符%：可以表示在任意主机使用用户登录数据库
    - 授权管理
        - 查询权限
            - show grants for '用户名'@'主机名';
        - 授予权限
            - grant 权限列表 on 数据库名.表名 to '用户名'@'主机名';
            - 授予用户所有权限
                - grant all on *.* to '用户名'@'主机名';

        - 撤销权限
            - 语法
                - revoke 权限列表 on 数据库名.表名 from '用户名'@'主机名';
                
        
        
