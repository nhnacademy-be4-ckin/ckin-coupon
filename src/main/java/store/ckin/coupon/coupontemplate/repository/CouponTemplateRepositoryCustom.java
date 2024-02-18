package store.ckin.coupon.coupontemplate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * CouponPolicyRepositoryCustom
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
@NoRepositoryBean
public interface CouponTemplateRepositoryCustom {
    Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable);

    Optional<GetCouponTemplateResponseDto> getCouponTemplate(Long couponId);

    Page<GetCouponTemplateResponseDto> getBirthCouponTemplate(Pageable pageable);

    Page<GetCouponTemplateResponseDto> getBookCouponTemplate(Pageable pageable);

    Page<GetCouponTemplateResponseDto> getCategoryTemplate(Pageable pageable);
}
