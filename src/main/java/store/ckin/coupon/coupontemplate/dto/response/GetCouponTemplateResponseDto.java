package store.ckin.coupon.coupontemplate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쿠폰 템플릿을 반환할 때 사용하는 dto 입니다.
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
    private Long typeId;
}
