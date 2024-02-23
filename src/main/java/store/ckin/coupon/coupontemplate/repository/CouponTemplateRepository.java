package store.ckin.coupon.coupontemplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.repository.impl.CouponTemplateRepositoryImpl;

/**
 * CouponTemplateRepository
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, Long>, CouponTemplateRepositoryCustom {
}
