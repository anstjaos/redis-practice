package org.example;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.params.GeoSearchParam;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (var jedis = jedisPool.getResource()) {
                // geo add
                jedis.geoadd("stores2:geo", 127.02985530619755, 37.49911212874, "some1");
                jedis.geoadd("stores2:geo", 127.0333352287619, 37.491921163986234, "some2");

                // geo dis
                System.out.println(jedis.geodist("stores2:geo", "some1", "some2"));

                // geo search
                var radiusResponseList = jedis.geosearch("stores2:geo",
                        new GeoCoordinate(127.033, 37.495),
                        500,
                        GeoUnit.M
                );

                radiusResponseList.forEach(response -> System.out.println(response.getMemberByString()));

                var radiusResponseList1 = jedis.geosearch("stores2:go", new GeoSearchParam()
                        .fromLonLat(new GeoCoordinate(127.033, 37.495))
                        .byRadius(500, GeoUnit.M)
                        .withCoord()
                );

                radiusResponseList1.forEach(response -> {
                    System.out.println(response.getMemberByString());
                    System.out.printf("%f %f%n", response.getCoordinate().getLatitude(), response.getCoordinate().getLongitude());
                });
            }
        }

    }
}