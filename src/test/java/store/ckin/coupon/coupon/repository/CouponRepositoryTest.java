package store.ckin.coupon.coupon.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.model.CouponTemplateType;

import javax.persistence.EntityManager;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CouponRepositoryTest
 *
 * @author : gaeun
 * @version : 2024. 02. 22
 */
@DataJpaTest
class CouponRepositoryTest {
    @Autowired
    CouponRepository couponRepository;

    @Autowired
    EntityManager entityManager;
    CouponTemplate birthCouponTemplate;
    CouponTemplate bookCouponTemplate;
    CouponTemplate categoryCouponTemplate;
    CouponTemplateType birthType;
    CouponTemplateType bookType;
    CouponTemplateType categoryType;
    Coupon birthCoupon;
    Coupon bookCoupon;
    Coupon categoryCoupon;
    Pageable pageable;
    @BeforeEach
    void setUp() {
        entityManager
                .createNativeQuery("ALTER TABLE Coupon ALTER COLUMN coupon_id RESTART WITH 1")
                .executeUpdate();
        entityManager
                .createNativeQuery("ALTER TABLE CouponTemplate ALTER COLUMN coupontemplate_id RESTART WITH 1")
                .executeUpdate();
        entityManager
                .createNativeQuery("ALTER TABLE CouponTemplateType ALTER COLUMN template_type_id RESTART WITH 1")
                .executeUpdate();
        pageable = PageRequest.of(0, 3);

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
        entityManager.persist(birthCouponTemplate);

        bookCouponTemplate = CouponTemplate.builder()
                .policyId(1L)
                .bookId(1L)
                .categoryId(null)
                .name("사람은 무엇으로 사는가 - 도서 쿠폰")
                .amount(100L)
                .type(bookType)
                .build();
        entityManager.persist(bookCouponTemplate);

        categoryCouponTemplate = CouponTemplate.builder()
                .policyId(1L)
                .bookId(null)
                .categoryId(1L)
                .name("소설 - 카테고리 쿠폰")
                .amount(100L)
                .type(categoryType)
                .build();
        entityManager.persist(categoryCouponTemplate);

        birthCoupon = Coupon.builder()
                .memberId(1L)
                .couponTemplateId(1L)
                .expirationDate(Date.valueOf("2024-05-30"))
                .issueDate(Date.valueOf("2024-02-22"))
                .usedDate(Date.valueOf("2024-02-24"))
                .build();
        couponRepository.save(birthCoupon);

        bookCoupon = Coupon.builder()
                .memberId(2L)
                .couponTemplateId(2L)
                .expirationDate(Date.valueOf("2024-05-30"))
                .issueDate(Date.valueOf("2024-02-22"))
                .usedDate(null)
                .build();
        couponRepository.save(bookCoupon);

        categoryCoupon = Coupon.builder()
                .memberId(3L)
                .couponTemplateId(3L)
                .expirationDate(Date.valueOf("2024-05-30"))
                .issueDate(Date.valueOf("2024-02-22"))
                .usedDate(null)
                .build();
        couponRepository.save(categoryCoupon);
    }

//    @BeforeEach
    @DisplayName("save Test")
    void saveEntity() {
        birthCoupon = Coupon.builder()
                .memberId(1L)
                .couponTemplateId(1L)
                .expirationDate(Date.valueOf("2024-05-30"))
                .issueDate(Date.valueOf("2024-02-22"))
                .usedDate(Date.valueOf("2024-02-24"))
                .build();
        couponRepository.save(birthCoupon);

        bookCoupon = Coupon.builder()
                .memberId(2L)
                .couponTemplateId(2L)
                .expirationDate(Date.valueOf("2024-05-30"))
                .issueDate(Date.valueOf("2024-02-22"))
                .usedDate(null)
                .build();
        couponRepository.save(bookCoupon);

        categoryCoupon = Coupon.builder()
                .memberId(3L)
                .couponTemplateId(3L)
                .expirationDate(Date.valueOf("2024-05-30"))
                .issueDate(Date.valueOf("2024-02-22"))
                .usedDate(null)
                .build();
        couponRepository.save(categoryCoupon);
    }

