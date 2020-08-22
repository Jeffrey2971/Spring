package jeffey.dao.impl;

import jeffey.dao.IAccountDao;
import jeffey.domian.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import java.util.List;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


/**
 * 账户的持久层实现类
 */


public class AccountDaoImpl_1 extends JdbcDaoSupport implements IAccountDao {

    public Account findAccountById(Integer accountId) {
        List<Account> accounts = getJdbcTemplate().query("select * from account where id = ?", new BeanPropertyRowMapper<Account>(Account.class), accountId);
        return accounts.isEmpty()?null:accounts.get(0);
    }

    public Account findAccountByName(String accountName) {
        List<Account> accounts = super.getJdbcTemplate().query("select * from account where name = ?", new BeanPropertyRowMapper<Account>(Account.class), accountName);
        if (accounts.isEmpty()){
            return null;
        }
        if (accounts.size() > 1){
            throw new RuntimeException("结果集不唯一，数据异常");
        }
        return accounts.get(0);
    }

    public void updateAccount(Account account) {
        getJdbcTemplate().update("update account set name=?,money=? where id=?", account.getName(), account.getMoney(), account.getId());

    }
}
