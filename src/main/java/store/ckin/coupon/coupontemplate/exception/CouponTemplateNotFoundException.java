package store.ckin.coupon.coupontemplate.exception;

/**
 * 쿠폰 템플릿이 존재하지 않음을 나타내는 class 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 14
 */
public class CouponTemplateNotFoundException extends RuntimeException {
    /**
     * 쿠폰템플릿이 존재하지 않음을 지정하는 생성자 입니다.
     *
     * @param couponTemplateId the couponTemplateId
     */
    public CouponTemplateNotFoundException(Long couponTemplateId) {
        super(couponTemplateId + "은 없는 쿠폰 정책 번호입니다.");
    }
}
