package jeffrey.dao.impl;

import jeffrey.dao.SellerDao;
import jeffrey.domain.Seller;
import jeffrey.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {

    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public Seller findBySid(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
    }
}
