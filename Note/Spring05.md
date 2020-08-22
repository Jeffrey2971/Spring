    
## Spring中的事物控制

- Spring事物中需明确
    - JavaEE体系进行分层开发，事物处理位于业务层中，Spring提供了分层设计业务层的事物处理解决方案
    - Spring框架提供了一组事物控制的接口，这组接口在Spring-tx-5.0.2.RELEASE.jar包中
    - Spring的事物控制是基于AOP的，它既可以使用编程的方式实现，也可以使用配置的方式实现

- Spring事物中的API
    - PlatformTransactionManager
        - 此接口是Spring的事物管理器，里面提供了常用的事物方法
            - 接口中的方法
                - TransactionStatus getTransaction(@Nullable TransactionDefinition definition)
                - void commit(TransactionStatus status)
                - void rollback(TransactionStatus status)
    - TransactionDefinition 
        - 它是事物定义信息对象，它提供了事务相关信息获取的方法
            - 接口中的方法
                - 获取事物对象名称：String getName()
                - 获取事物隔离级别：int getIsolationLevel()
                    - 事物隔离级别反映事物提交并发访问时的处理态度
                        - ISOLATION_DEFAULT：默认级别，归属下列某一种
                        - ISOLATION_READ_UNCOMMITTED()：可以读取未提交的数据
                        - ISOLATION_READ_COMMITTED()：只能读取已提交的数据，解决脏读问题(Oracle默认级别)
                        - ISOLATION_REPEATABLE_READ()：是否读取其他事物提交修改后的数据，解决不可重复度问题(MySQL默认级别)
                        - ISOLATION_SERIALIZABLE()：是否读取其他事物提交添加后的数据，解决幻影读的问题
                - 获取事物传播行为：int getPropagationBehavior()
                    - REQUIRED：如果当前没有事物，就新建一个事物，如果已存在一个事物中，加入到这个事物中。一般的选择(默认值)
                    - SUPPORTS：支持当前事物，如果当前没有事物，就以非事物方法执行(没有事物)
                    - MANDATORY：使用当前的事物，如果当前没有事物，则抛出异常
                    - REQUERS_NEW：新建事物，如果当前在事物当中，则当前事物挂起
                    - NOT_SUPPORTED：以非事物方式运行，如果当前存在事物，则抛出异常
                    - NESTED：如果当前存在事物，则在嵌套事物内执行，如果当前没有事物，则执行REQUIRED类似的操作
                - 获取事物的超时时间：int getTimeout()
                    - 默认值为-1，没有超时限制，如果有，则以妙为单位进行设置
                - 获取事物是否只读：boolean isReadOnly()
                    - 查询应该只设置只读
    - TransactionStatus
        - 此接口提供事物的运行状态
            - 接口中的方法
                - void flush()：刷新事物
                - boolean hasSavepoint()：获取是否存在存储点
                - boolean isCompleted()：获取事物是否完成
                - boolean isNewTransaction()：设置事物的回滚
                - boolean isRollbackOnly()：获取事物是否回滚
