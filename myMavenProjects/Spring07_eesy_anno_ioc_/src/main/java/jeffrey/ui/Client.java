package jeffrey.ui;


import jeffrey.dao.IAccountDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import jeffrey.service.IAccountService;

/**
 * 模拟一个表现层，用于调用业务层
 */

public class Client {
    /**
     * 获取spring的IOC核心容器，并根据id获取对象
     *
     * @param args
     */
    public static void main(String[] args) {

        // 1、获取核心容器对象
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
//        ApplicationContext ac = new FileSystemXmlApplicationContext("//home/jeffrey/桌面/bean.xml");
        // 2、根据id获取bean对象
        IAccountService as1 = (IAccountService) ac.getBean("accountServiceImpl");
//        IAccountService as2 = (IAccountService) ac.getBean("accountServiceImpl");
//        System.out.println(as1);
//        IAccountDao as2 = ac.getBean("accountDao", IAccountDao.class);
//        System.out.println(as2);
//        System.out.println(as1 == as2);
        as1.saveAccount();
        ac.close();

    }

}

