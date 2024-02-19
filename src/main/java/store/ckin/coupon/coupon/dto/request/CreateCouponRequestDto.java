package store.ckin.coupon.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
@Getter
@NoArgsConstructor
public class CreateCouponRequestDto {
    @NotNull(message = "회원 아이디를 입력해주세요.")
    private Long memberId;
    @NotNull(message = "쿠폰 템플릿 아이디를 입력해주세요.")
    private Long couponTemplateId;
    @NotNull(message = "쿠폰 만료일을 입력해주세요.")
    private Date expirationDate;
    @NotNull(message = "쿠폰 발급일을 입력해주세요.")
    private Date issueDate;
    @Column(name = "coupon_used_date")
    private Date usedDate;
}
