package com.jjstudio.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class JedisService {

    @Bean
    public Jedis getJedis() {
        return new Jedis("jjstudio_jedis", 6379);
    }

}
