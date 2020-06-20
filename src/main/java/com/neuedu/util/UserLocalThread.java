package com.neuedu.util;

import com.neuedu.pojo.User;

public class UserLocalThread {
    private static ThreadLocal<User> local = new ThreadLocal<>();
    /**
     * 设置用户信息
     *
     * @param user
     */
    public static void setUser( User user )
    {
        System.out.println("LocalThread set User");
        local.set( user );
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public static User getUser()
    {
        System.out.println( "当前线程：" + Thread.currentThread().getName() );
        return local.get();
    }

    /**
     * 移除用户信息
     *
     * @return
     */
    public static void remove() {
        System.out.println("LocalThread remove User");
        local.remove();
    }

}
