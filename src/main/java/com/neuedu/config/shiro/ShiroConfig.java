//package com.neuedu.config.shiro;
//
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
////@Configuration
//public class ShiroConfig {
//
//    //@Bean
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
//        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//
//
//        /*
//        内置过滤器
//        anon 无需认证（登录）就能访问
//        authc 必须认证才能访问
//        user  如果使用rememberme才能访问
//        perms  该资源必须得到资源权限才能访问
//        role  该资源必须得到角色权限才能访问
//         */
//        Map<String,String> filterMap=new LinkedHashMap<>();
//        //filterMap.put("/user/*","authc");
//        //filterMap.put("user/login","anon");
//        /*
//        当没有验证通过时跳转的地方
//         */
//        //shiroFilterFactoryBean.setLoginUrl("/role/all");
//        /*
//        当没有授权perms时执行的方法
//         */
//        //shiroFilterFactoryBean.setUnauthorizedUrl("role/all");
//       // shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
//        return  shiroFilterFactoryBean;
//    }
//
//
//    //@Bean(name="securityManager")
//    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
//        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
//        securityManager.setRealm(userRealm);
//        return  securityManager;
//    }
//
//
//    //@Bean(name="userRealm")
//    public UserRealm getRealm(){
//        return new UserRealm();
//    }
//}
