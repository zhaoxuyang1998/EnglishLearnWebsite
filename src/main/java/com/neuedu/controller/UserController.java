package com.neuedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.config.PassToken;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
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
@RequestMapping("/user")
public class UserController {
    @Resource
    IUserService userService;

    @GetMapping("/one")
    public User one(Integer id){
        return userService.getById(id);
    }

    @GetMapping("/list")
    public List<User> list(Integer roleNum){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_num",roleNum);
        queryWrapper.ne("status",2);
        return userService.list(queryWrapper);
    }
    @PostMapping("/login")
    @PassToken
    public Result login (User user){
        try{
            //String password=new Md5Hash(user.getPassword(),user.getUsername(),3).toString();
            //UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(user.getUsername(),user.getPassword());
            //Subject subject = SecurityUtils.getSubject();
            //subject.login(usernamePasswordToken);
            /*
            登陆成功
             */
            return userService.login(user);
        }catch (Exception e){
            /*
            登陆失败
             */
            return new Result(0,"登录失败","login fail");
        }

    }

    @GetMapping("checkExist")
    @PassToken
    public Result checkExist(User user){return userService.checkExist(user);}

    @PostMapping("/register")
    @PassToken
    public Result register(User user) throws IOException {
        System.out.println("get in register");
        return userService.register(user);
    }

    @GetMapping("changeTag")
    public Result changeTag(Integer tagNum) throws IOException {
        return userService.changeTag(tagNum);
    }

    @PostMapping("/changePassword")
    public Result changePassword(String pass,String changedPass){
        System.out.println(pass);
        System.out.println(changedPass);
        return userService.changePassword(pass,changedPass);
    }

    @GetMapping("allAdminRegReq")
    public Result allAdminRegReq(){
        return userService.allAdminRegReq();
    }

    @GetMapping("verifyAdmin")
    public Result verifyAdmin(Integer id,Integer res) {
        return userService.verifyAdmin(id,res);
    }
    @GetMapping("changeStatus")
    public Result changeStatus(Integer id,Integer status){
        return userService.changeStatus(id,status);
    }
}
