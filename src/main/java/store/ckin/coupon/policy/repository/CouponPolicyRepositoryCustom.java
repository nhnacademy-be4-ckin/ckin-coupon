package store.ckin.coupon.policy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;

/**
 * CouponPolicyRepositoryCustom
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
@NoRepositoryBean
public interface CouponPolicyRepositoryCustom {
    Page<GetCouponPolicyResponseDto> getCouponPolicy(Pageable pageable);
}
