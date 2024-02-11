package store.ckin.coupon.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.ckin.coupon.policy.model.CouponPolicy;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {
}
