package jeffrey.service.impl;


import jeffrey.dao.IAccountDao;
import jeffrey.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;


/**
 * 账户的业务层实现类
 * xml的配置
 * <bean id="accountService" class="service.impl.AccountServiceImpl" scope="" init-method="" destroy-method="">
 * <property name="" value="" | ref=""></property>
 * </bean>
 * <p>
 * 用于创建对象的注解
 * 他们的作用就和在XML配置文件中便携一个<bean></bean>实现的功能是一样的
 *
 * @Component 作用：用于把当前类对象存入Spring容器中
 * 属性：value：
 * 用于注入数据的注解
 * 他们的作用就喝在XML配置文件中bean标签中写一个<property></property>标签的作用是一样的
 * 用于改变作用范围的注解
 * 他们的作用就和bean标签中使用scope属性实现功能是一样的
 * 和生命周期相关
 * 他们的作用就和bean标签中使用init-method和destroy-method的作用是一样的
 */

@Service(value = "accountServiceImpl")
//@Scope("prototype")
public class AccountServiceImpl implements IAccountService {
    //    @Autowired
//    @Qualifier("accountDao2")
    @Resource(name = "accountDao2")

    private IAccountDao accountDao2 = null;

    @PostConstruct
    public void init() {
        System.out.println("方法初始化");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("方法销毁");
    }

    public void saveAccount() {
        accountDao2.saveAccount();
    }
}
