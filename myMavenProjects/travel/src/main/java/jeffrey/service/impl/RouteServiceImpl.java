package jeffrey.service.impl;

import jeffrey.dao.FavoriteDao;
import jeffrey.dao.RouteDao;
import jeffrey.dao.RouteImgDao;
import jeffrey.dao.SellerDao;
import jeffrey.dao.impl.FavoriteDaoImpl;
import jeffrey.dao.impl.RouteDaoImpl;
import jeffrey.dao.impl.RouteImgDaoImpl;
import jeffrey.dao.impl.SellerDaoImpl;
import jeffrey.domain.PageBean;
import jeffrey.domain.Route;
import jeffrey.domain.RouteImg;
import jeffrey.domain.Seller;
import jeffrey.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private final RouteDao routeDao = new RouteDaoImpl();
    private final RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private final SellerDao sellerDao = new SellerDaoImpl();
    private final FavoriteDao favoriteDao = new FavoriteDaoImpl();


    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname ) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;//开始的记录数
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :(totalCount / pageSize) + 1 ;
        pb.setTotalPage(totalPage);


        return pb;
    }

    @Override
    public Route findOne(String rid) {
        // 根据id去route表中查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        // 根据route的id查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(Integer.parseInt(rid));
        // 将集合设置到route对象中
        route.setRouteImgList(routeImgList);
        // 根据route的sid(商家id)查询商家对象
        Seller seller = sellerDao.findBySid(route.getSid());
        route.setSeller(seller);
        // 查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }

}
