package store.ckin.coupon.coupon.exception;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 27
 */
public class CouponAlreadyExistException extends RuntimeException {
    public static final String MESSAGE = "Coupon Already Exist";

    /**
     * 해당 쿠폰 번호가 없음을 지정하는 생성자 입니다.
     */
    public CouponAlreadyExistException() {
        super(MESSAGE);
    }
}
