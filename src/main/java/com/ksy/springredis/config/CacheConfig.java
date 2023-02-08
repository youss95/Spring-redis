package com.ksy.springredis.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Slf4j
@Configuration
public class CacheConfig {

    @Bean
    public RedisConnectionFactory cacheRedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("127.0.0.1", 6379);
            configuration.setDatabase(0); // 0 ~ 15 번 선택가능
            configuration.setUsername("ksy");
            configuration.setPassword("1234");

            //커넥션 생성 최대 시간 10초
            final SocketOptions socketOptions = SocketOptions.builder().
                    connectTimeout(Duration.ofSeconds(10)).build();
            final ClientOptions clientOptions = ClientOptions.builder().
                    socketOptions(socketOptions).build();

        LettuceClientConfiguration lettuceClientConfiguration =
                LettuceClientConfiguration.builder()
                        .clientOptions(clientOptions)
                        .commandTimeout(Duration.ofSeconds(5)) //커넥션 생성되고 레디스 응답 받는 시간 5초
                        .shutdownTimeout(Duration.ZERO) //종료 될때까지 기다리는 시간
                        .build();

        return new LettuceConnectionFactory(configuration, lettuceClientConfiguration);
    }
}
