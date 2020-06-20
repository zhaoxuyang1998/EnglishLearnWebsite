package com.neuedu.service;

import com.neuedu.mapper.TagMapper;
import com.neuedu.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-03-27
 */
public interface ITagService extends IService<Tag> {

    List<Integer> searchByTag(Integer tag);

}
