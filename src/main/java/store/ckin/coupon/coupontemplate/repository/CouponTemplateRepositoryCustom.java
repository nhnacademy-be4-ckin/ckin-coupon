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
    /**
     * 모든 쿠폰 템플릿 목록을 조회하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @return the coupon template list
     */
    Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable);

    /**
     * 아이디로 쿠폰 템플릿을 조회하는 메서드 입니다.
     *
     * @param couponId the coupon id
     * @return the coupon template
     */
    Optional<GetCouponTemplateResponseDto> getCouponTemplate(Long couponId);

    /**
     * 생일 쿠폰 템플릿 목록을 조회하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @return the birth coupon template
     */
    Page<GetCouponTemplateResponseDto> getBirthCouponTemplate(Pageable pageable);

    /**
     * 도서 쿠폰 템플릿 목록을 조회하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @return the book coupon template
     */
    Page<GetCouponTemplateResponseDto> getBookCouponTemplate(Pageable pageable);

    /**
     * 카테고리 쿠폰 템플릿 목록을 조회하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @return the category template
     */
    Page<GetCouponTemplateResponseDto> getCategoryTemplate(Pageable pageable);
}
