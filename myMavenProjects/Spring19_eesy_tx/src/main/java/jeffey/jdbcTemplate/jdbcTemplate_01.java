package jeffey.jdbcTemplate;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * jdbcTemplate基本用法
 */
public class jdbcTemplate_01 {
    public static void main(String[] args) {
        // 准备数据源：Spring内置数据源
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql:///eesy");
        ds.setUsername("root");
        ds.setPassword("Aa664490254");

        // 1、创建JdbcTemplate对象
        JdbcTemplate jt = new JdbcTemplate();
        // 给jt设置数据源
        jt.setDataSource(ds);
        // 2、执行操作
        jt.execute("insert into account(name, money)values('ccc', 1000)");
    }
}
