package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.factory.ConstantFactory;
import com.neuedu.mapper.FavoriteMapper;
import com.neuedu.mapper.TagMapper;
import com.neuedu.pojo.*;
import com.neuedu.mapper.ArticleMapper;
import com.neuedu.pojo.Article;
import com.neuedu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.util.ConstantDict;
import com.neuedu.util.UserLocalThread;
import org.apache.http.HttpResponse;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
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
 * @since 2020-04-04
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    ArticleMapper articleMapper;
    @Resource
    TagMapper tagMapper;
    @Resource
    FavoriteMapper favoriteMapper;
    @Resource
    ITagService tagService;
    @Resource
    IArticleratingService articleratingService;
    @Resource
    IUsercommentService usercommentService;
    @Resource
    IFavoriteService favoriteService;


    @Override
    public Result newArticleContent(Article article, Integer[] tags) throws IOException {
        if(tags==null||tags.length==0){
            return new Result(0,"没有选择标签","tags uncomplete");
        }
        StringBuffer tagsName=new StringBuffer("");
        //article.setPdfFileLocation(ConstantFactory.me().getFileURLWhenUpdate(pdffile));
        articleMapper.insert(article);
        Integer articleId=article.getId();
        for(Integer tag : tags){
            Tag tagPOJO=new Tag();
            tagPOJO.setArticleId(articleId);
            tagPOJO.setTagNum(tag);
            String tagName=ConstantFactory.me().getDictByKeywordAndNum(ConstantDict.STUDY_MAJOR,tag);
            tagPOJO.setTagName(tagName);
            tagMapper.insert(tagPOJO);
            tagsName.append(tagName+",");
        }
        int length=tagsName.length();
        tagsName.replace(length-1,length,"");
        article.setTags(tagsName.toString());
        articleMapper.updateById(article);
        return new Result(1,articleId,"article content upload success");
    }
    @Override
    public Result newArticlePDF(Integer articleID, MultipartFile pdffile) throws IOException {
        Article article=articleMapper.selectById(articleID);
        article.setPdfFileLocation(ConstantFactory.me().getFileURLWhenUpdate(pdffile));
        articleMapper.updateById(article);
        return new Result(1,article.getId(),"article pdf upload success");
    }

    @Override
    public Result newArticleCover(Integer articleID, MultipartFile cover) throws IOException {
        Article article=articleMapper.selectById(articleID);
        article.setCoverFileLocation(ConstantFactory.me().getFileURLWhenUpdate(cover));
        articleMapper.updateById(article);
        return new Result(1,article.getId(),"article cover upload success");
    }

    @Override
    public Result articlesByRecommend() throws TasteException {
        User user= UserLocalThread.getUser();
        List<Long> list=ConstantFactory.me().getItemBasedRecommender(Long.valueOf(1),4);
        System.out.println("---------articlerec");
        System.out.println(list);
        QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",list);
        List<Article> res=articleMapper.selectList(queryWrapper);
        return new Result(1,res,"success");
    }

    @Override
    public Result articleInfoByUser(Integer articleId) {
        User user=UserLocalThread.getUser();
        Map<String,Object> map=new HashMap<>();
        QueryWrapper<Favorite> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.eq("type_num",ConstantDict.FAV_ARTICLE_TYPE_NUM);
        queryWrapper.eq("content_id",articleId);
        queryWrapper.eq("status",ConstantDict.STATUS_VALID);
        Favorite favorite=favoriteMapper.selectOne(queryWrapper);
        if(favorite==null){
            map.put("favorite",0);
        }
        else{
            map.put("favorite",1);
        }
        map.put("score",articleratingService.averageScore(articleId));
        map.put("meContent",usercommentService.commentByArticleId(articleId));
        map.put("myScore",articleratingService.myScore(articleId));
        return new Result(1,map,"success");
    }

    @Override
    public Result favArticle(Integer articleId) throws IOException {
        return favoriteService.favorite(articleId,ConstantDict.FAV_ARTICLE_TYPE_NUM);
    }

    @Override
    public Result deFavArticle(Integer articleId) throws IOException {
        return favoriteService.deFavorite(articleId,ConstantDict.FAV_ARTICLE_TYPE_NUM);
    }

    @Override
    public void downloadArticle(String filePath, HttpServletResponse response) throws IOException {
        ConstantFactory.me().downloadFile(filePath,response);
    }

    @Override
    public Result changeArticleStatus(Integer articleId, Integer status) {
        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.eq("id",articleId);
        Article article = articleMapper.selectOne(articleQueryWrapper);
        article.setStatus(status);
        articleMapper.updateById(article);
        return new Result(1,"scuuess","success");
    }

    @Override
    public IPage<Article> searchArticles(Integer tag) {
        QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
        List<Integer> list=tagService.searchByTag(tag);
        System.out.println(list);
        queryWrapper.in("id",list);
        queryWrapper.orderByDesc("id");
        //System.out.println(articleMapper.selectList(queryWrapper));
        return this.page(new Page<Article>(1,7),queryWrapper);
    }

    @Override
    public Result articlesByTags() throws IOException {
        List<Map<String,Object>> list=ConstantFactory.me().getDictsListByKeyword(ConstantDict.STUDY_MAJOR);
        List<Map<String,Object>> res=new ArrayList<>();
        for(Map<String,Object> map :list){
            Map<String,Object> maP=new HashMap<>();
            maP.put("num",map.get("num"));
            maP.put("name",map.get("name"));
            IPage<Article> listArticle=new Page<>();
            Integer num=Integer.parseInt(map.get("num").toString());
            //System.out.println(num);
            listArticle=this.searchArticles(num);
            //System.out.println(listArticle);
            maP.put("data",listArticle);
            res.add(maP);
        }
        //System.out.println(res);
        return new Result(1,res,"success");
    }



}
