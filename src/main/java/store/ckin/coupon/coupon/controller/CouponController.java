package store.ckin.coupon.coupon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;

/**
 * CouponController
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;

    /**
     * 쿠폰을 생성하는 메서드 입니다.
     *
     * @param couponRequestDto 쿠폰 요청 DTO
     */
    @PostMapping
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CreateCouponRequestDto couponRequestDto) {
        couponService.createCoupon(couponRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 쿠폰을 목록을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param typeId   쿠폰 템플릿 타입 ID
     */
    @GetMapping
    public ResponseEntity<Page<GetCouponResponseDto>> getAllCouponList(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                                                       @RequestParam(required = false, name = "typeId") Long typeId) {
        Page<GetCouponResponseDto> content = null;
        if (Objects.isNull(typeId)) {
            content = couponService.getAllCouponList(pageable);
        } else {
            content = couponService.getCouponList(pageable, typeId);
        }
        return ResponseEntity.ok().body(content);
    }

    /**
     * 쿠폰 아이디에 해당하는 쿠폰을 조회하는 메소드 입니다.
     *
     * @param couponId 쿠폰 ID
     * @return 쿠폰 DTO
     */
    @GetMapping("/{couponId}")
    public ResponseEntity<GetCouponResponseDto> getCouponByCouponId(@PathVariable("couponId") Long couponId) {
        GetCouponResponseDto content = couponService.getCouponByCouponId(couponId);

        return ResponseEntity.ok().body(content);
    }

    /**
     * 특정 회원의 모든 쿠폰을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param memberId 회원 ID
     * @return 쿠폰 DTO 목록
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<Page<GetCouponResponseDto>> getAllCouponByMember(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                                                           @PathVariable("memberId") Long memberId) {
        Page<GetCouponResponseDto> content = couponService.getCouponByMember(pageable, memberId);

        return ResponseEntity.ok().body(content);
    }

    /**
     * 특정 회원의 사용된 쿠폰을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param memberId 회원 ID
     * @return 쿠폰 DTO 목록
     */
    @GetMapping("/used/{memberId}")
    public ResponseEntity<Page<GetCouponResponseDto>> getUsedCouponByMember(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                                                            @PathVariable("memberId") Long memberId) {
        Page<GetCouponResponseDto> content = couponService.getUsedCouponByMember(pageable, memberId);

        return ResponseEntity.ok().body(content);
    }

    /**
     * 특정 회원의 미사용된 쿠폰을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param memberId 회원 ID
     * @return 쿠폰 DTO 목록
     */
    @GetMapping("/unUsed/{memberId}")
    public ResponseEntity<Page<GetCouponResponseDto>> getUnUsedCouponByMember(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                                                              @PathVariable("memberId") Long memberId) {
        Page<GetCouponResponseDto> content = couponService.getUnUsedCouponByMember(pageable, memberId);

        return ResponseEntity.ok().body(content);
    }

    /**
     * 쿠폰이 사용됐음을 업데이트 하는 메서드 입니다.
     *
     * @param couponId 쿠폰 ID
     * @return 쿠폰 DTO 목록
     */
    @PutMapping("{couponId}")
    public ResponseEntity<Void> updateCouponUsedDate(@PathVariable("couponId") Long couponId) {
        couponService.updateCouponUsedDate(couponId);

        return ResponseEntity.ok().build();
    }

}
