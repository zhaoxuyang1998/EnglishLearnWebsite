package com.neuedu.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.neuedu.util.UserLocalThread;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class CheckLogin implements HandlerInterceptor {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    ObjectMapper objectMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取登录令牌
        String token=request.getHeader("token");
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod= (HandlerMethod) handler;
            Method method=handlerMethod.getMethod();
            //判断请求的方法是否需要登陆令牌，如果需要登陆令牌，进入方法体，如果不需要，返回true
            if(!method.isAnnotationPresent(PassToken.class)){
                //如果令牌为空，返回错误信息
                if(token==null){
                    response.getWriter().write(objectMapper.writeValueAsString(new Result(-1,null,"miss token")));
                    return false;
                }else {
                    //如果令牌不为空，则从令牌中解析出用户名和密码，并从数据库中查询出的用户信息进行比较
                    try {
                        DecodedJWT decoded= JWT.decode(token);
                        String username=decoded.getAudience().get(0);
                        String userjson=stringRedisTemplate.opsForValue().get(username);
                        User user=objectMapper.readValue(userjson,User.class);
                        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                        jwtVerifier.verify(token);
                        UserLocalThread.setUser(user);
                        System.out.println("set user in thread");
                    //验证不通过会自动抛出异常，返回错误信息
                    }catch (Exception e){
                        response.getWriter().write(objectMapper.writeValueAsString(new Result(-1,null,"token error")));
                        return false;
                    }
                }
            }
        }
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        //System.out.println("afterCompletion");
        UserLocalThread.remove();
    }

}
