package store.ckin.coupon.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 게이트웨이 포트 번호를 관리하기 위한 클래스입니다.
 *
 * @author 정승조
 * @version 2024. 02. 13.
 */

@Getter
@Setter
@ConfigurationProperties(prefix = "port")
public class GatewayProperties {

    private String gatewayUri;

}