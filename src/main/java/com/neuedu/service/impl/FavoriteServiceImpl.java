package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.factory.ConstantFactory;
import com.neuedu.mapper.ArticleMapper;
import com.neuedu.mapper.WordMapper;
import com.neuedu.pojo.*;
import com.neuedu.mapper.FavoriteMapper;
import com.neuedu.service.IFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.util.ConstantDict;
import com.neuedu.util.UserLocalThread;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {

    @Resource
    FavoriteMapper favoriteMapper;
    @Resource
    WordMapper wordMapper;
    @Resource
    ArticleMapper articleMapper;


    @Override
    public Result favorite(Integer Id, Integer type) throws IOException {
        String favType= ConstantFactory.me().getDictByKeywordAndNum(ConstantDict.FAVORITE,type);
        User user= UserLocalThread.getUser();
        System.out.println(user);
        QueryWrapper<Favorite> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("content_id",Id);
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.eq("type_num",type);
        Favorite favorite=favoriteMapper.selectOne(queryWrapper);
        if(favorite==null){
            favorite=new Favorite();
            favorite.setContentId(Id);
            favorite.setType(favType);
            favorite.setUserId(user.getId());
            favorite.setTypeNum(type);
            favoriteMapper.insert(favorite);
        }else{
            favorite.setStatus(ConstantDict.STATUS_VALID);
            favoriteMapper.updateById(favorite);
        }
        return new Result(1,"success","success");
    }

    @Override
    public Result deFavorite(Integer Id, Integer type) throws IOException {
        User user= UserLocalThread.getUser();
        QueryWrapper<Favorite> favoriteQueryWrapper=new QueryWrapper<>();
        favoriteQueryWrapper.eq("user_id",user.getId());
        favoriteQueryWrapper.eq("content_id",Id);
        favoriteQueryWrapper.eq("type_num",type);
        Favorite favorite=favoriteMapper.selectOne(favoriteQueryWrapper);
        favorite.setStatus(ConstantDict.STATUS_INVALID);
        favoriteMapper.updateById(favorite);
        return new Result(1,"success","success");
    }

    @Override
    public Result myFavorites(Integer typeNum,Integer pageNum,Integer pageSize) {
        Map<String,Object> map=new HashMap<>();
        User user=UserLocalThread.getUser();
        QueryWrapper<Favorite> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.eq("type_num",typeNum);
        queryWrapper.eq("status",1);
        IPage<Favorite> favoriteIPage = favoriteMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        map.put("mapIPage",favoriteIPage);
        List<Favorite> records = favoriteIPage.getRecords();
        List<Integer> idList=new ArrayList<>();
        for(Favorite favorite : records){
            idList.add(favorite.getContentId());
        }
        System.out.println(idList);
        if(typeNum==ConstantDict.FAV_ARTICLE_TYPE_NUM){
            QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
            articleQueryWrapper.in("id",idList);
            List<Article> articleList=articleMapper.selectList(articleQueryWrapper);
            System.out.println(articleList.size());
            map.put("data",articleList);
        }else if(typeNum==ConstantDict.FAV_WORD_TYPE_NUM){
            QueryWrapper<Word> wordQueryWrapper=new QueryWrapper<>();
            wordQueryWrapper.in("id",idList);
            List<Word> wordList=wordMapper.selectList(wordQueryWrapper);
            System.out.println(wordList.size());
            map.put("data",wordList);
        }
        return new Result(1,map,"success");
    }



}
