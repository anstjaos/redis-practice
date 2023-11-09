package org.example;

import redis.clients.jedis.JedisPool;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (var jedis = jedisPool.getResource()) {
                // hset
                jedis.hset("users:2:info", "name", "greg2");

                var map = new HashMap<String, String>();
                map.put("email", "greg2@fastcampus.co.kr");
                map.put("phone", "010-XXXX-YYYY");

                jedis.hset("users:2:info", map);

                // hdel
                jedis.hdel("users:2:info", "phone");

                // hget, hgetall
                System.out.println(jedis.hget("users:2:info", "email"));
                var users2Info = jedis.hgetAll("users:2:info");
                users2Info.forEach((k, v) -> System.out.printf("%s %s%n", k, v));

                // hincr
                jedis.hincrBy("users:2:info", "visits", 30);
            }
        }
    }
}