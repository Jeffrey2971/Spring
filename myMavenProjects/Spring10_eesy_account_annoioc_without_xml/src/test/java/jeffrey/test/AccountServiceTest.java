package jeffrey.test;

import config.JdbcConfig;
import config.SpringConfiguration;
import jeffrey.domain.Account;
import jeffrey.service.IAccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.Classes;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用Junit单元测试：测试配置
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountServiceTest {

    @Autowired
    private IAccountService as;

/*    @Before
    public void init(){
        // 获取容器
        ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        // 得到业务层对象
        as = ac.getBean("accountService", IAccountService.class);
    }*/

    @Test
    public void testFindAll() {
        // 执行方法
        List<Account> accounts = as.findAllAccount();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    @Test
    public void testFindOne() {
        // 执行方法
        Account accountById = as.findAccountById(4);
        System.out.println(accountById);
    }

    @Test
    public void testSave() {
        Account account = new Account();
        account.setName("mable");
        account.setMoney(12345f);
        // 获取容器
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        // 获取业务层对象
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        // 执行方法
        accountService.saveAccount(account);

    }

    @Test
    public void testUpdate() {
        // 获取容器
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        // 获取业务层对象
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        // 执行方法
        Account accountById = accountService.findAccountById(1);
        accountById.setMoney(23456f);
        accountService.updateAccount(accountById);
    }

    @Test
    public void testDelete() {
        // 获取容器
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        // 获取业务层对象
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        // 执行方法
        accountService.deleteAccount(4);
    }
}
