package store.ckin.coupon.coupon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.exception.CouponAlreadyExistException;
import store.ckin.coupon.coupon.exception.CouponNotFoundException;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupon.repository.CouponRepository;
import store.ckin.coupon.coupon.service.CouponService;
import store.ckin.coupon.coupontemplate.exception.CouponTemplateTypeNotFoundException;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateRepository;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateTypeRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * CouponServiceImpl
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final CouponTemplateRepository couponTemplateRepository;
    private final CouponTemplateTypeRepository couponTemplateTypeRepository;

    /**
     * {@inheritDoc}
     *
     * @param couponRequestDto 쿠폰 요청 DTO
     */
    @Override
    @Transactional
    public void createCoupon(CreateCouponRequestDto couponRequestDto) {
        //TODO: memberId 있는지 확인
        if (!couponTemplateTypeRepository.existsById(couponRequestDto.getCouponTemplateId())) {
            throw new CouponTemplateTypeNotFoundException();
        }

        couponRepository.save(Coupon.builder()
                .memberId(couponRequestDto.getMemberId())
                .couponTemplateId(couponRequestDto.getCouponTemplateId())
                .expirationDate(couponRequestDto.getExpirationDate())
                .issueDate(couponRequestDto.getIssueDate())
                .usedDate(couponRequestDto.getUsedDate())
                .build());
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 페이지 정보
     * @param memberId 회원 ID
     * @return
     */
    @Override
    public Page<GetCouponResponseDto> getUsedCouponByMember(Pageable pageable, Long memberId) {
        //TODO: 회원 아이디가 존재하는지 확인
        return couponRepository.getUsedCouponByMember(pageable, memberId);

    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 페이지 정보
     * @param memberId 회원 ID
     * @return
     */
    @Override
    public Page<GetCouponResponseDto> getUnUsedCouponByMember(Pageable pageable, Long memberId) {
        //TODO: 회원 아이디가 존재하는지 확인
        return couponRepository.getUnUsedCouponByMember(pageable, memberId);
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 페이지 정보
     * @param typeId   쿠폰 템플릿 타입 ID
     * @return
     */
    @Override
    public Page<GetCouponResponseDto> getCouponList(Pageable pageable, Long typeId) {
        if (!couponTemplateTypeRepository.existsById(typeId)) {
            throw new CouponTemplateTypeNotFoundException();
        }

        return couponRepository.getCouponList(pageable, typeId);

    }

    /**
     * {@inheritDoc}
     *
     * @param couponId 쿠폰 ID
     */
    @Override
    @Transactional
    public void updateCouponUsedDate(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(CouponNotFoundException::new);

        coupon.updateUsedCoupon();
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 페이지 정보
     * @return
     */
    @Override
    public Page<GetCouponResponseDto> getAllCouponList(Pageable pageable) {
        return couponRepository.getAllCouponList(pageable);
    }

    /**
     * {@inheritDoc}
     *
     * @param couponId 쿠폰 ID
     * @return
     */
    @Override
    public GetCouponResponseDto getCouponByCouponId(Long couponId) {
        if (!couponRepository.existsById(couponId)) {
            throw new CouponNotFoundException();
        }
        return couponRepository.getCouponByCouponId(couponId);
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 페이지 정보
     * @param memberId 회원 ID
     * @return
     */
    @Override
    public Page<GetCouponResponseDto> getCouponByMember(Pageable pageable, Long memberId) {
        //TODO: memberId 존재하는지 확인
        return couponRepository.getCouponByMember(pageable, memberId);
    }

    @Override
    public List<GetCouponResponseDto> getCouponForBuyList(Long memberId, List<Long> bookIdList) {
        //TODO: memberId 존재하는지 확인
        //TODO: 카테고리 아이디 리스트 받아오기
        List<Long> categoryIdList = List.of(1L);
        return couponRepository.getCouponForBuyList(memberId, bookIdList, categoryIdList);
    }

    @Override
    public Boolean isExistCoupon(Long memberId, Long couponTemplateId) {
        return couponRepository.isExistCoupon(memberId, couponTemplateId);
    }

    @Override
    @Transactional
    public boolean createCouponByIds(Long memberId, Long couponTemplateId) {
        if (!couponTemplateRepository.existsById((couponTemplateId))) {
            return false;
        }

        if(isExistCoupon(memberId, couponTemplateId)) {
            return false;
        }
        couponRepository.save(Coupon.builder()
                .memberId(memberId)
                .couponTemplateId(couponTemplateId)
                .expirationDate(Date.valueOf(LocalDate.now().plusDays(30)))
                .issueDate(Date.valueOf(LocalDate.now()))
                .usedDate(null)
                .build());
        return true;
    }


}
