package store.ckin.coupon.coupontemplate.exception;

/**
 * 쿠폰 템플릿이 존재하지 않음을 나타내는 class 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 14
 */
public class CouponTemplateNotFoundException extends RuntimeException {
    public static final String MESSAGE = "Not Found CouponPolicy";
    /**
     * 쿠폰템플릿이 존재하지 않음을 지정하는 생성자 입니다.
     *
     */
    public CouponTemplateNotFoundException() {
        super(MESSAGE);
    }
}
