package com.neuedu.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class MyAop {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    ObjectMapper objectMapper;

    /*
    查询缓存
     */

    public String passwordaop(ProceedingJoinPoint proceedingJoinPoint){
        String password=null;
        if(stringRedisTemplate.hasKey("password")) {
            System.out.println("redis has password");
            password=stringRedisTemplate.opsForValue().get("password");
            stringRedisTemplate.delete("password");
            System.out.println("redis has deleted password");
            //反序列化
            //objectMapper.readValue(对象,数据类型.class)
        }else{
            System.out.println("redis doesn't have password");
            try {
                password=(String)proceedingJoinPoint.proceed();
                //objectMapper序列化
                //objectMapper.writeValueAsString(对象);
                stringRedisTemplate.opsForValue().set("password","123456",60, TimeUnit.SECONDS);

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return password;
    }
    /*
    更新缓存
     */

    public Object updateaop(ProceedingJoinPoint proceedingJoinPoint){
        Object result= null;
        try {
            result = proceedingJoinPoint.proceed();
            System.out.println("进入update");
            String methodName=proceedingJoinPoint.getSignature().getName();
            if(methodName.equals("selectOne")){
                if(stringRedisTemplate.hasKey("password")){
                    System.out.println("update redis password");
                    stringRedisTemplate.delete("password");
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    @Around("execution(* com.neuedu.controller.UserController.login(..))")
    public Result loginAop(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Result result=(Result)proceedingJoinPoint.proceed();
        Map<String,Object> map=new HashMap<>();
        if(result.getObj()!=null){
            User user=(User)result.getObj();
            String token=JWT.create().withAudience(user.getUsername()).sign(Algorithm.HMAC256(user.getPassword()));
            map.put("user",user);
            map.put("token",token);
            //username为key,user对象转成json为value存到redis中
            String userjson=objectMapper.writeValueAsString(user);
            stringRedisTemplate.opsForValue().set(user.getUsername(),userjson,30,TimeUnit.MINUTES);
            return new Result(1,map,"login success");
        }
        return result;
    }


}
