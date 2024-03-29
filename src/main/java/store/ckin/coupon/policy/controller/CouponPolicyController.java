package store.ckin.coupon.policy.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.ckin.coupon.policy.dto.request.CreateCouponPolicyRequestDto;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;
import store.ckin.coupon.policy.service.CouponPolicyService;

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
     * @return 쿠폰 정책 목록
     */
    @GetMapping
    public ResponseEntity<List<GetCouponPolicyResponseDto>> getAllCouponPolicy() {
        List<GetCouponPolicyResponseDto> content = couponPolicyService.getCouponPolicyList();
        return ResponseEntity.ok().body(content);
    }

    /**
     * 쿠폰 정책을 생성하는 메서드 입니다.
     *
     * @param policyRequestDto 쿠폰 정책 요청 DTO
     */
    @PostMapping
    public ResponseEntity<Void> createCouponPolicy(@Valid @RequestBody CreateCouponPolicyRequestDto policyRequestDto) {
        couponPolicyService.createCouponPolicy(policyRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
