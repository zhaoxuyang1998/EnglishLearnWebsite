//package com.neuedu.config.shiro;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.neuedu.pojo.User;
//import com.neuedu.service.IUserService;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.Subject;
//
//import javax.annotation.Resource;
//
//public class UserRealm extends AuthorizingRealm {
//
//    //@Resource
//    private IUserService userService;
//
//    /**
//     * 执行授权逻辑
//     * @param principalCollection
//     * @return
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        System.out.println("执行授权逻辑");
//        SimpleAuthorizationInfo simpleAuthenticationInfo=new SimpleAuthorizationInfo();
//        User user= (User) SecurityUtils.getSubject();
//        //simpleAuthenticationInfo.addStringPermission(user.getPerms);
//        return simpleAuthenticationInfo;
//    }
//
//    /**
//     * 执行认证逻辑
//     * @param authenticationToken
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        System.out.println("执行认证逻辑");
//        /*
//        认证用户名和密码
//         */
//        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
//        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("username",token.getUsername());
//        User user=userService.getOne(queryWrapper);
//        if(user==null){
//            return null;
//        }
//        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
//    }
//}
