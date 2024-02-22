package store.ckin.coupon.coupontemplate.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;

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

    CouponTemplate birthCouponTemplate;
    CouponTemplate bookCouponTemplate;
    CouponTemplate categoryCouponTemplate;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(birthCouponTemplate, "policyId", 1L);
        ReflectionTestUtils.setField(birthCouponTemplate, "bookId", null);
        ReflectionTestUtils.setField(birthCouponTemplate, "categoryId", null);
        ReflectionTestUtils.setField(birthCouponTemplate, "name", "1월 생일 쿠폰");
        ReflectionTestUtils.setField(birthCouponTemplate, "amount", 100L);
        couponTemplateRepository.save(birthCouponTemplate);

        ReflectionTestUtils.setField(bookCouponTemplate, "policyId", 1L);
        ReflectionTestUtils.setField(bookCouponTemplate, "bookId", 1L);
        ReflectionTestUtils.setField(bookCouponTemplate, "categoryId", null);
        ReflectionTestUtils.setField(bookCouponTemplate, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(bookCouponTemplate, "amount", 100L);
        couponTemplateRepository.save(bookCouponTemplate);

        ReflectionTestUtils.setField(categoryCouponTemplate, "policyId", 1L);
        ReflectionTestUtils.setField(categoryCouponTemplate, "bookId", null);
        ReflectionTestUtils.setField(categoryCouponTemplate, "categoryId", 1L);
        ReflectionTestUtils.setField(categoryCouponTemplate, "name", "소설 - 카테고리 쿠폰");
        ReflectionTestUtils.setField(categoryCouponTemplate, "amount", 100L);
        couponTemplateRepository.save(categoryCouponTemplate);
    }

    @Test
    @DisplayName("쿠폰 템플릿 목록 가져오기 테스트")
    void testGetCouponTemplateList() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<GetCouponTemplateResponseDto> results = couponTemplateRepository.getCouponTemplateList(pageable, typeId);

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

    @Test
    @DisplayName("생일 쿠폰 템플릿 목록 가져오기 테스트")
    void testGetBirthCouponTemplate() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<GetCouponTemplateResponseDto> results = couponTemplateRepository.getBirthCouponTemplate(pageable);

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getId()).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(birthCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(birthCouponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId()).isEqualTo(birthCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(birthCouponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getAmount()).isEqualTo(birthCouponTemplate.getAmount());
    }

    @Test
    @DisplayName("도서 쿠폰 템플릿 목록 가져오기 테스트")
    void testGetBookCouponTemplate() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<GetCouponTemplateResponseDto> results = couponTemplateRepository.getBookCouponTemplate(pageable);

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getId()).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(bookCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(bookCouponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId()).isEqualTo(bookCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(bookCouponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getAmount()).isEqualTo(bookCouponTemplate.getAmount());
    }

    @Test
    @DisplayName("카테고리 쿠폰 템플릿 목록 가져오기 테스트")
    void testGetCategoryCouponTemplate() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<GetCouponTemplateResponseDto> results = couponTemplateRepository.getCategoryTemplate(pageable);

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getId()).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(categoryCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(categoryCouponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId()).isEqualTo(categoryCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(categoryCouponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getAmount()).isEqualTo(categoryCouponTemplate.getAmount());
    }

}