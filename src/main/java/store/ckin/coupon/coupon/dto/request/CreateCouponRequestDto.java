package store.ckin.coupon.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
@Getter
@NoArgsConstructor
public class CreateCouponRequestDto {

    @NotNull(message = "쿠폰 정책 번호를 입력해주세요")
    @Column(name = "couponpolicy_id")
    private Long policyId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "category_id")
    private Long categoryId;

    @NotNull(message = "쿠폰 이름을 입력해주세요")
    @Column(name = "coupon_name")
    private String name;

    @NotNull(message = "쿠폰 만료기간을 입력해주세요")
    @Column(name = "coupon_expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "coupon_issue_date")
    private LocalDateTime issueDate;

    @Column(name = "coupon_used_date")
    private LocalDateTime usedDate;
}
