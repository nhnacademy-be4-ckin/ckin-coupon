package store.ckin.coupon.coupon.model;

import lombok.*;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

/**
 * Coupon
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;
    @NotNull
    @Column(name = "member_id")
    private Long memberId;
    @NotNull
    @Column(name = "coupontemplate_id")
    private Long couponTemplateId;
    @NotNull
    @Column(name = "coupon_expiration_date")
    private Date expirationDate;
    @NotNull
    @Column(name = "coupon_issue_date")
    private Date issueDate;
    @Column(name = "coupon_used_date")
    private Date usedDate;

    public void updateUsedCoupon() {
        this.usedDate = Calendar.getInstance().getTime();
    }

}