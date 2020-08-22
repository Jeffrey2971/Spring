package jeffrey.service;

import jeffrey.domain.Account;

public interface IAccountService {


    /**
     * 查询
     * @param accountId
     * @return
     */
    Account findAccountById(Integer accountId);

    /**
     * 转账方法
     * @param sourceName
     * @param targetName
     * @param money
     */
    void transfer(String sourceName, String targetName, Float money);
}
