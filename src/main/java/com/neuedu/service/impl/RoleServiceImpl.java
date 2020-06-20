package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.Role;
import com.neuedu.mapper.RoleMapper;
import com.neuedu.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    RoleMapper roleMapper;

    @Override
    public Result all() {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("number","rolename").getSqlSelect();
        Object res=roleMapper.selectMaps(queryWrapper);
        return new Result(1,res,"all Role list");
    }
}
