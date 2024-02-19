package store.ckin.coupon.coupon.exception;

/**
 * 해당 쿠폰이 존재하지 않음을 나타내는 class 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 14
 */
public class CouponNotFoundException extends RuntimeException {

    /**
     * 해당 쿠폰 번호가 없음을 지정하는 생성자 입니다.
     *
     * @param couponId the coupon id
     */
    public CouponNotFoundException(Long couponId) {
        super(couponId + "은 없는 쿠폰번호입니다.");
    }
}