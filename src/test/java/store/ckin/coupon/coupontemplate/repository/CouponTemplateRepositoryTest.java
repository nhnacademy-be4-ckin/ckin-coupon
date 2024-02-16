package store.ckin.coupon.coupontemplate.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.policy.model.CouponCode;
import store.ckin.coupon.policy.model.CouponPolicy;
import store.ckin.coupon.policy.repository.CouponPolicyRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    CouponTemplate couponTemplate;

    @BeforeEach
    void setUp() {
        couponTemplate = new CouponTemplate();

        ReflectionTestUtils.setField(couponTemplate, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplate, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplate, "categoryId", 1L);
        ReflectionTestUtils.setField(couponTemplate, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponTemplate, "amount", 100L);
        couponTemplateRepository.save(couponTemplate);
    }

    @Test
    @DisplayName("쿠폰 템플릿 목록 가져오기 테스트")
    void testGetCouponTemplateList() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<GetCouponTemplateResponseDto> results = couponTemplateRepository.getCouponTemplateList(pageable);

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getId()).isNotNull();
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(couponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(couponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId()).isEqualTo(couponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(couponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getAmount()).isEqualTo(couponTemplate.getAmount());
    }

    @Test
    @DisplayName("쿠폰 템플릿 가져오기 테스트")
    void testGetCouponTemplate() {
        Optional<GetCouponTemplateResponseDto> results = couponTemplateRepository.getCouponTemplate(couponTemplate.getId());

        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.get().getId()).isNotNull();
        Assertions.assertThat(results.get().getPolicyId()).isEqualTo(couponTemplate.getPolicyId());
        Assertions.assertThat(results.get().getBookId()).isEqualTo(couponTemplate.getBookId());
        Assertions.assertThat(results.get().getCategoryId()).isEqualTo(couponTemplate.getCategoryId());
        Assertions.assertThat(results.get().getName()).isEqualTo(couponTemplate.getName());
        Assertions.assertThat(results.get().getAmount()).isEqualTo(couponTemplate.getAmount());
    }

}