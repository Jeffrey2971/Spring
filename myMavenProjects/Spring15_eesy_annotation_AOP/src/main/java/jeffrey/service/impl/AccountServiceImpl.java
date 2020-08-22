package jeffrey.service.impl;

import jeffrey.service.IAccountService;
import org.springframework.stereotype.Service;

/**
 * 账户的业务层实现类
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    public void saveAccount() {
        System.out.println("执行了保存方法");
//        int i = 1/0;
    }

    public void updateAccount(int i) {
        System.out.println("执行了更新方法" + i);
    }

    public int deleteAccount() {
        System.out.println("执行了删除方法");
        return 0;
    }
}
