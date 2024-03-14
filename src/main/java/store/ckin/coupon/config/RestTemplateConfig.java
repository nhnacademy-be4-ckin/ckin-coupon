package store.ckin.coupon.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Rest Template 설정 클래스입니다.
 *
 * @author 정승조
 * @version 2024. 02. 12.
 */

@Configuration
public class RestTemplateConfig {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofSeconds(5L))
                .setConnectTimeout(Duration.ofSeconds(5L))
                .build();
    }
}
