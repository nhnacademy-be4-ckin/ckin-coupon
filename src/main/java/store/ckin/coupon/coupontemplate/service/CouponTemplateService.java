package store.ckin.coupon.coupontemplate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;

import java.util.List;

/**
 * CouponTemplateService
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
public interface CouponTemplateService {

    /**
     * 쿠폰 템플릿을 생성하는 메서드 입니다.
     *
     * @param couponRequestDto the coupon request dto
     */
    void createCouponTemplate(CreateCouponTemplateRequestDto couponRequestDto);

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
     * @param couponTemplateId the coupon template id
     * @return the coupon template
     */
    GetCouponTemplateResponseDto getCouponTemplate(Long couponTemplateId);

    /**
     * 아이디로 쿠폰 템플릿을 수정하는 메서드 입니다.
     *
     * @param couponTemplateId the coupon template id
     * @param couponRequestDto the coupon request dto
     */
    void updateCouponTemplate(Long couponTemplateId, CreateCouponTemplateRequestDto couponRequestDto);

    /**
     * 아이디로 쿠폰 템플릿을 삭제하는 메서드 입니다.
     *
     * @param couponTemplateId the coupon template id
     */
    void deleteCouponTemplate(Long couponTemplateId);

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
     * @return the category coupon template
     */
    Page<GetCouponTemplateResponseDto> getCategoryCouponTemplate(Pageable pageable);
}
