package store.ckin.coupon.coupontemplate.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.sql.Date;
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
public class GetCouponTemplateResponseDto {
    private Long id;
    private Long policyId;
    private Integer minOrderPrice;
    private Integer discountPrice;
    private Integer discountRate;
    private Integer maxDiscountPrice;
    private Long bookId;
    private Long categoryId;
    private String name;
    private Long amount;
    private Long typeId;
    private Integer duration;
    private Date expirationDate;
    private Boolean isBirthPolicy;

    @QueryProjection
    public GetCouponTemplateResponseDto(Long id, Long policyId, Integer minOrderPrice, Integer discountPrice,
                                        Integer discountRate, Integer maxDiscountPrice, Long bookId, Long categoryId,
                                        String name, Long amount, Long typeId, Integer duration, Date expirationDate, Boolean isBirthPolicy) {
        this.id = id;
        this.policyId = policyId;
        this.minOrderPrice = minOrderPrice;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.maxDiscountPrice = maxDiscountPrice;
        this.bookId = bookId;
        this.categoryId = categoryId;
        this.name = name;
        this.amount = amount;
        this.typeId = typeId;
        this.duration = duration;
        this.expirationDate = expirationDate;
        this.isBirthPolicy = isBirthPolicy;
    }
}
