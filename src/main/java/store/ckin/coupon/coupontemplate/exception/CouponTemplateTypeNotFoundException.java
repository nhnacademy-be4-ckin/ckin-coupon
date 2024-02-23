package store.ckin.coupon.coupontemplate.exception;

/**
 * 쿠폰 템플릿 타입이 존재하지 않음을 나타내는 class 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 21
 */
public class CouponTemplateTypeNotFoundException extends RuntimeException {
    public static final String MESSAGE = "Not Found CouponTemplateType";

    /**
     * 쿠폰정책이 존재하지 않음을 지정하는 생성자 입니다.
     */
    public CouponTemplateTypeNotFoundException() {
        super(MESSAGE);
    }
}
