package store.ckin.coupon.coupon.exception;

/**
 * 해당 쿠폰이 존재하지 않음을 나타내는 class 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 14
 */
public class CouponNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Not Found Coupon";

    /**
     * 해당 쿠폰 번호가 없음을 지정하는 생성자 입니다.
     */
    public CouponNotFoundException() {
        super(MESSAGE);
    }
}