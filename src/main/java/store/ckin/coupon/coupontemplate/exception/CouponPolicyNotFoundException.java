package store.ckin.coupon.coupontemplate.exception;

/**
 * 쿠폰정책이 존재하지 않음을 나타내는 class 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 16
 */
public class CouponPolicyNotFoundException extends RuntimeException {
    /**
     * 쿠폰정책이 존재하지 않음을 지정하는 생성자 입니다.
     *
     * @param couponPolicyId the couponPolicyId
     */
    public CouponPolicyNotFoundException(Long couponPolicyId) {
        super(couponPolicyId + "은 없는 쿠폰 정책 번호입니다.");
    }
}
