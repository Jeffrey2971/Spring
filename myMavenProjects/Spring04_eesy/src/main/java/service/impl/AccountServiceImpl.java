package service.impl;

import dao.IAccountDao;
import dao.impl.AccountDaoImpl;
//import factory.BeanFactory;
import org.springframework.beans.factory.BeanFactory;
import service.IAccountService;

/**
 * 账户的业务层实现类
 */

public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;
//    private final IAccountDao accountDao = (IAccountDao) BeanFactory.getBean("accountDao");
//    private int i = 1;

    public AccountServiceImpl(){
        System.out.println("对象创建了～");
    }

    public void saveAccount() {
        accountDao.saveAccount();
    }
}
