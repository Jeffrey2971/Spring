## Spring中的JdbcTemplate
- 概述：它是Spring框架中提供的一个对象，是对原始Jdbc Api对象的简单封装。Spring框架提供了很多操作模版类
    - 操作关系型数据的
        - JdbcTemplate
        - HibernateTemplate
    - 操作nosql数据库的
        - RedisTemplate
    - 操作消息队列的
        - JmsTemplate
        
- JdbcTemplate的作用
    - 它就是用于和数据库交互的，实现对表的CRUD操作
    
- 创建/获取对象
    - Spring提供了内置的数据源对象
        - DriverManagerDataSource ds = new DriverManagerDataSource();
        - 代码示例
            ```
            // 准备数据源：Spring内置数据源
                    DriverManagerDataSource ds = new DriverManagerDataSource();
                    ds.setDriverClassName("com.mysql.jdbc.Driver");
                    ds.setUrl("jdbc:mysql:///eesy");
                    ds.setUsername("root");
                    ds.setPassword("Aa664490254");
            
                    // 1、创建JdbcTemplate对象
                    JdbcTemplate jt = new JdbcTemplate();
                    // 给jt设置数据源
                    jt.setDataSource(ds);
                    // 2、执行操作
                    jt.execute("insert into account(name, money)values('ccc', 1000)");
            ```
    - 使用XML配置
        - 首先需要导入pom.xml配置文件中导入包的坐标
            - jdbc：spring-jdbc
            - 事物相关：spring-tx
        - 在bean.xml中配置jdbc
            - bean.xml
                ```
              <!--配置JdbcTemplate-->
                  <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
                      <property name="dataSource" ref="dataSource"></property>
                  </bean>
              
                  <!--配置数据源-->
                  <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
                      <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
                      <property name="url" value="jdbc:mysql:///eesy"></property>
                      <property name="username" value="root"></property>
                      <property name="password" value="Aa664490254"></property>
                  </bean>
                ```
## 定位需要使用的重载方法
- 当一个类中有多种重载方法，就需要确定两件事情
    - 有什么
        - sql语句
    - 需要什么
        - List集合
- 对象中的常用方法
    

- 细节
    - query
        - query(String sql, Object[] args, RowMapper<T> rowMapper) 所有jdk版本都可以使用
        - query(String sql, Object... args, RowMapper<T> rowMapper) 在jdk1.5版本之后才可使用
        - RowMapper可使用Spring内置的BeanPropertyRowMapper
    - JdbcDaoSupport
        - JdbcDaoSupport是Spring内置的一个Dao层的基类，来自spring-jdbc-4.2.4.RELEASE.jar这个包，其内部定义了JdbcTemplate的set方法，
        这样我们自己的dao类只需要继承JdbcDaoSupport类，就可以省略JdbcTemplate的set方法书写了，通过查看源码你会发现，该方法是final修饰的
    - 有两种方式可选，如果使用注解配置就不能使用继承JdbcDaoSupport，如果使用XML配置就可以继承自JdbcDaoSupport

