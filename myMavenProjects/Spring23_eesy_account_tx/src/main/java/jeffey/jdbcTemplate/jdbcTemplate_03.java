package jeffey.jdbcTemplate;


import jeffey.domian.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * jdbcTemplate的CRUD操作
 */
public class jdbcTemplate_03 {
    public static void main(String[] args) {
        // 获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // 获取对象
        JdbcTemplate jt = ac.getBean("jdbcTemplate", JdbcTemplate.class);
        // 执行操作
        // 保存
//        jt.update("insert into account(name, money)values(?,?)", "eee", 2000f);
        // 更新
//        jt.update("update account set name = ?, money = ? where id =?", "test", 1000000f, 2);
        // 删除
//        jt.update("delete from account where id = ?", 6);

        // 查询所有

//        List<Account> accounts = jt.query("select * from account where money > ?", new AccountRowMapper(), 1000f);
//        List<Account> accounts = jt.query("select * from account where money > ?", new BeanPropertyRowMapper<Account>(Account.class), 1000f);
//
//        for (Account account : accounts) {
//            System.out.println(account);
//        }
        // 查询返回一行一列(使用聚合函数，不加group by)
//        Integer integer = jt.queryForObject("select count(*) from account where money > ?", Integer.class, 200000);
//        System.out.println(integer);
        // 查询一个
        List<Account> account = jt.query("select * from account where id = ?", new BeanPropertyRowMapper<Account>(Account.class), 8);

        System.out.println(account.isEmpty()?"没有内容" : account.get(0));

    }
}

class AccountRowMapper implements RowMapper<Account>{

    /**
     * 定义account的封装策略
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        /**
         * 把结果集中的数据疯转到account中，然后由Spring把每个account添加到集合中
         */
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setName(rs.getString("name"));
        account.setMoney(rs.getFloat("money"));
        return account;
    }
}
