package store.ckin.coupon.coupontemplate.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
@Getter
@NoArgsConstructor
public class CreateCouponTemplateRequestDto {
    @NotNull(message = "쿠폰 정책 아이디를 입력해주세요")
    private Long policyId;
    private Long bookId;
    private Long categoryId;
    @NotNull(message = "쿠폰 이름을 입력해주세요")
    private String name;
    private Long amount;
}
