package store.ckin.coupon.coupontemplate.repository;

import java.sql.Date;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.model.CouponTemplateType;
import store.ckin.coupon.policy.model.CouponCode;
import store.ckin.coupon.policy.model.CouponPolicy;

/**
 * 쿠폰 템플릿 레파지토리 테스트 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 16
 */
@DataJpaTest
class CouponTemplateRepositoryTest {
    @Autowired
    CouponTemplateRepository couponTemplateRepository;

    @Autowired
    EntityManager entityManager;

    CouponTemplate birthCouponTemplate;
    CouponTemplate bookCouponTemplate;
    CouponTemplate categoryCouponTemplate;
    CouponTemplateType birthType;
    CouponTemplateType bookType;
    CouponTemplateType categoryType;
    CouponPolicy couponPolicy;
    CouponCode couponCode;

    @BeforeEach
    void setUp() {
        entityManager
                .createNativeQuery("ALTER TABLE CouponPolicy ALTER COLUMN couponpolicy_id RESTART WITH 1")
                .executeUpdate();
        birthType = new CouponTemplateType(1L, "생일 쿠폰");
        bookType = new CouponTemplateType(2L, "도서 쿠폰");
        categoryType = new CouponTemplateType(3L, "카테고리 쿠폰");
        couponCode = new CouponCode("정률");
        couponPolicy = CouponPolicy.builder()
                .couponCode(couponCode)
                .discountPrice(null)
                .maxDiscountPrice(20000)
                .minOrderPrice(10000)
                .discountRate(10)
                .state(true)
                .build();

        entityManager.persist(birthType);
        entityManager.persist(bookType);
        entityManager.persist(categoryType);
        entityManager.persist(couponCode);
        entityManager.persist(couponPolicy);

        birthCouponTemplate = CouponTemplate.builder()
                .policyId(1L)
                .categoryId(null)
                .name("1월 생일 쿠폰")
                .amount(100L)
                .type(birthType)
                .state(true)
                .duration(null)
                .amount(10L)
                .expirationDate(Date.valueOf("2024-03-31"))
                .build();
        couponTemplateRepository.save(birthCouponTemplate);

        bookCouponTemplate = CouponTemplate.builder()
                .policyId(1L)
                .bookId(1L)
                .categoryId(null)
                .name("사람은 무엇으로 사는가 - 도서 쿠폰")
                .amount(100L)
                .type(bookType)
                .state(true)
                .amount(10L)
                .duration(null)
                .expirationDate(Date.valueOf("2024-03-31"))
                .build();
        couponTemplateRepository.save(bookCouponTemplate);

        categoryCouponTemplate = CouponTemplate.builder()
                .policyId(1L)
                .bookId(null)
                .categoryId(1L)
                .name("소설 - 카테고리 쿠폰")
                .amount(100L)
                .type(categoryType)
                .amount(10L)
                .state(true)
                .duration(null)
                .expirationDate(Date.valueOf("2024-03-31"))
                .build();

        couponTemplateRepository.save(categoryCouponTemplate);
    }

    @Test
    @DisplayName("쿠폰 템플릿 목록 가져오기 테스트")
    void testGetCouponTemplateList() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<GetCouponTemplateResponseDto> results =
                couponTemplateRepository.getCouponTemplateList(pageable, birthType.getId());

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getId()).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(birthCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(birthCouponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId())
                .isEqualTo(birthCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(birthCouponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getAmount()).isEqualTo(birthCouponTemplate.getAmount());
    }

    @Test
    @DisplayName("쿠폰 템플릿 가져오기 테스트")
    void testGetCouponTemplate() {
        Optional<GetCouponTemplateResponseDto> results =
                couponTemplateRepository.getCouponTemplate(bookCouponTemplate.getId());

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.get().getId()).isNotNull();
        Assertions.assertThat(results.get().getPolicyId()).isEqualTo(bookCouponTemplate.getPolicyId());
        Assertions.assertThat(results.get().getBookId()).isEqualTo(bookCouponTemplate.getBookId());
        Assertions.assertThat(results.get().getCategoryId()).isEqualTo(bookCouponTemplate.getCategoryId());
        Assertions.assertThat(results.get().getName()).isEqualTo(bookCouponTemplate.getName());
        Assertions.assertThat(results.get().getAmount()).isEqualTo(bookCouponTemplate.getAmount());
    }

    @Test
    @DisplayName("단일 쿠폰 템플릿 조회 테스트: 생일쿠폰, 웰컴쿠폰")
    void testGetCouponTemplateByTypeId() {
        GetCouponTemplateResponseDto results = couponTemplateRepository.getCouponTemplateByTypeId(1L);

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.getId()).isNotNull();
        Assertions.assertThat(results.getPolicyId()).isEqualTo(birthCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getMinOrderPrice()).isEqualTo(couponPolicy.getMinOrderPrice());
        Assertions.assertThat(results.getDiscountPrice()).isEqualTo(couponPolicy.getDiscountPrice());
        Assertions.assertThat(results.getDiscountRate()).isEqualTo(couponPolicy.getDiscountRate());
        Assertions.assertThat(results.getMaxDiscountPrice()).isEqualTo(couponPolicy.getMaxDiscountPrice());
        Assertions.assertThat(results.getBookId()).isEqualTo(birthCouponTemplate.getBookId());
        Assertions.assertThat(results.getCategoryId()).isEqualTo(birthCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getName()).isEqualTo(birthCouponTemplate.getName());
        Assertions.assertThat(results.getAmount()).isEqualTo(birthCouponTemplate.getAmount());
        Assertions.assertThat(results.getState()).isEqualTo(birthCouponTemplate.getState());
        Assertions.assertThat(results.getDuration()).isEqualTo(birthCouponTemplate.getDuration());
        Assertions.assertThat(results.getExpirationDate()).isEqualTo(birthCouponTemplate.getExpirationDate());
    }
}