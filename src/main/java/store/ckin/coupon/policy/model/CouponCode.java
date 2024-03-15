package store.ckin.coupon.policy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CouponCode
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponcode_id")
    private Long id;

    @NotNull
    @Column(name = "couponcode_name")
    private String name;

    public CouponCode(String name) {
        this.name = name;
    }
}
