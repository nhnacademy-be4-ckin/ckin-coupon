package store.ckin.coupon.policy.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ckin.coupon.policy.model.CouponCode;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * 쿠폰 정책을 생성하는 dto 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
@Getter
@NoArgsConstructor
public class CreateCouponPolicyRequestDto {
    @NotNull(message = "쿠폰 코드를 입력해주세요")
    private Long couponCodeId;
    @NotNull(message = "쿠폰의 최소 가격을 입력해주세요")
    private Integer minOrderPrice;
    private Integer discountPrice;
    private Integer discountRate;
    private Integer maxDiscountPrice;
}
