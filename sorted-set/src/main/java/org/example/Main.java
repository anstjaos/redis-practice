package org.example;

import redis.clients.jedis.JedisPool;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (var jedis = jedisPool.getResource()) {
                // sorted set
                var scores = new HashMap<String, Double>();
                scores.put("user1", 100.0);
                scores.put("user2", 30.0);
                scores.put("user3", 50.0);
                scores.put("user4", 80.0);
                scores.put("user5", 15.0);

                jedis.zadd("game2:scores", scores);
                // zrange
                var zrange = jedis.zrange("game2:scores", 0, Long.MAX_VALUE);
                zrange.forEach(System.out::println);

                var tuples = jedis.zrangeWithScores("game2:scores", 0, Long.MAX_VALUE);
                tuples.forEach(i -> System.out.printf("%s %f%n", i.getElement(), i.getScore()));
                // zcard
                System.out.println(jedis.zcard("game2:scores"));
                // zincrby
                jedis.zincrby("game2:scores", 100.0, "user5");
            }
        }
    }
}