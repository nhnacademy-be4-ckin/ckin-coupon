package store.ckin.coupon.coupon.adapter.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.ckin.coupon.config.properties.GatewayProperties;
import store.ckin.coupon.coupon.adapter.CouponAdapter;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 03. 11
 */
@Component
@RequiredArgsConstructor
public class CouponAdapterImpl implements CouponAdapter {
    private final RestTemplate restTemplate;
    private final GatewayProperties gatewayProperties;


    @Override
    public List<Long> getCategoryIds(List<Long> bookIds) {

        return null;
    }
}
