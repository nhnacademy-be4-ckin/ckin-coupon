package store.ckin.coupon.policy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ckin.coupon.policy.dto.request.CreateCouponPolicyRequestDto;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;

import java.util.List;

/**
 * CouponPolicyService
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
public interface CouponPolicyService {

    /**
     * 쿠폰 정책을 생성하는 메서드 입니다.
     *
     * @param policyRequestDto 쿠폰 정책 생성 DTO
     */
    void createCouponPolicy(CreateCouponPolicyRequestDto policyRequestDto);

    /**
     * 쿠폰 정책 목록을 반환하는 메서드 입니다.
     *
     * @return 쿠폰 정책 목록
     */
    List<GetCouponPolicyResponseDto> getCouponPolicyList();
}
