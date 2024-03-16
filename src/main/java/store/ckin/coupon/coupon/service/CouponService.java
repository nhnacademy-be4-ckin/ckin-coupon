package store.ckin.coupon.coupon.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;

/**
 * CouponService
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
public interface CouponService {
    /**
     * 쿠폰을 생성하는 메서드 입니다.
     *
     * @param couponRequestDto 쿠폰 요청 DTO
     */
    void createCoupon(CreateCouponRequestDto couponRequestDto);

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
     * 쿠폰을 목록을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
     * @param typeId   쿠폰 템플릿 타입 ID
     */
    Page<GetCouponResponseDto> getCouponList(Pageable pageable, Long typeId);

    /**
     * 쿠폰이 사용됐음을 업데이트하는 메서드 입니다.
     *
     * @param couponIds 쿠폰 ID
     */
    void updateCouponUsedDate(List<Long> couponIds);

    /**
     * 쿠폰을 목록을 조회하는 메서드 입니다.
     *
     * @param pageable 페이지 정보
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

    /**
     * 도서에 해당하는 쿠폰 리스트를 반환하는 메소드입니다.
     *
     * @param memberId   회원 ID
     * @param bookIdList 도서 리스트
     * @return 사용 가능한 쿠폰 목록
     */
    List<GetCouponResponseDto> getCouponForBuyList(Long memberId, List<Long> bookIdList);

    /**
     * 회원이 해당 쿠폰을 발급받은 기록이 있는지 확인하는 메소드 입니다.
     *
     * @param memberId         회원 ID
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @return True/False
     */
    Boolean isExistCoupon(Long memberId, Long couponTemplateId);

    /**
     * 회원이 쿠폰을 발급 받는 메소드 입니다.
     *
     * @param memberId         회원 ID
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @return
     */
    boolean createCouponByIds(Long memberId, Long couponTemplateId);
}
