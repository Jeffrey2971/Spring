package jeffrey.service.impl;

import jeffrey.dao.FavoriteDao;
import jeffrey.dao.impl.FavoriteDaoImpl;
import jeffrey.domain.Favorite;
import jeffrey.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteDao favoriteDao = new FavoriteDaoImpl();


    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);


        return favorite != null; // 如果对象有值，则为true，否则为false
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid), uid);
    }
}
