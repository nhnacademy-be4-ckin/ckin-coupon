package store.ckin.coupon.policy.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import store.ckin.coupon.policy.model.CouponCode;

@DataJpaTest
@Transactional
class CouponCodeRepositoryTest {

    @Autowired
    CouponCodeRepository couponCodeRepository;

    @Test
    @DisplayName("save 테스트")
    void testSave() {
        CouponCode couponCode = new CouponCode("정액");
        CouponCode result = couponCodeRepository.save(couponCode);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getName()).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(couponCode.getName());
    }

}