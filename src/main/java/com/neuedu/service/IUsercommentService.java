package com.neuedu.service;

import com.neuedu.pojo.Result;
import com.neuedu.pojo.Usercomment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-04-15
 */
public interface IUsercommentService extends IService<Usercomment> {

    List<Usercomment> commentByArticleId(Integer articleId);

    Result addComment(Usercomment usercomment,Integer typeNum) throws IOException;

}
