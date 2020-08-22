package jeffrey.proxy;

/**
 * 生产厂家的要求接口
 */
interface IProduct {
    /**
     * 销售
     * @param money
     */
    public void saleProduct(float money);

    /**
     * 售后
     * @param money
     */
    public void afterService(float money);

    }

