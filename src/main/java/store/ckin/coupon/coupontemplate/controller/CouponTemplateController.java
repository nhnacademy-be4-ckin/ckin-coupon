package store.ckin.coupon.coupontemplate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.service.CouponTemplateService;

import javax.validation.Valid;
import java.util.List;

/**
 * CouponTemplateController
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/couponTemplate")
public class CouponTemplateController {
    private final CouponTemplateService couponTemplateService;

    /**
     * 쿠폰 타입별로 쿠폰 템플릿 목록을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param typeId   쿠폰 템플릿 타입 ID
     * @return 쿠폰 템플릿 목록
     */
    @GetMapping
    public ResponseEntity<Page<GetCouponTemplateResponseDto>> getAllCouponTemplate(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                                                                   @RequestParam("type") Long typeId) {
        Page<GetCouponTemplateResponseDto> content = couponTemplateService.getCouponTemplateList(pageable, typeId);
        return ResponseEntity.ok().body(content);
    }

    /**
     * zn
     */
    /**
     * 쿠폰 템플릿을 조회하는 메서드 입니다.
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @return 쿠폰 템플릿
     */
    @GetMapping("/{couponTemplateId}")
    public ResponseEntity<GetCouponTemplateResponseDto> getCouponTemplateById(@PathVariable("couponTemplateId") Long couponTemplateId) {
        GetCouponTemplateResponseDto content = couponTemplateService.getCouponTemplate(couponTemplateId);
        return ResponseEntity.ok().body(content);
    }

    /**
     * 쿠폰 템플릿을 셍성하는 메서드 입니다.
     *
     * @param couponRequestDto 쿠폰 템플릿 요청 DTO
     */
    @PostMapping
    public ResponseEntity<Void> createCouponTemplate(@Valid @RequestBody CreateCouponTemplateRequestDto couponRequestDto) {
        couponTemplateService.createCouponTemplate(couponRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    /**
     * 쿠폰 템플릿을 수정하는 메서드 입니다.
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @param couponRequestDto 쿠폰 템플릿 요청 DTO
     */
    @PutMapping("/{couponTemplateId}")
    public ResponseEntity<Void> updateCouponTemplate(@PathVariable("couponTemplateId") Long couponTemplateId,
                                                     @Valid @RequestBody CreateCouponTemplateRequestDto couponRequestDto) {
        couponTemplateService.updateCouponTemplate(couponTemplateId, couponRequestDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 쿠폰 템플릿을 삭제하는 메서드 입니다.
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     */
    @DeleteMapping("/{couponTemplateId}")
    public ResponseEntity<Void> deleteCouponTemplate(@PathVariable("couponTemplateId") Long couponTemplateId) {
        couponTemplateService.deleteCouponTemplate(couponTemplateId);

        return ResponseEntity.ok().build();
    }
}
