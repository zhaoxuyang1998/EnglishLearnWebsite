package com.neuedu.service;

import com.neuedu.pojo.Article;
import com.neuedu.pojo.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.Word;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
public interface IFavoriteService extends IService<Favorite> {

    Result favorite(Integer Id,Integer type) throws IOException;
    Result deFavorite(Integer Id,Integer type) throws IOException;

    Result myFavorites(Integer typeNum,Integer pageNum,Integer pageSize);

}
