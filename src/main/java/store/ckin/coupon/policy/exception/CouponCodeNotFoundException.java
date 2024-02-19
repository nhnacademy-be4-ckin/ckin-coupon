package store.ckin.coupon.policy.exception;

/**
 * 해당 쿠폰 코드가 존재하지 않음을 나타내는 class 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 10
 */
public class CouponCodeNotFoundException extends RuntimeException {
    /**
     * 해당 쿠폰 코드 번호가 없음을 지정하는 생성자 입니다.
     *
     * @param couponCode the couponCode id
     */
    public CouponCodeNotFoundException(Long couponCode) {
        super(couponCode + "은 없는 쿠폰번호입니다.");
    }
}
