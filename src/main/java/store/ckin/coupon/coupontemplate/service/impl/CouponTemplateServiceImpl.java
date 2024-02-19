package store.ckin.coupon.coupontemplate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.exception.CouponPolicyNotFoundException;
import store.ckin.coupon.coupontemplate.exception.CouponTemplateNotFoundException;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateRepository;
import store.ckin.coupon.coupontemplate.service.CouponTemplateService;
import store.ckin.coupon.policy.exception.CouponCodeNotFoundException;
import store.ckin.coupon.policy.repository.CouponCodeRepository;
import store.ckin.coupon.policy.repository.CouponPolicyRepository;

import java.util.List;
import java.util.Optional;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Service
@RequiredArgsConstructor
public class CouponTemplateServiceImpl implements CouponTemplateService {
    private final CouponTemplateRepository couponTemplateRepository;
    private final CouponPolicyRepository couponPolicyRepository;

    @Transactional
    @Override
    public void createCouponTemplate(CreateCouponTemplateRequestDto couponTemplateRequestDto) {
        couponPolicyRepository.findById(couponTemplateRequestDto.getPolicyId())
                .orElseThrow(() -> new CouponPolicyNotFoundException(couponTemplateRequestDto.getPolicyId()));

        couponTemplateRepository.save(CouponTemplate.builder()
                .policyId(couponTemplateRequestDto.getPolicyId())
                .bookId(couponTemplateRequestDto.getBookId())
                .categoryId(couponTemplateRequestDto.getCategoryId())
                .name(couponTemplateRequestDto.getName())
                .amount(couponTemplateRequestDto.getAmount())
                .build());

    }
    @Transactional
    @Override
    public Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable) {
        return couponTemplateRepository.getCouponTemplateList(pageable);
    }
    @Transactional(readOnly = true)
    @Override
    public GetCouponTemplateResponseDto getCouponTemplate(Long couponTemplateId) {
        Optional<GetCouponTemplateResponseDto> optionalCoupon = couponTemplateRepository.getCouponTemplate(couponTemplateId);
        if (optionalCoupon.isEmpty()) {
            throw new CouponTemplateNotFoundException(couponTemplateId);
        }
        return optionalCoupon.get();
    }
    @Transactional
    @Override
    public void updateCouponTemplate(Long couponTemplateId, CreateCouponTemplateRequestDto couponRequestDto) {
        if (!couponTemplateRepository.existsById(couponTemplateId)) {
            throw new CouponTemplateNotFoundException(couponTemplateId);
        }
        couponTemplateRepository.save(CouponTemplate.builder().id(couponTemplateId)
                .policyId(couponRequestDto.getPolicyId())
                .bookId(couponRequestDto.getBookId())
                .categoryId(couponRequestDto.getCategoryId())
                .name(couponRequestDto.getName())
                .amount(couponRequestDto.getAmount())
                .build());
    }
    @Transactional
    @Override
    public void deleteCouponTemplate(Long couponTemplateId) {
        Optional<CouponTemplate> optionalCoupon = couponTemplateRepository.findById(couponTemplateId);

        if (optionalCoupon.isEmpty()) {
            throw new CouponTemplateNotFoundException(couponTemplateId);
        }

        couponTemplateRepository.delete(optionalCoupon.get());
    }
    @Transactional(readOnly = true)
    @Override
    public Page<GetCouponTemplateResponseDto> getBirthCouponTemplate(Pageable pageable) {
        return couponTemplateRepository.getBirthCouponTemplate(pageable);
    }
    @Transactional(readOnly = true)
    @Override
    public Page<GetCouponTemplateResponseDto> getBookCouponTemplate(Pageable pageable) {
        return couponTemplateRepository.getBookCouponTemplate(pageable);
    }
    @Transactional(readOnly = true)
    @Override
    public Page<GetCouponTemplateResponseDto> getCategoryCouponTemplate(Pageable pageable) {
        return couponTemplateRepository.getCategoryTemplate(pageable);
    }
}