## 作业
- Spring基于AOP的事物控制
    - 配置文件
        - 配置文件添加注解支持的空间
            ```
              <?xml version="1.0" encoding="UTF-8"?>
              <beans xmlns="http://www.springframework.org/schema/beans"
                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xmlns:aop="http://www.springframework.org/schema/aop"
                     xmlns:context="http://www.springframework.org/schema/context"
                     xsi:schemaLocation="http://www.springframework.org/schema/beans
                      http://www.springframework.org/schema/beans/spring-beans.xsd
                      http://www.springframework.org/schema/aop
                      http://www.springframework.org/schema/aop/spring-aop.xsd
                      http://www.springframework.org/schema/context
                      http://www.springframework.org/schema/context/spring-context.xsd">
            ```
        - 配置Spring创建容器时要扫描的包
            - ```
              <context:component-scan base-package='jeffrey'></conntext:component-scam>
              ```
        - 开启Spring注解对AOP的支持
            - ```
                <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
              ```
    - Service层
        - AccountService
            - 添加@Service和@Autowired
                - ```
                  package jeffrey.service.impl;
                  
                  
                  import jeffrey.dao.IAccountDao;
                  import jeffrey.domain.Account;
                  import jeffrey.service.IAccountService;
                  import org.springframework.beans.factory.annotation.Autowired;
                  import org.springframework.stereotype.Service;
                  
                  import java.util.List;
                  
                  /**
                   * 账户的业务层实现类
                   *
                   * 事务控制应该都是在业务层
                   */
                  @Service("accountService")
                  public class AccountServiceImpl implements IAccountService {
                  
                      @Autowired
                      private IAccountDao accountDao;
                  
                      
                      public List<Account> findAllAccount() {
                         return accountDao.findAllAccount();
                      }
                  
                      
                      public Account findAccountById(Integer accountId) {
                          return accountDao.findAccountById(accountId);
                  
                      }
                  
                      
                      public void saveAccount(Account account) {
                          accountDao.saveAccount(account);
                      }
                  
                      
                      public void updateAccount(Account account) {
                          accountDao.updateAccount(account);
                      }
                  
                      
                      public void deleteAccount(Integer acccountId) {
                          accountDao.deleteAccount(acccountId);
                      }
                  
                      
                      public void transfer(String sourceName, String targetName, Float money) {
                          System.out.println("transfer....");
                              //2.1根据名称查询转出账户
                              Account source = accountDao.findAccountByName(sourceName);
                              //2.2根据名称查询转入账户
                              Account target = accountDao.findAccountByName(targetName);
                              //2.3转出账户减钱
                              source.setMoney(source.getMoney()-money);
                              //2.4转入账户加钱
                              target.setMoney(target.getMoney()+money);
                              //2.5更新转出账户
                              accountDao.updateAccount(source);
                  
                              // 自定义异常
                              int i = 1 / 0;
                  
                              //2.6更新转入账户
                              accountDao.updateAccount(target);
                      }
                  }
    
                  ```
    - Dao层
        - AccountDaoImpl
            - 添加@Repository和@Autowired
                - ```
                  package jeffrey.dao.impl;
                  
                  
                  import jeffrey.dao.IAccountDao;
                  import jeffrey.domain.Account;
                  import jeffrey.utils.ConnectionUtils;
                  import org.apache.commons.dbutils.QueryRunner;
                  import org.apache.commons.dbutils.handlers.BeanHandler;
                  import org.apache.commons.dbutils.handlers.BeanListHandler;
                  import org.springframework.beans.factory.annotation.Autowired;
                  import org.springframework.stereotype.Repository;
                  
                  import java.util.List;
                  
                  /**
                   * 账户的持久层实现类
                   */
                  @Repository("accountDao")
                  public class AccountDaoImpl implements IAccountDao {
                  
                      @Autowired
                      private QueryRunner runner;
                      @Autowired
                      private ConnectionUtils connectionUtils;
                      
                      public List<Account> findAllAccount() {
                          try{
                              return runner.query(connectionUtils.getThreadConnection(),"select * from account",new BeanListHandler<Account>(Account.class));
                          }catch (Exception e) {
                              throw new RuntimeException(e);
                          }
                      }
                  
                      
                      public Account findAccountById(Integer accountId) {
                          try{
                              return runner.query(connectionUtils.getThreadConnection(),"select * from account where id = ? ",new BeanHandler<Account>(Account.class),accountId);
                          }catch (Exception e) {
                              throw new RuntimeException(e);
                          }
                      }
                  
                      
                      public void saveAccount(Account account) {
                          try{
                              runner.update(connectionUtils.getThreadConnection(),"insert into account(name,money)values(?,?)",account.getName(),account.getMoney());
                          }catch (Exception e) {
                              throw new RuntimeException(e);
                          }
                      }
                  
                      
                      public void updateAccount(Account account) {
                          try{
                              runner.update(connectionUtils.getThreadConnection(),"update account set name=?,money=? where id=?",account.getName(),account.getMoney(),account.getId());
                          }catch (Exception e) {
                              throw new RuntimeException(e);
                          }
                      }
                  
                      
                      public void deleteAccount(Integer accountId) {
                          try{
                              runner.update(connectionUtils.getThreadConnection(),"delete from account where id=?",accountId);
                          }catch (Exception e) {
                              throw new RuntimeException(e);
                          }
                      }
                  
                      
                      public Account findAccountByName(String accountName) {
                          try{
                              List<Account> accounts = runner.query(connectionUtils.getThreadConnection(),"select * from account where name = ? ",new BeanListHandler<Account>(Account.class),accountName);
                              if(accounts == null || accounts.size() == 0){
                                  return null;
                              }
                              if(accounts.size() > 1){
                                  throw new RuntimeException("结果集不唯一，数据有问题");
                              }
                              return accounts.get(0);
                          }catch (Exception e) {
                              throw new RuntimeException(e);
                          }
                      }
                  }
    
                  ```
    - utils
        - ConnectionUtils
            - 添加@Component和@Autowired
                - ```
                  package jeffrey.utils;
                  
                  import org.springframework.beans.factory.annotation.Autowired;
                  import org.springframework.stereotype.Component;
                  
                  import javax.sql.DataSource;
                  import java.sql.Connection;
                  
                  /**
                   * 连接的工具类，它用于从数据源中获取一个连接，并且实现和线程的绑定
                   */
                  @Component("ConnectionUtils")
                  public class ConnectionUtils {
                  
                      private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
                  
                      @Autowired
                      private DataSource dataSource;
                  
                      /**
                       * 获取当前线程上的连接
                       * @return
                       */
                      public Connection getThreadConnection() {
                          try{
                              //1.先从ThreadLocal上获取
                              Connection conn = tl.get();
                              //2.判断当前线程上是否有连接
                              if (conn == null) {
                                  //3.从数据源中获取一个连接，并且存入ThreadLocal中
                                  conn = dataSource.getConnection();
                                  conn.setAutoCommit(false);
                                  tl.set(conn);
                              }
                              //4.返回当前线程上的连接
                              return conn;
                          }catch (Exception e){
                              throw new RuntimeException(e);
                          }
                      }
                  
                      /**
                       * 把连接和线程解绑
                       */
                      public void removeConnection(){
                          tl.remove();
                      }
                  }

                  ```
        - TransactionManager
            - 添加@Component，@Aspect，@Pointcut，@Autowired，@Around
                - ```
                  package jeffrey.utils;
                  
                  import org.aspectj.lang.ProceedingJoinPoint;
                  import org.aspectj.lang.annotation.*;
                  import org.springframework.beans.factory.annotation.Autowired;
                  import org.springframework.stereotype.Component;
                  
                  /**
                   * 和事务管理相关的工具类，它包含了，开启事务，提交事务，回滚事务和释放连接
                   */
                  
                  @Component("TransactionManager")
                  @Aspect
                  public class TransactionManager {
                  
                      @Autowired
                      private ConnectionUtils connectionUtils;
                  
                      @Pointcut("execution (* jeffrey.service.impl.*.*(..))")
                      private void pt1(){}
                  
                      /**
                       * 开启事务
                       */
                      /*@Before("pt1()")*/
                      public  void beginTransaction(){
                          try {
                              System.out.println("执行了开启事物");
                              connectionUtils.getThreadConnection().setAutoCommit(false);
                          }catch (Exception e){
                              e.printStackTrace();
                          }
                      }
                  
                      /**
                       * 提交事务
                       */
                      /*@AfterReturning("pt1()")*/
                      public  void commit(){
                          try {
                              System.out.println("执行了提交事物");
                              connectionUtils.getThreadConnection().commit();
                          }catch (Exception e){
                              e.printStackTrace();
                          }
                      }
                  
                      /**
                       * 回滚事务
                       */
                      /*@AfterThrowing("pt1()")*/
                      public  void rollback(){
                          try {
                              System.out.println("执行了回滚事物");
                  
                              connectionUtils.getThreadConnection().rollback();
                          }catch (Exception e){
                              e.printStackTrace();
                          }
                      }
                  
                  
                      /**
                       * 释放连接
                       */
                      /*@After("pt1()")*/
                      public  void release(){
                          try {
                              System.out.println("执行了释放连接");
                  
                              connectionUtils.getThreadConnection().close();//还回连接池中
                              connectionUtils.removeConnection();
                          }catch (Exception e){
                              e.printStackTrace();
                          }
                      }
                  
                      /**
                       * 环绕通知
                       * @param pjp
                       * @return
                       */
                  
                      @Around("pt1()")
                      public Object aroundAdvice(ProceedingJoinPoint pjp){
                          Object rtValue = null;
                          try {
                              // 获取参数
                              Object[] args = pjp.getArgs();
                              // 开启事物
                              this.beginTransaction();
                              // 执行方法
                              rtValue = pjp.proceed(args);
                              // 提交事物
                              this.commit();
                              return rtValue;
                          } catch (Throwable throwable) {
                              // 回滚事物
                              this.rollback();
                              throw new RuntimeException(throwable);
                          } finally {
                              // 释放资源
                              this.release();
                          }
                  
                  
                      }
                  
                  }

                  ```