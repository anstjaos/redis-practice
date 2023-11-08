package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.set("users:300:email", "kim@fastcampus.co.kr");
                jedis.set("users:300:name", "kim 00");
                jedis.set("users:300:age", "100");

                var userEmail = jedis.get("users:300:email");
                System.out.println(userEmail);

                List<String> userInfo = jedis.mget("users:300:email", "users:300:name", "users:300:age");
                userInfo.forEach(System.out::println);

                long counter = jedis.incr("counter");
                System.out.println(counter);

                Pipeline pipeline = jedis.pipelined();
                pipeline.set("users:400:email", "greg@fastcampus.co.kr");
                pipeline.set("users:400:name", "greg");
                pipeline.set("users:400:age", "15");

                List<Object> objects = pipeline.syncAndReturnAll();
                objects.forEach(i -> System.out.println(i.toString()));
            }
        }
    }
}