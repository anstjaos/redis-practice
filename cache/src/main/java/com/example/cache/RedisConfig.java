package com.example.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisConfig {

    @Bean(name = "jedis.pool")
    public JedisPool createJedisPool() {
        var jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setJmxEnabled(false);
        return new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
    }

    @Autowired(required = false)
    void disableAutoJmxRegistration(MBeanExporter mBeanExporter) {
        mBeanExporter.addExcludedBean("jedis.pool");
    }
}
