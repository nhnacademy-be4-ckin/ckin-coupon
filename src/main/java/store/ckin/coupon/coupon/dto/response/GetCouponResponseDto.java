package store.ckin.coupon.coupon.dto.response;

import com.querydsl.core.annotations.QueryProjection;
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
public class GetCouponResponseDto {
    private Long id;
    private Long memberId;
    private Long couponTemplateId;
    private Long policyId;
    private Long bookId;
    private Long categoryId;
    private String name;
    private Date expirationDate;
    private Date issueDate;
    private Date usedDate;

    @QueryProjection
    public GetCouponResponseDto (Long id, Long memberId, Long couponTemplateId, Long policyId, Long bookId, Long categoryId, String name, Date expirationDate, Date issueDate, Date usedDate) {
        this.id = id;
        this.memberId = memberId;
        this.couponTemplateId = couponTemplateId;
        this.policyId = policyId;
        this.bookId = bookId;
        this.categoryId = categoryId;
        this.name = name;
        this.expirationDate = expirationDate;
        this.issueDate = issueDate;
        this.usedDate = usedDate;
    }
}
