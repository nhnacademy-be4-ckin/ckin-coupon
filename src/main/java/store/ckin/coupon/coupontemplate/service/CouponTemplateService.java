package store.ckin.coupon.coupontemplate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;

/**
 * CouponTemplateService
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
public interface CouponTemplateService {

    /**
     * 쿠폰 템플릿을 셍성하는 메서드 입니다.
     *
     * @param couponRequestDto 쿠폰 템플릿 요청 DTO
     */
    void createCouponTemplate(CreateCouponTemplateRequestDto couponRequestDto);

    /**
     * 쿠폰 타입별로 쿠폰 템플릿 목록을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param typeId   쿠폰 템플릿 타입 ID
     * @return 쿠폰 템플릿 목록
     */
    Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable, Long typeId);

    /**
     * 쿠폰 템플릿을 조회하는 메서드 입니다.
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @return 쿠폰 템플릿
     */
    GetCouponTemplateResponseDto getCouponTemplate(Long couponTemplateId);

    /**
     * 쿠폰 템플릿을 수정하는 메서드 입니다.
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @param couponRequestDto 쿠폰 템플릿 요청 DTO
     */
    void updateCouponTemplate(Long couponTemplateId, CreateCouponTemplateRequestDto couponRequestDto);

    /**
     * 쿠폰 템플릿을 삭제하는 메서드 입니다.
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     */
    void deleteCouponTemplate(Long couponTemplateId);
}
