package jeffey.jdbcTemplate;


import jeffey.dao.IAccountDao;
import jeffey.domian.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * jdbcTemplate基本用法
 */
public class jdbcTemplate_04 {
    public static void main(String[] args) {
        // 获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // 获取对象
        IAccountDao accountDao = ac.getBean("accountDao", IAccountDao.class);

        Account account = accountDao.findAccountById(1);
        System.out.println(account);

//        Account test = accountDao.findAccountByName("test");
//        System.out.println(test);

        account.setMoney(35000f);
        accountDao.updateAccount(account);

    }
}
