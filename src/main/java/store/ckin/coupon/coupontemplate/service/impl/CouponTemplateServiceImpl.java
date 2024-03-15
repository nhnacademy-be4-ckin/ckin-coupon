package store.ckin.coupon.coupontemplate.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
@Service
@RequiredArgsConstructor
public class CouponTemplateServiceImpl implements CouponTemplateService {
    private final CouponTemplateRepository couponTemplateRepository;
    private final CouponTemplateTypeRepository couponTemplateTypeRepository;
    private final CouponPolicyRepository couponPolicyRepository;

    /**
     * {@inheritDoc}
     *
     * @param couponTemplateRequestDto 쿠폰 템플릿 요청 DTO
     */
    @Override
    public void createCouponTemplate(CreateCouponTemplateRequestDto couponTemplateRequestDto) {
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
                .build());

    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 페이지 정보
     * @param typeId   쿠폰 템플릿 타입 ID
     * @return
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
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @return
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
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @param couponRequestDto 쿠폰 템플릿 요청 DTO
     */
    @Override
    public void updateCouponTemplate(Long couponTemplateId, CreateCouponTemplateRequestDto couponRequestDto) {
        CouponTemplate couponTemplate = couponTemplateRepository.findById(couponTemplateId)
                .orElseThrow(CouponTemplateNotFoundException::new);
        if (!couponPolicyRepository.existsById(couponRequestDto.getPolicyId())) {
            throw new CouponPolicyNotFoundException();
        }

        //TODO: bookId, categoryId 검수
        couponTemplate.update(couponRequestDto);
    }

    /**
     * {@inheritDoc}
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     */
    @Override
    public void deleteCouponTemplate(Long couponTemplateId) {
        CouponTemplate couponTemplate = couponTemplateRepository.findById(couponTemplateId)
                .orElseThrow(CouponTemplateNotFoundException::new);

        couponTemplateRepository.delete(couponTemplate);
    }
}
