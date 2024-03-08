package store.ckin.coupon.coupontemplate.model;

import lombok.*;

import javax.persistence.*;

/**
 * CouponTemplateType
 *
 * @author : gaeun
 * @version : 2024. 02. 21
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CouponTemplateType {
    @Id
    @Column(name = "template_type_id")
    private Long id;
    @Column(name = "template_type_name")
    private String name;
}
