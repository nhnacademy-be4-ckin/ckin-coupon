package store.ckin.coupon.coupon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.exception.CouponNotFoundException;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupon.repository.CouponRepository;
import store.ckin.coupon.coupon.service.CouponService;

import java.util.Calendar;
import java.util.Optional;

/**
 * CouponServiceImpl
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
    public void createCoupon(CreateCouponRequestDto couponRequestDto) {
        couponRepository.save(Coupon.builder()
                .memberId(couponRequestDto.getMemberId())
                .couponTemplateId(couponRequestDto.getCouponTemplateId())
                .expirationDate(couponRequestDto.getExpirationDate())
                .issueDate(couponRequestDto.getIssueDate())
                .usedDate(couponRequestDto.getUsedDate())
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetCouponResponseDto> getUsedCouponByMember(Pageable pageable, Long memberId) {
        //TODO: 회원 아이디가 존재하는지 확인
        return couponRepository.getUsedCouponByMember(pageable, memberId);

    }

    @Override
    public Page<GetCouponResponseDto> getUnUsedCouponByMember(Pageable pageable, Long memberId) {
        //TODO: 회원 아이디가 존재하는지 확인
        return couponRepository.getUnUsedCouponByMember(pageable, memberId);
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

    @Override
    public void updateCouponUsedDate(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(CouponNotFoundException::new);

        couponRepository.save(Coupon.builder()
                .memberId(coupon.getMemberId())
                .couponTemplateId(coupon.getCouponTemplateId())
                .expirationDate(coupon.getExpirationDate())
                .issueDate(coupon.getIssueDate())
                .usedDate(Calendar.getInstance().getTime())
                .build());
    }

    @Override
    public Page<GetCouponResponseDto> getAllCouponList(Pageable pageable) {
        return couponRepository.getAllCouponList(pageable);
    }

}
