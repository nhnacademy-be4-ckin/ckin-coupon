package store.ckin.coupon.coupontemplate.repository;

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
    CouponTemplateType bookType;
    CouponTemplateType categoryType;

    @Test
    @DisplayName("save test")
    void testSave() {
        birthType = new CouponTemplateType(1L, "생일 쿠폰");
        bookType = new CouponTemplateType(2L, "도서 쿠폰");
        categoryType = new CouponTemplateType(3L, "카테고리 쿠폰");

        couponTemplateTypeRepository.save(birthType);
        couponTemplateTypeRepository.save(bookType);
        couponTemplateTypeRepository.save(categoryType);
    }
}