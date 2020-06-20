package com.neuedu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neuedu.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.pojo.Article;
import com.neuedu.pojo.Result;
import org.apache.http.HttpResponse;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-04-04
 */
public interface IArticleService extends IService<Article> {
    Result newArticleContent(Article article, Integer[] tags) throws IOException;

    IPage<Article> searchArticles(Integer tag);

    Result articlesByTags() throws IOException;

    Result newArticlePDF(Integer articleID, MultipartFile pdffile) throws IOException;

    Result newArticleCover(Integer articleID, MultipartFile cover) throws IOException;

    Result articlesByRecommend() throws TasteException;

    Result articleInfoByUser(Integer articleId);

    Result favArticle(Integer articleId) throws IOException;

    Result deFavArticle(Integer articleId) throws IOException;

    void downloadArticle(String filePath, HttpServletResponse response) throws IOException;

    Result changeArticleStatus(Integer articleId, Integer status);
}
