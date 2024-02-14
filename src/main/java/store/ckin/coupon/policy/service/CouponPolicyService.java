package store.ckin.coupon.policy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ckin.coupon.policy.dto.request.CreateCouponPolicyRequestDto;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
public interface CouponPolicyService {

    void createCouponPolicy(CreateCouponPolicyRequestDto policyRequestDto);

    Page<GetCouponPolicyResponseDto> getCouponPolicyList(Pageable pageable);
}
