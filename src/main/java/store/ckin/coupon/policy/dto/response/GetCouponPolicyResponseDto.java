package store.ckin.coupon.policy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * GetCouponPolicyResponseDto
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
