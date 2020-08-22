package jeffrey.service.impl;

import jeffrey.dao.IAccountDao;
import jeffrey.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import jeffrey.service.IAccountService;

@Transactional

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Account findAccountById(Integer accountId) {

        return accountDao.findAccountById(accountId);
    }

    public void transfer(String sourceName, String targetName, Float money) {

    }
}
