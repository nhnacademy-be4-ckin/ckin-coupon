package store.ckin.coupon.coupontemplate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
public interface CouponTemplateService {

    void createCouponTemplate(CreateCouponTemplateRequestDto couponRequestDto);

    Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable);

    GetCouponTemplateResponseDto getCouponTemplate(Long couponTemplateId);

    void updateCouponTemplate(Long couponTemplateId, CreateCouponTemplateRequestDto couponRequestDto);

    void deleteCouponTemplate(Long couponTemplateId);
}
