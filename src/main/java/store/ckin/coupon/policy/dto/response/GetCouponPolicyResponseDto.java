package store.ckin.coupon.policy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쿠폰 정책을 반환하는 dto 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 12
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCouponPolicyResponseDto {
    private Long id;
    private Integer minOrderPrice;
    private Integer discountPrice;
    private Integer discountRate;
    private Integer maxDiscountPrice;
}
