package store.ckin.coupon.coupon.model;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