    @Test
    @DisplayName("회원의 사용된 쿠폰 가져오기")
    void testGetUsedCouponByMember() {
        Page<GetCouponResponseDto> results = couponRepository.getCouponByMember(pageable, 1L);

        Assertions.assertThat(results.getNumber()).isEqualTo(pageable.getPageNumber());
        Assertions.assertThat(results.getContent().get(0).getMemberId()).isEqualTo(birthCoupon.getMemberId());
        Assertions.assertThat(results.getContent().get(0).getCouponTemplateId()).isEqualTo(birthCoupon.getCouponTemplateId());
        Assertions.assertThat(results.getContent().get(0).getUsedDate().getMonth()).isEqualTo(birthCoupon.getUsedDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getExpirationDate().getMonth()).isEqualTo(birthCoupon.getExpirationDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getIssueDate().getMonth()).isEqualTo(birthCoupon.getIssueDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(birthCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(birthCouponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId()).isEqualTo(birthCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(birthCouponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getTypeId()).isEqualTo(birthType.getId());
    }

    @Test
    @DisplayName("회원의 미사용 쿠폰 가져오기")
    void testGetUnUsedCouponByMember() {
        Page<GetCouponResponseDto> results = couponRepository.getCouponByMember(pageable, 2L);

        Assertions.assertThat(results.getNumber()).isEqualTo(pageable.getPageNumber());
        Assertions.assertThat(results.getContent().get(0).getMemberId()).isEqualTo(bookCoupon.getMemberId());
        Assertions.assertThat(results.getContent().get(0).getCouponTemplateId()).isEqualTo(bookCoupon.getCouponTemplateId());
        Assertions.assertThat(results.getContent().get(0).getUsedDate()).isEqualTo(bookCoupon.getUsedDate());
        Assertions.assertThat(results.getContent().get(0).getExpirationDate().getMonth()).isEqualTo(bookCoupon.getExpirationDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getIssueDate().getMonth()).isEqualTo(bookCoupon.getIssueDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(bookCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(bookCouponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId()).isEqualTo(bookCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(bookCouponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getTypeId()).isEqualTo(bookType.getId());
    }

    @Test
    @DisplayName("쿠폰 타입으로 쿠폰 목록 가져오기")
    void testGetCouponList() {
        Page<GetCouponResponseDto> results = couponRepository.getCouponList(pageable, 2L);

        Assertions.assertThat(results.getNumber()).isEqualTo(pageable.getPageNumber());
        Assertions.assertThat(results.getContent().get(0).getMemberId()).isEqualTo(bookCoupon.getMemberId());
        Assertions.assertThat(results.getContent().get(0).getCouponTemplateId()).isEqualTo(bookCoupon.getCouponTemplateId());
        Assertions.assertThat(results.getContent().get(0).getUsedDate()).isEqualTo(bookCoupon.getUsedDate());
        Assertions.assertThat(results.getContent().get(0).getExpirationDate().getMonth()).isEqualTo(bookCoupon.getExpirationDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getIssueDate().getMonth()).isEqualTo(bookCoupon.getIssueDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(bookCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(bookCouponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId()).isEqualTo(bookCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(bookCouponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getTypeId()).isEqualTo(bookType.getId());
    }

    @Test
    @DisplayName("모든 쿠폰 목록 가져오기")
    void testGetAllCouponList() {
        Page<GetCouponResponseDto> results = couponRepository.getAllCouponList(pageable);

        Assertions.assertThat(results.getNumber()).isEqualTo(pageable.getPageNumber());
        Assertions.assertThat(results.getContent().size()).isEqualTo(3);
        Assertions.assertThat(results.getContent().get(0).getMemberId()).isEqualTo(birthCoupon.getMemberId());
        Assertions.assertThat(results.getContent().get(0).getCouponTemplateId()).isEqualTo(birthCoupon.getCouponTemplateId());
        Assertions.assertThat(results.getContent().get(0).getUsedDate().getMonth()).isEqualTo(birthCoupon.getUsedDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getExpirationDate().getMonth()).isEqualTo(birthCoupon.getExpirationDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getIssueDate().getMonth()).isEqualTo(birthCoupon.getIssueDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(birthCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(birthCouponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId()).isEqualTo(birthCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(birthCouponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getTypeId()).isEqualTo(birthType.getId());
    }

    @Test
    @DisplayName("쿠폰 아이디로 쿠폰 가져오기")
    void testGetCouponByCouponId() {
        GetCouponResponseDto result = couponRepository.getCouponByCouponId(1L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getMemberId()).isEqualTo(birthCoupon.getMemberId());
        Assertions.assertThat(result.getCouponTemplateId()).isEqualTo(birthCoupon.getCouponTemplateId());
        Assertions.assertThat(result.getUsedDate().getMonth()).isEqualTo(birthCoupon.getUsedDate().getMonth());
        Assertions.assertThat(result.getExpirationDate().getMonth()).isEqualTo(birthCoupon.getExpirationDate().getMonth());
        Assertions.assertThat(result.getIssueDate().getMonth()).isEqualTo(birthCoupon.getIssueDate().getMonth());
        Assertions.assertThat(result.getPolicyId()).isEqualTo(birthCouponTemplate.getPolicyId());
        Assertions.assertThat(result.getBookId()).isEqualTo(birthCouponTemplate.getBookId());
        Assertions.assertThat(result.getCategoryId()).isEqualTo(birthCouponTemplate.getCategoryId());
        Assertions.assertThat(result.getName()).isEqualTo(birthCouponTemplate.getName());
        Assertions.assertThat(result.getTypeId()).isEqualTo(birthType.getId());
    }

    @Test
    @DisplayName("회원의 쿠폰 목록 가져오기")
    void testGetCouponByMember() {
        Page<GetCouponResponseDto> results = couponRepository.getCouponByMember(pageable, 1L);

        Assertions.assertThat(results.getNumber()).isEqualTo(pageable.getPageNumber());
        Assertions.assertThat(results.getContent().get(0).getMemberId()).isEqualTo(birthCoupon.getMemberId());
        Assertions.assertThat(results.getContent().get(0).getCouponTemplateId()).isEqualTo(birthCoupon.getCouponTemplateId());
        Assertions.assertThat(results.getContent().get(0).getUsedDate().getMonth()).isEqualTo(birthCoupon.getUsedDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getExpirationDate().getMonth()).isEqualTo(birthCoupon.getExpirationDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getIssueDate().getMonth()).isEqualTo(birthCoupon.getIssueDate().getMonth());
        Assertions.assertThat(results.getContent().get(0).getPolicyId()).isEqualTo(birthCouponTemplate.getPolicyId());
        Assertions.assertThat(results.getContent().get(0).getBookId()).isEqualTo(birthCouponTemplate.getBookId());
        Assertions.assertThat(results.getContent().get(0).getCategoryId()).isEqualTo(birthCouponTemplate.getCategoryId());
        Assertions.assertThat(results.getContent().get(0).getName()).isEqualTo(birthCouponTemplate.getName());
        Assertions.assertThat(results.getContent().get(0).getTypeId()).isEqualTo(birthType.getId());
    }
}