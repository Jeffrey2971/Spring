package jeffrey.dao.impl;

import jeffrey.dao.IAccountDao;
import org.springframework.stereotype.Repository;

/**
 * 账户的持久层实现类
 */
@Repository("accountDao1")
public class AccountDaoImpl_1 implements IAccountDao {
    public void saveAccount() {
        System.out.println("保存了账户！1");
    }
}
