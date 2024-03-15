package store.ckin.coupon.coupontemplate.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;

/**
 * CouponPolicyRepositoryCustom
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
@NoRepositoryBean
public interface CouponTemplateRepositoryCustom {
    /**
     * 모든 쿠폰 템플릿 목록을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param typeId   쿠폰 템플릿 타입 ID
     * @return 쿠폰 템플릿 목록
     */
    Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable, Long typeId);

    /**
     * 아이디로 쿠폰 템플릿을 조회하는 메서드 입니다.
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @return 쿠폰 템플릿
     */
    Optional<GetCouponTemplateResponseDto> getCouponTemplate(Long couponTemplateId);

}
