package store.ckin.coupon.coupon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.exception.CouponPolicyNotFoundException;
import store.ckin.coupon.coupon.exception.CouponTemplateNotFoundException;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupon.repository.CouponRepository;
import store.ckin.coupon.coupon.service.CouponService;
import store.ckin.coupon.policy.repository.CouponPolicyRepository;

import java.util.Optional;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;

    @Transactional
    @Override
    public void createCouponTemplate(CreateCouponRequestDto couponTemplateRequestDto) {
        couponRepository.save(Coupon.builder()
                .memberId(couponTemplateRequestDto.getMemberId())
                .couponTemplateId(couponTemplateRequestDto.getCouponTemplateId())
                .expirationDate(couponTemplateRequestDto.getExpirationDate())
                .issueDate(couponTemplateRequestDto.getIssueDate())
                .usedDate(couponTemplateRequestDto.getUsedDate())
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetCouponResponseDto> getCouponListByMember(Pageable pageable, Long memberId) {
        //TODO: 회원 아이디가 존재하는지 확인
        return couponRepository.getCouponListByMember(pageable, memberId);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetCouponResponseDto> getBirthCouponAll(Pageable pageable) {
        return couponRepository.getBirthCouponAll(pageable);

    }

    @Override
    public Page<GetCouponResponseDto> getBookCouponAll(Pageable pageable) {
        return couponRepository.getBookCouponAll(pageable);

    }

    @Override
    public Page<GetCouponResponseDto> getCategoryCouponAll(Pageable pageable) {
        return couponRepository.getCategoryCouponAll(pageable);
    }

}
