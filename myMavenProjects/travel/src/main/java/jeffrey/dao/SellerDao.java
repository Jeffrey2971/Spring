package jeffrey.dao;

import jeffrey.domain.Seller;

public interface SellerDao {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Seller findBySid(int id);
}
