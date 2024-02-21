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
 * CouponController
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;

    /**
     * 쿠폰을 생성하는 메서드 입니다.
     *
     * @param couponRequestDto the coupon request dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CreateCouponRequestDto couponRequestDto) {
        couponService.createCoupon(couponRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<GetCouponResponseDto>> getAllCouponList(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<GetCouponResponseDto> content = couponService.getAllCouponList(pageable);

        return ResponseEntity.ok().body(content);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<GetCouponResponseDto> getCouponByCouponId(@PathVariable("couponId")Long couponId) {
        GetCouponResponseDto content = couponService.getCouponByCouponId(couponId);

        return ResponseEntity.ok().body(content);
    }

    /**
     * 특정 회원의 사용된 쿠폰을 조회하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @param memberId the member id
     * @return the used coupon by member
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<Page<GetCouponResponseDto>> getAllCouponByMember(@PageableDefault(page = 0, size = 5) Pageable pageable, @PathVariable("memberId") Long memberId) {
        Page<GetCouponResponseDto> content = couponService.getCouponByMember(pageable, memberId);

        return ResponseEntity.ok().body(content);
    }

    @GetMapping("/used/{memberId}")
    public ResponseEntity<Page<GetCouponResponseDto>> getUsedCouponByMember(@PageableDefault(page = 0, size = 5) Pageable pageable, @PathVariable("memberId") Long memberId) {
        Page<GetCouponResponseDto> content = couponService.getUsedCouponByMember(pageable, memberId);

        return ResponseEntity.ok().body(content);
    }

    /**
     * 특정 회원의 사용중인 쿠폰을 조회하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @param memberId the member id
     * @return the un used coupon by member
     */
    @GetMapping("/unUsed/{memberId}")
    public ResponseEntity<Page<GetCouponResponseDto>> getUnUsedCouponByMember(@PageableDefault(page = 0, size = 5) Pageable pageable, @PathVariable("memberId") Long memberId) {
        Page<GetCouponResponseDto> content = couponService.getUnUsedCouponByMember(pageable, memberId);

        return ResponseEntity.ok().body(content);
    }

    /**
     * 생일 쿠폰 목록을 반환하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @return the birth coupon all
     */
    @GetMapping("/birth")
    public ResponseEntity<Page<GetCouponResponseDto>> getBirthCouponAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<GetCouponResponseDto> content = couponService.getBirthCouponAll(pageable);

        return ResponseEntity.ok().body(content);
    }

    /**
     * 도서 쿠폰 목록을 반환하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @return the book coupon all
     */
    @GetMapping("/book")
    public ResponseEntity<Page<GetCouponResponseDto>> getBookCouponAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<GetCouponResponseDto> content = couponService.getBookCouponAll(pageable);

        return ResponseEntity.ok().body(content);
    }

    /**
     * 카테고리 쿠폰 목록을 반환하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @return the category coupon all
     */
    @GetMapping("/category")
    public ResponseEntity<Page<GetCouponResponseDto>> getCategoryCouponAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<GetCouponResponseDto> content = couponService.getCategoryCouponAll(pageable);

        return ResponseEntity.ok().body(content);
    }

    /**
     * 쿠폰이 사용됐음을 업데이트 하는 메서드 입니다.
     *
     * @param couponId the coupon id
     * @return the response entity
     */
    @PutMapping("{couponId}")
    public ResponseEntity<Void> updateCouponUsedDate(@PathVariable("couponId") Long couponId) {
        couponService.updateCouponUsedDate(couponId);

        return ResponseEntity.ok().build();
    }

}
