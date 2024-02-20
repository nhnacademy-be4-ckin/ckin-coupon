package store.ckin.coupon.coupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;

import java.util.Optional;

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
     * @param pageable the pageable
     * @param memberId the member id
     * @return the used coupon by member
     */
    Page<GetCouponResponseDto> getUsedCouponByMember(Pageable pageable, Long memberId);

    /**
     * 특정 회원의 사용중인 쿠폰을 조회하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @param memberId the member id
     * @return the un used coupon by member
     */
    Page<GetCouponResponseDto> getUnUsedCouponByMember(Pageable pageable, Long memberId);

    /**
     * 생일 쿠폰 목록을 조회하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @return the birth coupon all
     */
    Page<GetCouponResponseDto> getBirthCouponAll(Pageable pageable);

    /**
     * 도서 쿠폰 목록을 조회하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @return the book coupon all
     */
    Page<GetCouponResponseDto> getBookCouponAll(Pageable pageable);

    /**
     * 카테고리 쿠폰 목록을 조회하는 메서드 입니다.
     *
     * @param pageable the pageable
     * @return the category coupon all
     */
    Page<GetCouponResponseDto> getCategoryCouponAll(Pageable pageable);

    Page<GetCouponResponseDto> getAllCouponList(Pageable pageable);
}
