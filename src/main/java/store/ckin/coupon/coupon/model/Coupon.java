package store.ckin.coupon.coupon.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * CouponPolicy
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @NotNull
    @Column(name = "couponpolicy_id")
    private Long policyId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "category_id")
    private Long categoryId;

    @NotNull
    @Column(name = "coupon_name")
    private String name;

    @NotNull
    @Column(name = "coupon_expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "coupon_issue_date")
    private LocalDateTime issueDate;

    @Column(name = "coupon_used_date")
    private LocalDateTime usedDate;


}
