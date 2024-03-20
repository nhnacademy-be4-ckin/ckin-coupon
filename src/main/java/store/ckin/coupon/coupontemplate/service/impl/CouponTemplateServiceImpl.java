package store.ckin.coupon.coupontemplate.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.exception.CouponPolicyNotFoundException;
import store.ckin.coupon.coupontemplate.exception.CouponTemplateNotFoundException;
import store.ckin.coupon.coupontemplate.exception.CouponTemplateTypeNotFoundException;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.model.CouponTemplateType;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateRepository;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateTypeRepository;
import store.ckin.coupon.coupontemplate.service.CouponTemplateService;
import store.ckin.coupon.policy.repository.CouponPolicyRepository;

/**
 * CouponTemplateServiceImpl
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class CouponTemplateServiceImpl implements CouponTemplateService {
    private final CouponTemplateRepository couponTemplateRepository;
    private final CouponTemplateTypeRepository couponTemplateTypeRepository;
    private final CouponPolicyRepository couponPolicyRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createCouponTemplate(CreateCouponTemplateRequestDto couponTemplateRequestDto) {
        log.debug(couponTemplateRequestDto.toString());
        CouponTemplateType templateType =
                couponTemplateTypeRepository.findById(couponTemplateRequestDto.getTypeId())
                        .orElseThrow(CouponTemplateTypeNotFoundException::new);
        if (!couponPolicyRepository.existsById(couponTemplateRequestDto.getPolicyId())) {
            throw new CouponPolicyNotFoundException();
        }

        couponTemplateRepository.save(CouponTemplate.builder()
                .policyId(couponTemplateRequestDto.getPolicyId())
                .bookId(couponTemplateRequestDto.getBookId())
                .categoryId(couponTemplateRequestDto.getCategoryId())
                .type(templateType)
                .name(couponTemplateRequestDto.getName())
                .amount(couponTemplateRequestDto.getAmount())
                .type(templateType)
                .duration(couponTemplateRequestDto.getDuration())
                .expirationDate(couponTemplateRequestDto.getExpirationDate())
                .state(couponTemplateRequestDto.getState())
                .build());

    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable, Long typeId) {
        if (!couponTemplateTypeRepository.existsById(typeId)) {
            throw new CouponTemplateTypeNotFoundException();
        }
        return couponTemplateRepository.getCouponTemplateList(pageable, typeId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public GetCouponTemplateResponseDto getCouponTemplate(Long couponTemplateId) {
        Optional<GetCouponTemplateResponseDto> optionalCoupon =
                couponTemplateRepository.getCouponTemplate(couponTemplateId);
        if (optionalCoupon.isEmpty()) {
            throw new CouponTemplateNotFoundException();
        }

        return optionalCoupon.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCouponTemplate(Long couponTemplateId) {
        CouponTemplate couponTemplate = couponTemplateRepository.findById(couponTemplateId)
                .orElseThrow(CouponTemplateNotFoundException::new);

        couponTemplateRepository.delete(couponTemplate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCouponTemplateStatus(Long templateId, Boolean state) {
        CouponTemplate couponTemplate = couponTemplateRepository.findById(templateId)
                .orElseThrow(CouponTemplateNotFoundException::new);

        log.debug("state : {}", state);
        couponTemplate.updateTemplateStatus(state);
    }
}
