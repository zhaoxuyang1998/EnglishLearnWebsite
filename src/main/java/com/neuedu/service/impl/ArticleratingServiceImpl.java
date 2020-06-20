package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.pojo.Articlerating;
import com.neuedu.mapper.ArticleratingMapper;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.neuedu.service.IArticleratingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.util.UserLocalThread;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-04-15
 */
@Service
public class ArticleratingServiceImpl extends ServiceImpl<ArticleratingMapper, Articlerating> implements IArticleratingService {

    @Resource
    ArticleratingMapper articleratingMapper;

    @Override
    public BigDecimal averageScore(Integer articleId) {
        QueryWrapper<Articlerating> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("article_id",articleId);
        List<Articlerating> articleratingList=articleratingMapper.selectList(queryWrapper);
        System.out.println(articleratingList);
        BigDecimal score= new BigDecimal("0");
        if(articleratingList!=null){
            for(Articlerating articlerating : articleratingList){
                score=score.add(new BigDecimal(articlerating.getRating()));
                System.out.println(score);
            }
            if(!score.equals(BigDecimal.valueOf(0))){
                score=score.divide(BigDecimal.valueOf(articleratingList.size()),2,RoundingMode.CEILING);
            }
        }
        return score;
    }

    @Override
    public Result rateArticle(Integer articleId, String socre) {
        User user= UserLocalThread.getUser();
        QueryWrapper<Articlerating> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.eq("article_id",articleId);
        Articlerating articlerating = articleratingMapper.selectOne(queryWrapper);
        if(articlerating==null){
            articlerating=new Articlerating();
            articlerating.setArticleId(String.valueOf(articleId));
            articlerating.setRating(socre);
            articlerating.setUserId(Long.valueOf(user.getId()));
            articleratingMapper.insert(articlerating);
        }else{
            articlerating.setRating(socre);
            articlerating.setRatetime(Timestamp.valueOf(LocalDateTime.now()));
            articleratingMapper.update(articlerating,queryWrapper);
        }
        return new Result(1,"success","success");
    }

    @Override
    public BigDecimal myScore(Integer articleId) {
        User user=UserLocalThread.getUser();
        QueryWrapper<Articlerating> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("article_id",articleId);
        queryWrapper.eq("user_id",user.getId());
        Articlerating articlerating = articleratingMapper.selectOne(queryWrapper);
        if (articlerating!=null){
            return new BigDecimal(articlerating.getRating());
        }
        return new BigDecimal("0.0");
    }

}
