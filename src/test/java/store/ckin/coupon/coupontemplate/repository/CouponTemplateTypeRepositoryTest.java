package store.ckin.coupon.coupontemplate.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import store.ckin.coupon.coupontemplate.model.CouponTemplateType;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 23
 */
@DataJpaTest
class CouponTemplateTypeRepositoryTest {
    @Autowired
    CouponTemplateTypeRepository couponTemplateTypeRepository;
    CouponTemplateType birthType;

    @Test
    @DisplayName("save test")
    void testSave() {
        birthType = new CouponTemplateType(1L, "생일 쿠폰");

        CouponTemplateType birthCouponTemplateType = couponTemplateTypeRepository.save(birthType);

        Assertions.assertThat(birthCouponTemplateType.getId()).isNotNull();
        Assertions.assertThat(birthCouponTemplateType.getName()).isEqualTo(birthCouponTemplateType.getName());
    }
}