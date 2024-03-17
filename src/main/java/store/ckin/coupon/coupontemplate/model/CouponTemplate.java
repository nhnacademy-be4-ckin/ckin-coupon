package store.ckin.coupon.coupontemplate.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;

/**
 * CouponTemplate
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Entity
@Getter
@Builder
@ToString
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
    @Column(name = "is_birth_policy")
    private Boolean isBirthPolicy = false;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "template_type_id")
    private CouponTemplateType type;

    /**
     * 쿠폰 템플릿의 사용여부를 변경하는 메소드 입니다.
     *
     * @param isBirthPolicy
     */
    public void updateTemplateStatus(Boolean isBirthPolicy) {
        this.isBirthPolicy = isBirthPolicy;
    }
}
