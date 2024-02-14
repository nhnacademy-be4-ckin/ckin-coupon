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
 * CouponController
 *
 * @author : gaeun
 * @version : 2024. 02. 09
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;


    @GetMapping
    public ResponseEntity<Page<GetCouponResponseDto>> getAllCoupon(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<GetCouponResponseDto> content = couponService.getCouponList(pageable);
        return ResponseEntity.ok().body(content);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<GetCouponResponseDto> getCouponById(@PathVariable("couponId") Long couponId) {
        GetCouponResponseDto content = couponService.getCoupon(couponId);
        return ResponseEntity.ok().body(content);
    }


    @PostMapping
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CreateCouponRequestDto couponRequestDto) {
        couponService.createCoupon(couponRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<Void> updateCoupon(@PathVariable("couponId") Long couponId,
                                             @Valid @RequestBody CreateCouponRequestDto couponRequestDto) {
        couponService.updateCoupon(couponId, couponRequestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable("couponId") Long couponId) {
        couponService.deleteCoupon(couponId);

        return ResponseEntity.ok().build();
    }
}
