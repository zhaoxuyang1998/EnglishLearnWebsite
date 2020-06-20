package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.pojo.Tag;
import com.neuedu.mapper.TagMapper;
import com.neuedu.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-03-27
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Resource
    TagMapper tagMapper;

    @Override
    public List<Integer> searchByTag(Integer tag) {
        List<Integer> list=new ArrayList<>();
        QueryWrapper<Tag> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tag_num",tag);
        List<Tag> tags=tagMapper.selectList(queryWrapper);
        for(Tag taG : tags){
            list.add(taG.getArticleId());
        }
        return list;
    }
}
