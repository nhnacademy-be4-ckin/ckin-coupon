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

import java.util.Optional;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CouponTemplateServiceImpl implements CouponTemplateService {
    private final CouponTemplateRepository couponTemplateRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    @Override
    public void createCouponTemplate(CreateCouponTemplateRequestDto couponTemplateRequestDto) {
        couponPolicyRepository.findById(couponTemplateRequestDto.getPolicyId())
                .orElseThrow(CouponPolicyNotFoundException::new);

        couponTemplateRepository.save(CouponTemplate.builder()
                .policyId(couponTemplateRequestDto.getPolicyId())
                .bookId(couponTemplateRequestDto.getBookId())
                .categoryId(couponTemplateRequestDto.getCategoryId())
                .name(couponTemplateRequestDto.getName())
                .amount(couponTemplateRequestDto.getAmount())
                .build());

    }

    @Override
    public Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable) {
        return couponTemplateRepository.getCouponTemplateList(pageable);
    }

    @Override
    public GetCouponTemplateResponseDto getCouponTemplate(Long couponTemplateId) {
        Optional<GetCouponTemplateResponseDto> optionalCoupon = couponTemplateRepository.getCouponTemplate(couponTemplateId);
        if(optionalCoupon.isEmpty()) {
            throw new CouponTemplateNotFoundException();
        }
        return optionalCoupon.get();
    }

    @Override
    public void updateCouponTemplate(Long couponTemplateId, CreateCouponTemplateRequestDto couponRequestDto) {
        if(!couponTemplateRepository.existsById(couponTemplateId)) {
            throw new CouponTemplateNotFoundException();
        }
        couponTemplateRepository.save(CouponTemplate.builder()
                .id(couponTemplateId)
                .policyId(couponRequestDto.getPolicyId())
                .bookId(couponRequestDto.getBookId())
                .categoryId(couponRequestDto.getCategoryId())
                .name(couponRequestDto.getName())
                .amount(couponRequestDto.getAmount())
                .build());
    }

    @Override
    public void deleteCouponTemplate(Long couponId) {
        Optional<CouponTemplate> optionalCoupon = couponTemplateRepository.findById(couponId);

        if(optionalCoupon.isEmpty()) {
            throw new CouponTemplateNotFoundException();
        }

        couponTemplateRepository.delete(optionalCoupon.get());
    }
}
