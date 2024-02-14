package store.ckin.coupon.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * GetCouponPolicyResponseDto
 *
 * @author : gaeun
 * @version : 2024. 02. 12
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCouponResponseDto {
    private Long id;
    private Long policyId;
    private Long memberId;
    private Long bookId;
    private Long categoryId;
    private String name;
    private LocalDateTime expirationDate;
    private LocalDateTime issueDate;
    private LocalDateTime usedDate;
}
