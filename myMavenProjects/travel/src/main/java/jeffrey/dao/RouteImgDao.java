package jeffrey.dao;

import jeffrey.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 根据线路route的id查询图片
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
