package jeffrey.service.impl;

import jeffrey.dao.IAccountDao;
import jeffrey.domain.Account;
import jeffrey.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账户的业务层实现类
 * <p>
 * 事务控制应该都是在业务层
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true) // 只读型事物配置
    public Account findAccountById(Integer accountId) {
        return accountDao.findAccountById(accountId);

    }


    @Transactional(propagation = Propagation.REQUIRED,readOnly = false) // 读写型事物
    // 需要的是读写型的事物配置
    public void transfer(String sourceName, String targetName, Float money) {
        System.out.println("transfer....");
        //2.1根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        //2.2根据名称查询转入账户
        Account target = accountDao.findAccountByName(targetName);
        //2.3转出账户减钱
        source.setMoney(source.getMoney() - money);
        //2.4转入账户加钱
        target.setMoney(target.getMoney() + money);
        //2.5更新转出账户
        accountDao.updateAccount(source);

//         int i = 1 / 0;

        //2.6更新转入账户
        accountDao.updateAccount(target);
    }
}


