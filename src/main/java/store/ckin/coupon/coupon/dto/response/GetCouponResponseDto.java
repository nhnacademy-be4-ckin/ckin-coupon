package store.ckin.coupon.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private Long memberId;
    private Long couponTemplateId;
    private Date expirationDate;
    private Date issueDate;
    private Date usedDate;
}
