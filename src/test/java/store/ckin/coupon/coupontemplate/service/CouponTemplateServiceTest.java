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
import store.ckin.coupon.coupontemplate.exception.CouponTemplateTypeNotFoundException;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.model.CouponTemplateType;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateRepository;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateTypeRepository;
import store.ckin.coupon.coupontemplate.service.impl.CouponTemplateServiceImpl;
import store.ckin.coupon.policy.model.CouponCode;
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
    CouponTemplateTypeRepository couponTemplateTypeRepository;
    @MockBean
    ObjectMapper objectMapper;
    CreateCouponTemplateRequestDto couponTemplateRequestDto;
    GetCouponTemplateResponseDto couponTemplateResponseDto;
    CouponPolicy couponPolicy;
    CouponTemplate bookCouponTemplate;
    CouponTemplateType birthType;
    CouponTemplateType bookType;
    CouponTemplateType categoryType;
    Long typeId;

    @BeforeEach
    void setUp() {
        typeId = 1L;
        birthType = new CouponTemplateType(1L, "생일 쿠폰");
        bookType = new CouponTemplateType(2L, "도서 쿠폰");
        categoryType = new CouponTemplateType(3L, "카테고리 쿠폰");

        couponPolicy = new CouponPolicy(1L, new CouponCode("정액"), 10000, 3000, null, 10000, true);
        bookCouponTemplate = new CouponTemplate(1L, 1L, 1L, null, "사람은 무엇으로 사는가 - 도서 쿠폰", 100L, bookType);
        couponTemplateService = new CouponTemplateServiceImpl(couponTemplateRepository, couponTemplateTypeRepository, couponPolicyRepository);
        couponTemplateRequestDto = new CreateCouponTemplateRequestDto();
        couponTemplateResponseDto = new GetCouponTemplateResponseDto(1L, 1L, 1L, null, "사람은 무엇으로 사는가 - 도서 쿠폰", 100L, 2L);
    }

    @Test
    @DisplayName("쿠폰 템플릿 생성 테스트: 성공")
    void testCreateCouponTemplate() {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", null);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "typeId", bookType.getId());
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        when(couponPolicyRepository.existsById(anyLong())).thenReturn(true);
        when(couponTemplateTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(bookType));

        couponTemplateService.createCouponTemplate(couponTemplateRequestDto);

        verify(couponPolicyRepository, times(1))
                .existsById(anyLong());
        verify(couponTemplateTypeRepository, times(1))
                .findById(anyLong());
        verify(couponTemplateRepository, times(1))
                .save(any());
    }

    @Test
    @DisplayName("쿠폰 템플릿 생성 테스트: Not Found CouponPolicy 실패")
    void testCreateCouponTemplate_XPolicy() {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", null);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "typeId", bookType.getId());
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        when(couponPolicyRepository.findById(anyLong())).thenThrow(new CouponPolicyNotFoundException());
        when(couponTemplateTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(bookType));

        assertThrows(CouponPolicyNotFoundException.class, () -> couponTemplateService.createCouponTemplate(couponTemplateRequestDto));
    }

    @Test
    @DisplayName("쿠폰 템플릿 생성 테스트: Not Found CouponType 실패")
    void testCreateCouponTemplate_XType() {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", null);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "typeId", bookType.getId());
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        when(couponPolicyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(couponPolicy));
        when(couponTemplateTypeRepository.findById(anyLong())).thenThrow(new CouponTemplateTypeNotFoundException());

        assertThrows(CouponTemplateTypeNotFoundException.class, () -> couponTemplateService.createCouponTemplate(couponTemplateRequestDto));
    }
    @Test
    @DisplayName("쿠폰 템플릿 목록 조회 테스트")
    void testGetCouponTemplateList() {
        PageImpl<GetCouponTemplateResponseDto> page = new PageImpl<>(List.of(couponTemplateResponseDto));
        Pageable pageable = PageRequest.of(0, 5);

        when(couponTemplateTypeRepository.existsById(anyLong())).thenReturn(true);
        when(couponTemplateRepository.getCouponTemplateList(any(),anyLong())).thenReturn(page);

        couponTemplateService.getCouponTemplateList(pageable, typeId);

        verify(couponTemplateTypeRepository, times(1))
                .existsById(anyLong());
        verify(couponTemplateRepository, times(1))
                .getCouponTemplateList(any(), anyLong());
    }

    @Test
    @DisplayName("쿠폰 템플릿 목록 조회 테스트 : 실패")
    void testGetCouponTemplateList_X() {
        PageImpl<GetCouponTemplateResponseDto> page = new PageImpl<>(List.of(couponTemplateResponseDto));
        Pageable pageable = PageRequest.of(0, 5);

        when(couponTemplateTypeRepository.existsById(anyLong())).thenReturn(false);
        when(couponTemplateRepository.getCouponTemplateList(any(),anyLong())).thenReturn(page);

        assertThrows(CouponTemplateTypeNotFoundException.class, () -> couponTemplateService.getCouponTemplateList(pageable, typeId));
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
        ReflectionTestUtils.setField(couponTemplateRequestDto, "typeId", bookType.getId());
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        when(couponTemplateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(bookCouponTemplate));
        when(couponPolicyRepository.existsById(anyLong())).thenReturn(true);

        couponTemplateService.updateCouponTemplate(1L, couponTemplateRequestDto);

        verify(couponTemplateRepository, times(1))
                .findById(anyLong());
        verify(couponPolicyRepository, times(1))
                .existsById(anyLong());
    }


    @Test
    @DisplayName("쿠폰 템플릿 수정 테스트: 실패")
    void testUpdateCouponTemplate_X() {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", null);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "typeId", bookType.getId());
        ReflectionTestUtils.setField(couponTemplateRequestDto,  "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        when(couponTemplateRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(CouponTemplateNotFoundException.class, () -> couponTemplateService.updateCouponTemplate(1L, couponTemplateRequestDto));
    }

    @Test
    @DisplayName("쿠폰 템플릿 삭제 테스트")
    void testDeleteCouponTemplate() {
        when(couponTemplateRepository.findById(anyLong())).thenReturn(Optional.of(new CouponTemplate(1L, 1L, 1L, null, "template", 30L, new CouponTemplateType(1L, "생일 쿠폰"))));

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