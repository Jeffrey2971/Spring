package factory;

import service.IAccountService;
import service.impl.AccountServiceImpl;

public class StaticFactory {
    /**
     * 模拟一个工厂类(该类可能存在于jar包中，无法通过修改源码的方式来提供默认构造函数)
     */
    public static IAccountService getAccountService(){
        return new AccountServiceImpl();
    }
}
