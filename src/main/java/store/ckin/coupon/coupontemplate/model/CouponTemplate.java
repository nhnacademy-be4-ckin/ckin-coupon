package store.ckin.coupon.coupontemplate.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 15
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

}
