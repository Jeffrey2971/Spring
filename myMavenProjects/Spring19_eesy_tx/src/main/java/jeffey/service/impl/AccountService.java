package jeffey.service.impl;

import jeffey.dao.IAccountDao;
import jeffey.domian.Account;
import jeffey.service.IAccountService;

import java.util.List;

public class AccountService implements IAccountService {
    private IAccountDao accountDao;

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }


    public Account findAccountById(Integer accountId) {
        return accountDao.findAccountById(accountId);

    }



    public void transfer(String sourceName, String targetName, Float money) {
        System.out.println("transfer....");
        //2.1根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        //2.2根据名称查询转入账户
        Account target = accountDao.findAccountByName(targetName);
        //2.3转出账户减钱
        source.setMoney(source.getMoney()-money);
        //2.4转入账户加钱
        target.setMoney(target.getMoney()+money);
        //2.5更新转出账户
        accountDao.updateAccount(source);

        // 自定义异常
//            int i = 1 / 0;

        //2.6更新转入账户
        accountDao.updateAccount(target);
    }
}
