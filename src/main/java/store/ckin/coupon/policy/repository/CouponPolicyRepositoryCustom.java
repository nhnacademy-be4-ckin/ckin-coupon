package store.ckin.coupon.policy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;

import java.util.List;

/**
 * CouponPolicyRepositoryCustom
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
@NoRepositoryBean
public interface CouponPolicyRepositoryCustom {
    /**
     * 쿠폰 정책을 반환하는 메서드 입니다.
     *
     * @return the coupon policy
     */
    List<GetCouponPolicyResponseDto> getCouponPolicy();
}
