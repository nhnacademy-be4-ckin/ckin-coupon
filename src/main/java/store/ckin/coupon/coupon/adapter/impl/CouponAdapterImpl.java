package store.ckin.coupon.coupon.adapter.impl;

import static store.ckin.coupon.util.AdapterHeaderUtil.getHttpHeaders;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.ckin.coupon.config.properties.GatewayProperties;
import store.ckin.coupon.coupon.adapter.CouponAdapter;

/**
 * CouponAdapterImpl
 *
 * @author : 이가은
 * @version : 2024. 03. 11
 */
@Component
@RequiredArgsConstructor
public class CouponAdapterImpl implements CouponAdapter {
    private final RestTemplate restTemplate;
    private final GatewayProperties gatewayProperties;


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getCategoryIds(List<Long> bookIds) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(getHttpHeaders());

        String uri =
                UriComponentsBuilder.fromHttpUrl(gatewayProperties.getGatewayUri() + "/api/categories/parentIds")
                        .queryParam("bookIds", bookIds)
                        .encode()
                        .toUriString();

        ResponseEntity<List<Long>> exchange = restTemplate.exchange(uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody();
    }
}
