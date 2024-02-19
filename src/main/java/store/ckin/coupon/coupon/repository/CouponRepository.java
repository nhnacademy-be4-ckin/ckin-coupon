package store.ckin.coupon.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.ckin.coupon.coupon.model.Coupon;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {
}
