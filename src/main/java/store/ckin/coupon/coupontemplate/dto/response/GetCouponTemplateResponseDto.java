package store.ckin.coupon.coupontemplate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * GetCouponPolicyResponseDto
 *
 * @author : gaeun
 * @version : 2024. 02. 12
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCouponTemplateResponseDto {
    private Long id;
    private Long policyId;
    private Long bookId;
    private Long categoryId;
    private String name;
    private Long amount;
}
