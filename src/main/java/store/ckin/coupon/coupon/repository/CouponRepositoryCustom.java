package store.ckin.coupon.coupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;

import java.util.List;

/**
 * CouponPolicyRepositoryCustom
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
@NoRepositoryBean
public interface CouponRepositoryCustom {
    /**
     * 특정 회원의 사용된 쿠폰을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param memberId 회원 ID
     * @return 쿠폰 DTO 목록
     */
    Page<GetCouponResponseDto> getUsedCouponByMember(Pageable pageable, Long memberId);

    /**
     * 특정 회원의 미사용된 쿠폰을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param memberId 회원 ID
     * @return 쿠폰 DTO 목록
     */
    Page<GetCouponResponseDto> getUnUsedCouponByMember(Pageable pageable, Long memberId);


    /**
     * 쿠폰 타입에 해당하는 목록을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param typeId   쿠폰 템플릿 타입 ID
     */
    Page<GetCouponResponseDto> getCouponList(Pageable pageable, Long typeId);

    /**
     * 쿠폰을 목록을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @return 쿠폰 목록
     */
    Page<GetCouponResponseDto> getAllCouponList(Pageable pageable);

    /**
     * 쿠폰 아이디에 해당하는 쿠폰을 조회하는 메소드 입니다.
     *
     * @param couponId 쿠폰 ID
     * @return 쿠폰 DTO
     */
    GetCouponResponseDto getCouponByCouponId(Long couponId);

    /**
     * 특정 회원의 모든 쿠폰을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param memberId 회원 ID
     * @return 쿠폰 DTO 목록
     */
    Page<GetCouponResponseDto> getCouponByMember(Pageable pageable, Long memberId);

    List<GetCouponResponseDto> getCouponForBuyList(Long memberId, List<Long> bookIdList, List<Long> categoryIdList);
}
