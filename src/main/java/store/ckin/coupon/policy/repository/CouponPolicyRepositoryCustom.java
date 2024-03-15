package store.ckin.coupon.policy.repository;

import java.util.List;
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
    /**
     * 쿠폰 정책을 반환하는 메서드 입니다.
     *
     * @return 쿠폰 정책 목록
     */
    List<GetCouponPolicyResponseDto> getCouponPolicy();
}
