package store.ckin.coupon.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
public interface CouponService {

    void createCouponTemplate(CreateCouponRequestDto couponRequestDto);

    Page<GetCouponResponseDto> getCouponListByMember(Pageable pageable, Long memberId);

    Page<GetCouponResponseDto> getBirthCouponAll(Pageable pageable);

    Page<GetCouponResponseDto> getBookCouponAll(Pageable pageable);

    Page<GetCouponResponseDto> getCategoryCouponAll(Pageable pageable);
}
