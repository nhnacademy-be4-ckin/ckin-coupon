package store.ckin.coupon.policy.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ckin.coupon.policy.dto.request.CreateCouponPolicyRequestDto;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;
import store.ckin.coupon.policy.exception.CouponCodeNotFoundException;
import store.ckin.coupon.policy.model.CouponCode;
import store.ckin.coupon.policy.model.CouponPolicy;
import store.ckin.coupon.policy.repository.CouponCodeRepository;
import store.ckin.coupon.policy.repository.CouponPolicyRepository;
import store.ckin.coupon.policy.service.CouponPolicyService;

/**
 * CouponPolicyServiceImpl
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
@Service
@RequiredArgsConstructor
public class CouponPolicyServiceImpl implements CouponPolicyService {
    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponCodeRepository couponCodeRepository;

    /**
     * {@inheritDoc}
     *
     * @param policyRequestDto 쿠폰 정책 생성 DTO
     */
    @Override
    @Transactional
    public void createCouponPolicy(CreateCouponPolicyRequestDto policyRequestDto) {
        CouponCode couponCode = couponCodeRepository.findById(policyRequestDto.getCouponCodeId())
                .orElseThrow(CouponCodeNotFoundException::new);

        couponPolicyRepository.save(CouponPolicy.builder()
                .couponCode(couponCode)
                .minOrderPrice(policyRequestDto.getMinOrderPrice())
                .discountPrice(policyRequestDto.getDiscountPrice())
                .discountRate(policyRequestDto.getDiscountRate())
                .maxDiscountPrice(policyRequestDto.getMaxDiscountPrice())
                .state(true)
                .build());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<GetCouponPolicyResponseDto> getCouponPolicyList() {
        return couponPolicyRepository.getCouponPolicy();
    }
}
