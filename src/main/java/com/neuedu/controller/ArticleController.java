package com.neuedu.controller;


import com.neuedu.config.PassToken;
import com.neuedu.pojo.Article;
import com.neuedu.pojo.Result;
import com.neuedu.service.IArticleService;
import org.apache.http.HttpResponse;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-04-04
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    IArticleService articleService;

    @PostMapping("/newArticleContent")
    @PassToken
    @Transactional
    public Result newArticleContent(Article article, Integer[] tags) throws IOException {
        return articleService.newArticleContent(article,tags);
    }
    @PostMapping("newArticlePDF")
    @PassToken
    public Result newArticlePDF(Integer id, MultipartFile pdffile) throws IOException {
        return articleService.newArticlePDF(id,pdffile);
    }
    @PostMapping("newArticleCover")
    @PassToken
    public Result newArticleCover(Integer id, MultipartFile cover) throws IOException {

        return articleService.newArticleCover(id,cover);
    }

    @GetMapping("/articlesByAllTagsWithPage")
    @PassToken
    public Result articlesByAllTagsWithPage() throws IOException {
        return articleService.articlesByTags();
    }
    @GetMapping("/articlesByRecommend")
    public Result articlesByRecommend() throws TasteException {
        return articleService.articlesByRecommend();
    }
    @GetMapping("/articleInfoByUser")
    public Result articleInfoByUser(Integer articleId){
        return articleService.articleInfoByUser(articleId);
    }
    @GetMapping("/favArticle")
    public Result favArticle(Integer articleId) throws IOException {
        return articleService.favArticle(articleId);
    }
    @GetMapping("/deFavArticle")
    public Result deFavArticle(Integer articleId) throws IOException {
        return articleService.deFavArticle(articleId);
    }
    @GetMapping("/downloadArticle")
    public void downloadArticle(String filePath, HttpServletResponse response) throws IOException {
        articleService.downloadArticle(filePath,response);
    }

    @GetMapping("/changeArticleStatus")
    public Result changeArticleStatus(Integer articleId,Integer status){
        return articleService.changeArticleStatus(articleId,status);
    }


}
