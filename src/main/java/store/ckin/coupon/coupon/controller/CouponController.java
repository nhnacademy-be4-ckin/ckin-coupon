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
 * CouponPolicyController
 *
 * @author : gaeun
 * @version : 2024. 02. 09
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;


    @GetMapping("/coupons")
    public ResponseEntity<Page<GetCouponResponseDto>> getAllCouponPolicy(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<GetCouponResponseDto> content = couponService.getCouponList(pageable);
        return ResponseEntity.ok().body(content);
    }


    @PostMapping
    public ResponseEntity<Void> createCouponPolicy(@Valid @RequestBody CreateCouponRequestDto policyRequestDto) {
        couponService.createCouponPolicy(policyRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
