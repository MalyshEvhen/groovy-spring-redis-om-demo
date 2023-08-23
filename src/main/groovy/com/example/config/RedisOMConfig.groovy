package com.example.config


import com.redis.om.spring.annotations.EnableRedisDocumentRepositories
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRedisDocumentRepositories(basePackages = ['com.example.*'])
class RedisOMConfig {}
