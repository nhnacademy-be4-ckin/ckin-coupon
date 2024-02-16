package store.ckin.coupon.coupontemplate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.exception.CouponPolicyNotFoundException;
import store.ckin.coupon.coupontemplate.exception.CouponTemplateNotFoundException;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateRepository;
import store.ckin.coupon.coupontemplate.service.impl.CouponTemplateServiceImpl;
import store.ckin.coupon.policy.model.CouponPolicy;
import store.ckin.coupon.policy.repository.CouponPolicyRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 16
 */
@ExtendWith(SpringExtension.class)
@Import(CouponTemplateServiceImpl.class)
class CouponTemplateServiceTest {
    CouponTemplateService couponTemplateService;

    @MockBean
    CouponTemplateRepository couponTemplateRepository;
    @MockBean
    CouponPolicyRepository couponPolicyRepository;
    @MockBean
    ObjectMapper objectMapper;
    CreateCouponTemplateRequestDto couponTemplateRequestDto;
    GetCouponTemplateResponseDto couponTemplateResponseDto;
    CouponPolicy couponPolicy;

    @BeforeEach
    void setUp() {
        couponTemplateService = new CouponTemplateServiceImpl(couponTemplateRepository, couponPolicyRepository);
        couponTemplateRequestDto = new CreateCouponTemplateRequestDto();
        couponTemplateResponseDto = new GetCouponTemplateResponseDto(1L, 1L, 1L, null, "사람은 무엇으로 사는가 - 도서 쿠폰", 100L);
        couponPolicy = new CouponPolicy();
    }

    @Test
    @DisplayName("쿠폰 템플릿 생성 테스트: 성공")
    void testCreateCouponTemplate() {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", null);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        when(couponPolicyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(couponPolicy));

        couponTemplateService.createCouponTemplate(couponTemplateRequestDto);

        verify(couponPolicyRepository, times(1))
                .findById(anyLong());
        verify(couponTemplateRepository, times(1))
                .save(any());
    }

    @Test
    @DisplayName("쿠폰 템플릿 생성 테스트: 실패")
    void testCreateCouponTemplate_X() {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", null);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        when(couponPolicyRepository.findById(anyLong())).thenThrow(new CouponPolicyNotFoundException());

        assertThrows(CouponPolicyNotFoundException.class, () -> couponTemplateService.createCouponTemplate(couponTemplateRequestDto));
    }

    @Test
    @DisplayName("쿠폰 템플릿 목록 조회 테스트")
    void testGetCouponTemplateList() {
        PageImpl<GetCouponTemplateResponseDto> page = new PageImpl<>(List.of(couponTemplateResponseDto));
        Pageable pageable = PageRequest.of(0, 5);

        when(couponTemplateRepository.getCouponTemplateList(any())).thenReturn(page);

        couponTemplateService.getCouponTemplateList(pageable);

        verify(couponTemplateRepository, times(1))
                .getCouponTemplateList(any());
    }

    @Test
    @DisplayName("쿠폰 템플릿 단일 조회 테스트")
    void testGetCouponTemplate() {
        when(couponTemplateRepository.getCouponTemplate(anyLong())).thenReturn(Optional.ofNullable(couponTemplateResponseDto));

        couponTemplateService.getCouponTemplate(1L);

        verify(couponTemplateRepository, times(1))
                .getCouponTemplate(anyLong());
    }

    @Test
    @DisplayName("쿠폰 템플릿 단일 조회 테스트: 실패")
    void testGetCouponTemplate_X() {
        when(couponTemplateRepository.getCouponTemplate(anyLong())).thenReturn(Optional.empty());

        assertThrows(CouponTemplateNotFoundException.class, () -> couponTemplateService.getCouponTemplate(1L));
    }

    @Test
    @DisplayName("쿠폰 템플릿 수정 테스트")
    void testUpdateCouponTemplate() {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", null);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        when(couponTemplateRepository.existsById(anyLong())).thenReturn(true);

        couponTemplateService.updateCouponTemplate(1L, couponTemplateRequestDto);

        verify(couponTemplateRepository, times(1))
                .existsById(anyLong());
        verify(couponTemplateRepository, times(1))
                .save(any());
    }


    @Test
    @DisplayName("쿠폰 템플릿 수정 테스트: 실패")
    void testUpdateCouponTemplate_X() {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", null);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        when(couponTemplateRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(CouponTemplateNotFoundException.class, () -> couponTemplateService.updateCouponTemplate(1L, couponTemplateRequestDto));
    }

    @Test
    @DisplayName("쿠폰 템플릿 삭제 테스트")
    void testDeleteCouponTemplate() {
        when(couponTemplateRepository.findById(anyLong())).thenReturn(Optional.of(new CouponTemplate()));

        couponTemplateService.deleteCouponTemplate(1L);

        verify(couponTemplateRepository, times(1))
                .delete(any());
    }

    @Test
    @DisplayName("쿠폰 템플릿 삭제 테스트: 실패")
    void testDeleteCouponTemplate_X() {
        when(couponTemplateRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CouponTemplateNotFoundException.class, () -> couponTemplateService.deleteCouponTemplate(1L));

    }
}