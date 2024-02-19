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
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;

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
    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CreateCouponRequestDto couponRequestDto) {
        couponService.createCouponTemplate(couponRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Page<GetCouponResponseDto>> getCouponByMember(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                                                        @PathVariable("memberId") Long memberId) {
        Page<GetCouponResponseDto> content = couponService.getCouponListByMember(pageable, memberId);

        return ResponseEntity.ok().body(content);
    }

}
