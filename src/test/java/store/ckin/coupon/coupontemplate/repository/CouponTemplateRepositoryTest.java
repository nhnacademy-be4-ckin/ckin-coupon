package store.ckin.coupon.coupontemplate.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.model.CouponTemplateType;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 16
 */
@DataJpaTest
class CouponTemplateRepositoryTest {
    @Autowired
    CouponTemplateRepository couponTemplateRepository;

    @Autowired
    TestEntityManager entityManager;

    CouponTemplate birthCouponTemplate;
    CouponTemplate bookCouponTemplate;
    CouponTemplate categoryCouponTemplate;
    CouponTemplateType birthType;
    CouponTemplateType bookType;
    CouponTemplateType categoryType;

    @BeforeEach
    void setUp() {
        birthType = new CouponTemplateType(1L, "생일 쿠폰");
        bookType = new CouponTemplateType(2L, "도서 쿠폰");
        categoryType = new CouponTemplateType(3L, "카테고리 쿠폰");

        entityManager.persist(birthType);
        entityManager.persist(bookType);
        entityManager.persist(categoryType);

        birthCouponTemplate = CouponTemplate.builder()
                .policyId(1L)
                .bookId(null)
                .categoryId(null)
                .name("1월 생일 쿠폰")
                .amount(100L)
                .type(birthType)
                .build();
        couponTemplateRepository.save(birthCouponTemplate);

        bookCouponTemplate = CouponTemplate.builder()
                .policyId(1L)
                .bookId(1L)
                .categoryId(null)
                .name("사람은 무엇으로 사는가 - 도서 쿠폰")
                .amount(100L)
                .type(bookType)
                .build();
        couponTemplateRepository.save(bookCouponTemplate);

        categoryCouponTemplate = CouponTemplate.builder()
                .policyId(1L)
                .bookId(null)
                .categoryId(1L)
                .name("소설 - 카테고리 쿠폰")
                .amount(100L)
                .type(categoryType)
                .build();
        couponTemplateRepository.save(categoryCouponTemplate);
    }

    @Test
    @DisplayName("쿠폰 템플릿 목록 가져오기 테스트")
    void testGetCouponTemplateList() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<GetCouponTemplateResponseDto> results = couponTemplateRepository.getCouponTemplateList(pageable, birthType.getId());

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getId()).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(birthCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(birthCouponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId()).isEqualTo(birthCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(birthCouponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getAmount()).isEqualTo(birthCouponTemplate.getAmount());
    }

    @Test
    @DisplayName("쿠폰 템플릿 가져오기 테스트")
    void testGetCouponTemplate() {
        Optional<GetCouponTemplateResponseDto> results = couponTemplateRepository.getCouponTemplate(bookCouponTemplate.getId());

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.get().getId()).isNotNull();
        Assertions.assertThat(results.get().getPolicyId()).isEqualTo(bookCouponTemplate.getPolicyId());
        Assertions.assertThat(results.get().getBookId()).isEqualTo(bookCouponTemplate.getBookId());
        Assertions.assertThat(results.get().getCategoryId()).isEqualTo(bookCouponTemplate.getCategoryId());
        Assertions.assertThat(results.get().getName()).isEqualTo(bookCouponTemplate.getName());
        Assertions.assertThat(results.get().getAmount()).isEqualTo(bookCouponTemplate.getAmount());
    }
}