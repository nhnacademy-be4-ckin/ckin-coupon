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
    private Long policyId;
    private Long memberId;
    private Long bookId;
    private Long categoryId;
    @NotNull(message = "쿠폰 이름을 입력해주세요")
    private String name;
    @NotNull(message = "쿠폰 만료기간을 입력해주세요")
    private LocalDateTime expirationDate;
    private LocalDateTime issueDate;
    private LocalDateTime usedDate;
}
