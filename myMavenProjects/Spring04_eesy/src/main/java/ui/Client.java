package ui;


import dao.IAccountDao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import service.IAccountService;
import service.impl.AccountServiceImpl;

/**
 * 模拟一个表现层，用于调用业务层
 */

public class Client {
    /**
     * 获取spring的IOC核心容器，并根据id获取对象
     * @param args
     */
    public static void main(String[] args) {

        /*// 1、获取核心容器对象
        // ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        ApplicationContext ac = new FileSystemXmlApplicationContext("//home/jeffrey/桌面/bean.xml");
        // 2、根据id获取bean对象
        IAccountService as = (IAccountService) ac.getBean("accountService");
        IAccountDao dao = ac.getBean("accountDao", IAccountDao.class);
        System.out.println("as" + as);
        System.out.println("dao" + dao);
        //
        as.saveAccount();*/


        // ---------------BeanFactory---------------
        Resource resource = new ClassPathResource("bean.xml");
        BeanFactory factory = new XmlBeanFactory(resource);
        IAccountService as = (IAccountService) factory.getBean("accountService");
        System.out.println(as);

    }

}

