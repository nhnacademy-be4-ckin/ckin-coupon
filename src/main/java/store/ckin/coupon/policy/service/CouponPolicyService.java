package store.ckin.coupon.policy.service;

import store.ckin.coupon.policy.dto.request.CreateCouponPolicyRequestDto;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
public interface CouponPolicyService {

    void createCouponPolicy(CreateCouponPolicyRequestDto policyRequestDto);
}
