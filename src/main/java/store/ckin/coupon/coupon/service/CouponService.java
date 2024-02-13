package store.ckin.coupon.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
public interface CouponService {

    void createCouponPolicy(CreateCouponRequestDto couponRequestDto);

//    Page<GetCouponResponseDto> getCouponPolicyList(Pageable pageable);
}
