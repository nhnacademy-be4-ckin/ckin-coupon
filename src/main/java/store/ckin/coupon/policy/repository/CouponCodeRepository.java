package store.ckin.coupon.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.ckin.coupon.policy.model.CouponCode;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 10
 */
public interface CouponCodeRepository extends JpaRepository<CouponCode, Long> {
}
