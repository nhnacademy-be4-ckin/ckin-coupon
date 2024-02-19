package store.ckin.coupon.policy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import store.ckin.coupon.policy.model.CouponPolicy;

/**
 * CouponPolicyRepository
 *
 * @author : gaeun
 * @version : 2024. 02. 08
 */
public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long>, CouponPolicyRepositoryCustom {
}
