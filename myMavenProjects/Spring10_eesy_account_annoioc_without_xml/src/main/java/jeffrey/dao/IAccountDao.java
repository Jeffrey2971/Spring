package jeffrey.dao;

import jeffrey.domain.Account;

import java.util.List;

/**
 * 账户的持久层接口
 */

public interface IAccountDao {
    /**
     * 查询所有
     *
     * @return
     */
    public abstract List<Account> findAllAccount();

    /**
     * 查询一个
     *
     * @return
     */
    public abstract Account findAccountById(Integer accountId);

    /**
     * 保存操作
     *
     * @param account
     */
    public abstract void saveAccount(Account account);

    /**
     * 更新操作
     *
     * @param account
     */
    public abstract void updateAccount(Account account);

    /**
     * 更新操作
     */
    public abstract void deleteAccount(Integer accountId);

}
