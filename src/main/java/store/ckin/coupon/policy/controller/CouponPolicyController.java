package store.ckin.coupon.policy.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ckin.coupon.policy.dto.request.CreateCouponPolicyRequestDto;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;
import store.ckin.coupon.policy.service.CouponPolicyService;

import javax.validation.Valid;

/**
 * CouponPolicyController
 *
 * @author : gaeun
 * @version : 2024. 02. 09
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/couponPolicy")
public class CouponPolicyController {
    private final CouponPolicyService couponPolicyService;


    @GetMapping
    public ResponseEntity<Page<GetCouponPolicyResponseDto>> getAllCouponPolicy(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<GetCouponPolicyResponseDto> content = couponPolicyService.getCouponPolicyList(pageable);
        return ResponseEntity.ok().body(content);
    }


    @PostMapping
    public ResponseEntity<Void> createCouponPolicy(@Valid @RequestBody CreateCouponPolicyRequestDto policyRequestDto) {
        couponPolicyService.createCouponPolicy(policyRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