## 配置
- 基于XML
    - spring中基于XML的声明式事务控制配置步骤
        - 配置事务管理器
            - ```
              <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
                  <property name="dataSource" ref="dataSource"></property>
              </bean>
              ```
        - 配置事务的通知
            - 此时需要导入事务的约束 tx名称空间和约束，同时也需要aop的
            - 使用tx:advice标签配置事务通知
                - 属性：
                    - id：给事务通知起一个唯一标识
                    - transaction-manager：给事务通知提供一个事务管理器引用
            - ```
              <tx:advice id="txAdvice" transaction-manager="transactionManager">
                  <!-- 配置事务的属性
                      isolation：用于指定事务的隔离级别。默认值是DEFAULT，表示使用数据库的默认隔离级别。
                      propagation：用于指定事务的传播行为。默认值是REQUIRED，表示一定会有事务，增删改的选择。查询方法可以选择SUPPORTS。
                      read-only：用于指定事务是否只读。只有查询方法才能设置为true。默认值是false，表示读写。
                      timeout：用于指定事务的超时时间，默认值是-1，表示永不超时。如果指定了数值，以秒为单位。
                      rollback-for：用于指定一个异常，当产生该异常时，事务回滚，产生其他异常时，事务不回滚。没有默认值。表示任何异常都回滚。
                      no-rollback-for：用于指定一个异常，当产生该异常时，事务不回滚，产生其他异常时事务回滚。没有默认值。表示任何异常都回滚。
                  -->
                  <tx:attributes>
                      <tx:method name="*" propagation="REQUIRED" read-only="false"/>
                      <tx:method name="find*" propagation="SUPPORTS" read-only="true"></tx:method>
                  </tx:attributes>
              </tx:advice>
              ```
        - 配置AOP中的通用切入点表达式并建立事务通知和切入点表达式的对应关系
            - ```
              <aop:config>
                  <!-- 配置切入点表达式-->
                  <aop:pointcut id="pt1" expression="execution(* jeffrey.service.impl.*.*(..))"></aop:pointcut>
                  <!--建立切入点表达式和事务通知的对应关系 -->
                  <aop:advisor advice-ref="txAdvice" pointcut-ref="pt1"></aop:advisor>
              </aop:config>
              ```
        - 配置事务的属性
           - 在事务的通知tx:advice标签的内部，使用```<tx:attributes>```标签

- 基于注解
    - Spring中基于注解的声明事物控制配置步骤
        - 导入注解所需的空间配置
            - ```
              
              ```
        - 导入spring创建容器时要扫描的包并在相对应的关系层添加注解
            - ```
                <context:component-scan base-package='指定包名'>
              ```
        - 添加事务管理器
            - ```
                <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
                                <property name="dataSource" ref="dataSource"></property>
                </bean>
              ```
        - 开启Spring对事物的支持
            - ```
                <tx:annotation-driven transaction-manager="transactionManager(事物管理器)"></tx:annotation-driven transaction-manager>
              ```
        - 在需要事物支持的地方添加@Transaction注解

- 基于纯注解
    - 删除bean.xml配置文件
    - 新建一个config包，用于存放配置类
        - SpringConfiguration
            - ```
              /**
               * Spring配置类，相当于bean.xml
               */
              
              @Configurable
              @ComponentScan("jeffrey")
              @Import({JdbcConfig.class, TransactionConfig.class})
              @PropertySource("jdbcConfig.properties")
              @EnableTransactionManagement
              public class SpringConfiguration {}
              ```
        - JdbcConfig
            - ```
              /**
                  和数据库连接相关的配置类
               */
              public class JdbcConfig {
              
                  @Value("${jdbc.driver}")
                  private String driver;
                  @Value("${jdbc.url}")
                  private String url;
                  @Value("${jdbc.username}")
                  private String username;
                  @Value("${jdbc.password}")
                  private String password;
              
                  /**
                   * 创建JdbcTemplate对象
                   * @param dataSource
                   * @return
                   */
                  @Bean(name = "jdbcTemplate")
                  public JdbcTemplate createJdbcTemplate(DataSource dataSource){
                      return new JdbcTemplate(dataSource);
                  }
              
                  /**
                   * 创建一个数据源对象
                   * @return
                   */
                  @Bean(name = "dataSource")
                  public DataSource createDataSource(){
                      DriverManagerDataSource ds = new DriverManagerDataSource();
                      ds.setDriverClassName(driver);
                      ds.setUrl(url);
                      ds.setUsername(username);
                      ds.setPassword(password);
                      return ds;
                  }
              }

              ```
        - TransactionConfig
            - ```
              /**
               * 和事务相关的配置类
               */
              public class TransactionConfig {
              
                  /**
                   * 用于创建事务管理器对象
                   * @param dataSource
                   * @return
                   */
                  @Bean(name = "transactionManager")
                  public PlatformTransactionManager createTransaction(DataSource dataSource){
                      return new DataSourceTransactionManager(dataSource);
                  }
              }
              ```
    - 新建一个properties配置文件
        - ```
          jdbc.driver = com.mysql.jdbc.Driver
          jdbc.url = jdbc:mysql:///eesy
          jdbc.username = root
          jdbc.password = Aa664490254
          ```