package store.ckin.coupon.policy.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;
import store.ckin.coupon.policy.model.CouponCode;
import store.ckin.coupon.policy.model.CouponPolicy;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 16
 */
@DataJpaTest
@Transactional
class CouponPolicyRepositoryTest {
    @Autowired
    CouponPolicyRepository couponPolicyRepository;

    @Autowired
    EntityManager entityManager;

    CouponCode couponCode;
    CouponPolicy couponPolicy;

    @BeforeEach
    void setUp() {
        couponCode = new CouponCode("정액");
        couponPolicy = new CouponPolicy(1L, new CouponCode("정액"), 10000, 3000, null, 10000, true);

        ReflectionTestUtils.setField(couponCode, "name", "정액");
        entityManager.persist(couponCode);

        ReflectionTestUtils.setField(couponPolicy, "couponCode", couponCode);
        ReflectionTestUtils.setField(couponPolicy, "minOrderPrice", 10000);
        ReflectionTestUtils.setField(couponPolicy, "discountPrice", 3000);
        ReflectionTestUtils.setField(couponPolicy, "discountRate", null);
        ReflectionTestUtils.setField(couponPolicy, "maxDiscountPrice", 10000);
        ReflectionTestUtils.setField(couponPolicy, "state", true);

        entityManager.flush();
        couponPolicyRepository.save(couponPolicy);
    }

    @Test
    @DisplayName("쿠폰 정책 목록 가져오기 테스트")
    void testGetCouponPolicy() {
        List<GetCouponPolicyResponseDto> results = couponPolicyRepository.getCouponPolicy();

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.get(0).getId()).isNotNull();
        Assertions.assertThat(results.get(0).getMinOrderPrice()).isEqualTo(couponPolicy.getMinOrderPrice());
        Assertions.assertThat(results.get(0).getDiscountPrice()).isEqualTo(couponPolicy.getDiscountPrice());
        Assertions.assertThat(results.get(0).getDiscountRate()).isEqualTo(couponPolicy.getDiscountRate());
        Assertions.assertThat(results.get(0).getMaxDiscountPrice()).isEqualTo(couponPolicy.getMaxDiscountPrice());
    }


}