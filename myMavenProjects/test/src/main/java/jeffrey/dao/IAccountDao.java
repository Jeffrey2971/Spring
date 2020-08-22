package jeffrey.dao;

import jeffrey.domain.Account;

public interface IAccountDao {

    /**
     * 查询
     */
    Account findAccountById(Integer accountId);
}
