package store.ckin.coupon.coupontemplate.controller;

import lombok.RequiredArgsConstructor;
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
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/couponTemplate")
public class CouponTemplateController {
    private final CouponTemplateService couponTemplateService;


    @GetMapping
    public ResponseEntity<Page<GetCouponTemplateResponseDto>> getAllCouponTemplate(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<GetCouponTemplateResponseDto> content = couponTemplateService.getCouponTemplateList(pageable);
        return ResponseEntity.ok().body(content);
    }

    @GetMapping("/{couponTemplateId}")
    public ResponseEntity<GetCouponTemplateResponseDto> getCouponTemplateById(@PathVariable("couponTemplateId") Long couponTemplateId) {
        GetCouponTemplateResponseDto content = couponTemplateService.getCouponTemplate(couponTemplateId);
        return ResponseEntity.ok().body(content);
    }

    @GetMapping("/birth")
    public ResponseEntity<List<GetCouponTemplateResponseDto>> getBirthCouponTemplate() {
        List<GetCouponTemplateResponseDto> content = couponTemplateService.getBirthCouponTemplate();

        return ResponseEntity.ok().body(content);
    }


    @PostMapping
    public ResponseEntity<Void> createCouponTemplate(@Valid @RequestBody CreateCouponTemplateRequestDto couponRequestDto) {
        couponTemplateService.createCouponTemplate(couponRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{couponTemplateId}")
    public ResponseEntity<Void> updateCouponTemplate(@PathVariable("couponTemplateId") Long couponTemplateId,
                                             @Valid @RequestBody CreateCouponTemplateRequestDto couponRequestDto) {
        couponTemplateService.updateCouponTemplate(couponTemplateId, couponRequestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{couponTemplateId}")
    public ResponseEntity<Void> deleteCouponTemplate(@PathVariable("couponTemplateId") Long couponTemplateId) {
        couponTemplateService.deleteCouponTemplate(couponTemplateId);

        return ResponseEntity.ok().build();
    }
}
