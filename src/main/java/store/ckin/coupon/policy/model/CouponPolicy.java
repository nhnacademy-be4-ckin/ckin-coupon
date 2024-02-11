package store.ckin.coupon.policy.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
public class CouponPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponpolicy_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "couponcode_id")
    private CouponCode couponCode;

    @NotNull
    @Column(name = "couponpolicy_min_order_price")
    private int minOrderPrice;

    @Column(name = "couponpolicy_discount_price")
    private int discountPrice;

    @Column(name = "couponpolicy_discount_rate")
    private int discountRate;

    @Column(name = "couponpolicy_max_discount_price")
    private int maxDiscountPrice;

    @NotNull
    @Column(name = "couponpolicy_state")
    private boolean state;


}
