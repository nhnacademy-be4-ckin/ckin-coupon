package store.ckin.coupon.coupon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.exception.CouponCodeNotFoundException;
import store.ckin.coupon.coupon.exception.CouponNotFoundException;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupon.repository.CouponRepository;
import store.ckin.coupon.coupon.service.CouponService;

import java.util.Optional;

/**
 * CouponPolicyServiceImpl
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    @Override
    public void createCoupon(CreateCouponRequestDto policyRequestDto) {
        couponRepository.save(Coupon.builder()
                .policyId(policyRequestDto.getPolicyId())
                .memberId(policyRequestDto.getMemberId())
                .bookId(policyRequestDto.getBookId())
                .categoryId(policyRequestDto.getCategoryId())
                .name(policyRequestDto.getName())
                .expirationDate(policyRequestDto.getExpirationDate())
                .issueDate(policyRequestDto.getIssueDate())
                .usedDate(policyRequestDto.getUsedDate())
                .build());

    }

    @Override
    public Page<GetCouponResponseDto> getCouponList(Pageable pageable) {
        return couponRepository.getCouponList(pageable);
    }

    @Override
    public GetCouponResponseDto getCoupon(Long couponId) {
        Optional<GetCouponResponseDto> optionalCoupon = couponRepository.getCoupon(couponId);
        if(optionalCoupon.isEmpty()) {
            throw new CouponNotFoundException();
        }
        return optionalCoupon.get();
    }

    @Override
    public void updateCoupon(Long couponId, CreateCouponRequestDto couponRequestDto) {
        if(!couponRepository.existsById(couponId)) {
            throw new CouponNotFoundException();
        }
        couponRepository.save(Coupon.builder()
                .id(couponId)
                .policyId(couponRequestDto.getPolicyId())
                .memberId(couponRequestDto.getMemberId())
                .bookId(couponRequestDto.getBookId())
                .categoryId(couponRequestDto.getCategoryId())
                .name(couponRequestDto.getName())
                .expirationDate(couponRequestDto.getExpirationDate())
                .issueDate(couponRequestDto.getIssueDate())
                .usedDate(couponRequestDto.getUsedDate())
                .build());
    }

    @Override
    public void deleteCoupon(Long couponId) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);

        if(optionalCoupon.isEmpty()) {
            throw new CouponNotFoundException();
        }

        couponRepository.delete(optionalCoupon.get());
    }
}
