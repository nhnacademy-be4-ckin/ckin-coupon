package store.ckin.coupon.policy.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * CouponCode
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponcode_id")
    private Long id;

    @NotNull
    @Column(name="couponcode_name")
    private String name;

}
