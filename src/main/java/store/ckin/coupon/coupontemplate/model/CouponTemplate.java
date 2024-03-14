package store.ckin.coupon.coupontemplate.model;

import lombok.*;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * CouponTemplate
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CouponTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupontemplate_id")
    private Long id;
    @NotNull
    @Column(name = "couponpolicy_id")
    private Long policyId;
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "category_id")
    private Long categoryId;
    @NotNull
    @Column(name = "coupontemplate_name")
    private String name;
    @NotNull
    @Column(name = "coupontemplate_amount")
    private Long amount;
    @Column(name = "expiration_duration")
    private Integer duration;
    @Column(name = "expiration_date")
    private Date expirationDate;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "template_type_id")
    private CouponTemplateType type;

    public void update(CreateCouponTemplateRequestDto couponTemplateRequestDto) {
        this.policyId = couponTemplateRequestDto.getPolicyId();
        this.bookId = couponTemplateRequestDto.getBookId();
        this.categoryId = couponTemplateRequestDto.getCategoryId();
        this.name = couponTemplateRequestDto.getName();
        this.amount = couponTemplateRequestDto.getAmount();
        this.duration = couponTemplateRequestDto.getDuration();
        this.expirationDate = couponTemplateRequestDto.getExpirationDate();
    }

}
