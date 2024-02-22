package store.ckin.coupon.coupon.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 쿠폰 반환시 사용하는 dto 입니다.
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
    private Long typeId;
    private String name;
    private Date expirationDate;
    private Date issueDate;
    private Date usedDate;

    /**
     * 쿠폰 응답을 반환하는 생성자 입니다.
     *
     * @param id               the id
     * @param memberId         the member id
     * @param couponTemplateId the coupon template id
     * @param policyId         the policy id
     * @param bookId           the book id
     * @param categoryId       the category id
     * @param name             the name
     * @param expirationDate   the expiration date
     * @param issueDate        the issue date
     * @param usedDate         the used date
     */
    @QueryProjection
    public GetCouponResponseDto(Long id, Long memberId, Long couponTemplateId, Long policyId, Long bookId, Long categoryId, Long typeId, String name, Date expirationDate, Date issueDate, Date usedDate) {
        this.id = id;
        this.memberId = memberId;
        this.couponTemplateId = couponTemplateId;
        this.policyId = policyId;
        this.bookId = bookId;
        this.categoryId = categoryId;
        this.typeId = typeId;
        this.name = name;
        this.expirationDate = expirationDate;
        this.issueDate = issueDate;
        this.usedDate = usedDate;
    }
}
