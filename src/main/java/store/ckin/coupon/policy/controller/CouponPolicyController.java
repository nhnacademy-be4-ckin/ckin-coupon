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
import java.util.List;

/**
 * CouponPolicyController
 *
 * @author : gaeun
 * @version : 2024. 02. 09
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/couponPolicy")
public class CouponPolicyController {
    private final CouponPolicyService couponPolicyService;


    /**
     * 모든 쿠폰 정책 목록을 반환하는 메서드 입니다.
     *
     * @return the all coupon policy
     */
    @GetMapping
    public ResponseEntity<List<GetCouponPolicyResponseDto>> getAllCouponPolicy() {
        List<GetCouponPolicyResponseDto> content = couponPolicyService.getCouponPolicyList();
        return ResponseEntity.ok().body(content);
    }

    /**
     * 쿠폰 정책을 생성하는 메서드 입니다.
     *
     * @param policyRequestDto the policy request dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Void> createCouponPolicy(@Valid @RequestBody CreateCouponPolicyRequestDto policyRequestDto) {
        couponPolicyService.createCouponPolicy(policyRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
