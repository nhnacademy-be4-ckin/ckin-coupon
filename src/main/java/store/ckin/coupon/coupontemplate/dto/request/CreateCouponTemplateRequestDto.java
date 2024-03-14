package store.ckin.coupon.coupontemplate.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Date;

/**
 * 쿠폰 템플릿을 생성할 때 사용하는 dto 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
@Getter
@NoArgsConstructor
public class CreateCouponTemplateRequestDto {
    @NotNull(message = "쿠폰 정책 아이디를 입력해주세요")
    @PositiveOrZero(message = "0보다 큰 값을 입력해주세요")
    private Long policyId;
    @PositiveOrZero(message = "0보다 큰 값을 입력해주세요")
    private Long bookId;
    @PositiveOrZero(message = "0보다 큰 값을 입력해주세요")
    private Long categoryId;
    @NotNull(message = "쿠폰 템플릿 타입 아이디를 입력해주세요.")
    @PositiveOrZero(message = "0보다 큰 값을 입력해주세요")
    private Long typeId;
    @NotBlank(message = "쿠폰 이름을 입력해주세요")
    private String name;
    @PositiveOrZero(message = "0보다 큰 값을 입력해주세요")
    private Long amount;
    @PositiveOrZero(message = "0보다 큰 값을 입력해주세요")
    private Integer duration;
    private Date expirationDate;
}
