package store.ckin.coupon.policy.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.ckin.coupon.policy.dto.request.CreateCouponPolicyRequestDto;
import store.ckin.coupon.policy.service.CouponPolicyService;

import javax.validation.Valid;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 09
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/couponPolicy")
public class CouponPolicyController {
    private final CouponPolicyService couponPolicyService;

    @PostMapping
    public ResponseEntity<Void> createCouponPolicy(@Valid @RequestBody CreateCouponPolicyRequestDto policyRequestDto) {
        couponPolicyService.createCouponPolicy(policyRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
