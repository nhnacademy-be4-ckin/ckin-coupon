package store.ckin.coupon.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.service.CouponService;

import javax.validation.Valid;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponTemplateService;

    @PostMapping
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CreateCouponRequestDto couponRequestDto) {
        couponTemplateService.createCouponTemplate(couponRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

}
