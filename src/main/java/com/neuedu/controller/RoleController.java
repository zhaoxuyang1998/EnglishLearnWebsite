package com.neuedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.config.PassToken;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.Role;
import com.neuedu.pojo.User;
import com.neuedu.service.impl.RoleServiceImpl;
import com.neuedu.util.UserLocalThread;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    RoleServiceImpl roleService;

    @GetMapping("/all")
    @PassToken
    public Result all(){
        return roleService.all();
    }


}
