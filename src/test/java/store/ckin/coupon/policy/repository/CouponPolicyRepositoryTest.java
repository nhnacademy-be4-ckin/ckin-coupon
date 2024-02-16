package store.ckin.coupon.policy.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;
import store.ckin.coupon.policy.model.CouponCode;
import store.ckin.coupon.policy.model.CouponPolicy;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 16
 */
@DataJpaTest
class CouponPolicyRepositoryTest {
    @Autowired
    CouponPolicyRepository couponPolicyRepository;

    @Autowired
    TestEntityManager entityManager;

    CouponCode couponCode;
    CouponPolicy couponPolicy;

    @BeforeEach
    void setUp() {
        couponCode = new CouponCode();
        couponPolicy = new CouponPolicy();
        ReflectionTestUtils.setField(couponCode, "name", "정액");
        entityManager.persist(couponCode);

        ReflectionTestUtils.setField(couponPolicy, "couponCode", couponCode);
        ReflectionTestUtils.setField(couponPolicy, "minOrderPrice", 10000);
        ReflectionTestUtils.setField(couponPolicy, "discountPrice", 3000);
        ReflectionTestUtils.setField(couponPolicy, "discountRate", null);
        ReflectionTestUtils.setField(couponPolicy, "maxDiscountPrice", 10000);
        ReflectionTestUtils.setField(couponPolicy, "state", true);
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