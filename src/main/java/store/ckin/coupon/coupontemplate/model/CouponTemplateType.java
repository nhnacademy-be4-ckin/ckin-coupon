package store.ckin.coupon.coupontemplate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
