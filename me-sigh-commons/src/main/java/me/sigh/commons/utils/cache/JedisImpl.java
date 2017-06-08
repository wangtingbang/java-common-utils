package me.sigh.commons.utils.cache;

import redis.clients.jedis.Jedis;

/**
 * Created by wangtingbang on 2017/3/2.
 */
public class JedisImpl {
  public static void main(String[] argv){
//    Jedis jedis = new Jedis("localhost", 6379);
    String a = "aa";
    System.out.printf("%d, %d\n", a.hashCode(), (int)a.toCharArray()[0]);
  }
}
