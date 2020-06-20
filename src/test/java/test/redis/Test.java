package test.redis;

import redis.clients.jedis.Jedis;

public class Test {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("182.92.100.82",6379);
        jedis.auth("123456");
        System.out.println(jedis.set("test","123456"));
    }
}
