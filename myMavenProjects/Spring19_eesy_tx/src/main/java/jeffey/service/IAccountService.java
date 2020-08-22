package jeffey.service;

import jeffey.domian.Account;

/**
 * 账户的业务层接口
 */
public interface IAccountService {

    /**
     * 根据ID查询账户信息
     *
     * @param accountId     传入ID
     * @return
     */
    Account findAccountById(Integer accountId);

    /**
     * 转账
     * @param sourceName    转出账户名称
     * @param targetNme     转入账户名称
     * @param money         转出金额
     */
    void transfer(String sourceName, String targetNme, Float money);
}
