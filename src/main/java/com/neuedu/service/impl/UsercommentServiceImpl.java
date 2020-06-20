package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.factory.ConstantFactory;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.neuedu.pojo.Usercomment;
import com.neuedu.mapper.UsercommentMapper;
import com.neuedu.service.IUsercommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.util.ConstantDict;
import com.neuedu.util.UserLocalThread;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
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
public class UsercommentServiceImpl extends ServiceImpl<UsercommentMapper, Usercomment> implements IUsercommentService {

    @Resource
    UsercommentMapper usercommentMapper;

    @Override
    public List<Usercomment> commentByArticleId(Integer articleId) {
        User user= UserLocalThread.getUser();
        QueryWrapper<Usercomment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type_num", ConstantDict.FAV_ARTICLE_TYPE_NUM);
        queryWrapper.eq("content_id",articleId);
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.orderByDesc("id");
        return usercommentMapper.selectList(queryWrapper);
    }

    @Override
    public Result addComment(Usercomment usercomment,Integer typeNum) throws IOException {
        User user=UserLocalThread.getUser();
        usercomment.setTypeNum(typeNum);
        usercomment.setType(ConstantFactory.me().getDictByKeywordAndNum(ConstantDict.FAVORITE,typeNum));
        usercomment.setUserId(user.getId());
        usercommentMapper.insert(usercomment);
        return new Result(1,"success","success");
    }
}
