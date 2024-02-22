package store.ckin.coupon.coupontemplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.ckin.coupon.coupontemplate.model.CouponTemplateType;

/**
 * CouponTemplateTypeRepository
 *
 * @author : gaeun
 * @version : 2024. 02. 21
 */
public interface CouponTemplateTypeRepository extends JpaRepository<CouponTemplateType, Long> {
}
