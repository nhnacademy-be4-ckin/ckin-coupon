package store.ckin.coupon.coupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;

import java.util.Optional;

/**
 * CouponPolicyRepositoryCustom
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
@NoRepositoryBean
public interface CouponRepositoryCustom {
    Page<GetCouponResponseDto> getUsedCouponByMember(Pageable pageable, Long memberId);
    Page<GetCouponResponseDto> getUnUsedCouponByMember(Pageable pageable, Long memberId);

    Page<GetCouponResponseDto> getBirthCouponAll(Pageable pageable);

    Page<GetCouponResponseDto> getBookCouponAll(Pageable pageable);

    Page<GetCouponResponseDto> getCategoryCouponAll(Pageable pageable);
}
