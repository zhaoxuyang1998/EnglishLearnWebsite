package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.factory.ConstantFactory;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.neuedu.mapper.UserMapper;
import com.neuedu.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.util.ConstantDict;
import com.neuedu.util.UserLocalThread;
import org.springframework.data.redis.core.StringRedisTemplate;
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
 * @since 2020-03-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    UserMapper userMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(User user) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",user.getUsername());
        User queryUser=userMapper.selectOne(wrapper);
        if(queryUser==null){
            return new Result(0,null,"wrong username");
        }else{
            if(queryUser.getStatus()==2){
                return new Result(0,null,"账号审核中");
            }
            else if(queryUser.getStatus()==0){
                return new Result(0,null,"账号被禁用");
            }
            if(!queryUser.getPassword().equals(user.getPassword())){
                return new Result(0,null,"wrong password");
            }else{
                return new Result(1,queryUser,"login success");
            }
        }
    }

    @Override
    public Result checkExist(User user) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",user.getUsername());
        User queryUser=userMapper.selectOne(wrapper);
        if(queryUser==null){
            return new Result(0,"yes","username can be used");
        }else {
            return new Result(0,"no","username has been used");
        }
    }

    @Override
    public Result register(User user) throws IOException {
        if(user.getUsername()==null||user.getPassword()==null||user.getRoleNum()==null){
            return new Result(1,"no","user info uncomplete");
        }
        user.setSalt("salt");
        user.setRolename(ConstantFactory.me().getRoleNameByNum(user.getRoleNum()));
        if (user.getRolename().equals("内容管理员")){
            user.setStatus(2);
        }
        user.setTag(ConstantFactory.me().getDictByKeywordAndNum(ConstantDict.STUDY_MAJOR,user.getTagNum()));
        userMapper.insert(user);
        return new Result(1,"yes","user register complete");
    }

    @Override
    public Result changeTag(Integer tagNum) throws IOException {
        User user= UserLocalThread.getUser();
        user.setTagNum(tagNum);
        user.setTag(ConstantFactory.me().getDictByKeywordAndNum(ConstantDict.STUDY_MAJOR,user.getTagNum()));
        userMapper.updateById(user);
        UserLocalThread.setUser(user);
        return new Result(1,user,"success");
    }

    @Override
    public Result changePassword(String pass, String changedPass) {
        User user=UserLocalThread.getUser();
        User userFromDatabase=userMapper.selectById(user.getId());
        if(userFromDatabase.getPassword().equals(pass)){
            userFromDatabase.setPassword(changedPass);
            userMapper.updateById(userFromDatabase);
            stringRedisTemplate.delete(userFromDatabase.getUsername());
            return new Result(1,userFromDatabase,"success");
        }else {
            return new Result(0, "wrong password", "wrong password");
        }
    }

    @Override
    public Result allAdminRegReq() {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",2);
        List<User> list=userMapper.selectList(queryWrapper);
        return new Result(1,list,"success");
    }


    @Override
    public Result verifyAdmin(Integer id, Integer res) {
        User user=userMapper.selectById(id);
        user.setStatus(res);
        userMapper.updateById(user);
        return new Result(1,"success","success");
    }

    @Override
    public Result changeStatus(Integer id, Integer status) {
        User user=userMapper.selectById(id);
        user.setStatus(status);
        userMapper.updateById(user);
        return new Result(1,"success","success");
    }

}
