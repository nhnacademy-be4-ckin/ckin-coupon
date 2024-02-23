package store.ckin.coupon.coupon.service;

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
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.exception.CouponNotFoundException;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupon.repository.CouponRepository;
import store.ckin.coupon.coupon.service.impl.CouponServiceImpl;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.exception.CouponTemplateTypeNotFoundException;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.model.CouponTemplateType;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateRepository;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateTypeRepository;
import store.ckin.coupon.coupontemplate.service.CouponTemplateService;
import store.ckin.coupon.coupontemplate.service.impl.CouponTemplateServiceImpl;
import store.ckin.coupon.policy.model.CouponCode;
import store.ckin.coupon.policy.model.CouponPolicy;
import store.ckin.coupon.policy.repository.CouponPolicyRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

/**
 * CouponServiceTest
 *
 * @author : gaeun
 * @version : 2024. 02. 23
 */
@ExtendWith(SpringExtension.class)
@Import(CouponServiceImpl.class)
class CouponServiceTest {
    CouponService couponService;

    @MockBean
    CouponTemplateRepository couponTemplateRepository;
    @MockBean
    CouponRepository couponRepository;
    @MockBean
    CouponTemplateTypeRepository couponTemplateTypeRepository;
    @MockBean
    ObjectMapper objectMapper;
    CreateCouponRequestDto couponRequestDto;
    GetCouponResponseDto couponResponseDto;
    CouponPolicy couponPolicy;
    CouponTemplate bookCouponTemplate;
    CouponTemplateType birthType;
    CouponTemplateType bookType;
    CouponTemplateType categoryType;
    Coupon coupon;
    Long typeId;
    Pageable pageable;
    PageImpl<GetCouponResponseDto> page;

    @BeforeEach
    void setUp() {
        typeId = 1L;
        birthType = new CouponTemplateType(1L, "생일 쿠폰");
        bookType = new CouponTemplateType(2L, "도서 쿠폰");
        categoryType = new CouponTemplateType(3L, "카테고리 쿠폰");

        couponPolicy = new CouponPolicy(1L, new CouponCode("정액"), 10000, 3000, null, 10000, true);
        bookCouponTemplate = new CouponTemplate(1L, 1L, 1L, null, "사람은 무엇으로 사는가 - 도서 쿠폰", 100L, bookType);
        couponService = new CouponServiceImpl(couponRepository, couponTemplateRepository, couponTemplateTypeRepository);
        couponRequestDto = new CreateCouponRequestDto();
        ReflectionTestUtils.setField(couponRequestDto, "memberId", 1L);
        ReflectionTestUtils.setField(couponRequestDto, "couponTemplateId", 1L);
        ReflectionTestUtils.setField(couponRequestDto, "expirationDate", Date.valueOf("2024-02-27"));
        ReflectionTestUtils.setField(couponRequestDto, "issueDate", Date.valueOf("2024-02-28"));
        ReflectionTestUtils.setField(couponRequestDto, "usedDate", null);

        couponResponseDto = new GetCouponResponseDto();
        ReflectionTestUtils.setField(couponResponseDto, "id", 1L);
        ReflectionTestUtils.setField(couponResponseDto, "memberId", 1L);
        ReflectionTestUtils.setField(couponResponseDto, "couponTemplateId", 1L);
        ReflectionTestUtils.setField(couponResponseDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponResponseDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponResponseDto, "categoryId", null);
        ReflectionTestUtils.setField(couponResponseDto, "typeId", 2L);
        ReflectionTestUtils.setField(couponResponseDto, "name", "사람은 무엇으로 사는가 - 도서 쿠폰");
        ReflectionTestUtils.setField(couponResponseDto, "expirationDate", Date.valueOf("2024-02-27"));
        ReflectionTestUtils.setField(couponResponseDto, "issueDate", Date.valueOf("2024-02-28"));
        ReflectionTestUtils.setField(couponResponseDto, "usedDate", null);

        pageable = PageRequest.of(0, 5);
        page = new PageImpl<>(List.of(couponResponseDto));

        coupon =  Coupon.builder()
                .id(1L)
                .memberId(1L)
                .couponTemplateId(1L)
                .expirationDate(Date.valueOf("2024-02-27"))
                .issueDate(Date.valueOf("2024-02-30"))
                .usedDate(null)
                .build();
    }

    @Test
    @DisplayName("쿠폰 생성 테스트")
    void testCreateCoupon() {
        when(couponTemplateTypeRepository.existsById(anyLong())).thenReturn(true);

        couponService.createCoupon(couponRequestDto);

        verify(couponTemplateTypeRepository, times(1))
                .existsById(anyLong());
        verify(couponRepository, times(1))
                .save(any());
    }

    @Test
    @DisplayName("쿠폰 조회 테스트 : 회원의 사용완료된 쿠폰")
    void testGetUsedCouponByMember() {
        when(couponRepository.getUsedCouponByMember(pageable, couponRequestDto.getMemberId())).thenReturn(page);

        couponService.getUsedCouponByMember(pageable, couponRequestDto.getMemberId());

        verify(couponRepository, times(1))
                .getUsedCouponByMember(any(), anyLong());
    }
    @Test
    @DisplayName("쿠폰 조회 테스트 : 회원의 미사용 쿠폰")
    void testGetUnUsedCouponByMember() {
        when(couponRepository.getUnUsedCouponByMember(pageable, couponRequestDto.getMemberId())).thenReturn(page);

        couponService.getUnUsedCouponByMember(pageable, couponRequestDto.getMemberId());

        verify(couponRepository, times(1))
                .getUnUsedCouponByMember(any(), anyLong());
    }

    @Test
    @DisplayName("쿠폰 목록 조회 테스트 : 타입별")
    void testGetCouponList() {
        when(couponTemplateTypeRepository.existsById(anyLong())).thenReturn(true);

        couponService.getCouponList(pageable, bookType.getId());

        verify(couponTemplateTypeRepository, times(1))
                .existsById(anyLong());
        verify(couponRepository, times(1))
                .getCouponList(any(), anyLong());
    }

    @Test
    @DisplayName("쿠폰 목록 조회 테스트 : 타입별 (실패)")
    void testGetCouponList_X() {
        when(couponTemplateTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CouponTemplateTypeNotFoundException.class, () -> couponService.getCouponList(pageable, bookType.getId()));
    }

    @Test
    @DisplayName("쿠폰 수정 테스트")
    void testUpdateCouponUsedDate() {
        when(couponRepository.findById(anyLong())).thenReturn(Optional.of(coupon));

        couponService.updateCouponUsedDate(1L);

        verify(couponRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @DisplayName("쿠폰 목록 조회 테스트")
    void testGetAllCouponList() {
        when(couponRepository.getAllCouponList(any())).thenReturn(page);

        couponService.getAllCouponList(pageable);

        verify(couponRepository, times(1))
                .getAllCouponList(any());
    }

    @Test
    @DisplayName("쿠폰 조회 테스트 : 쿠폰 아이디")
    void testGetCouponByCouponId() {
        when(couponRepository.existsById(anyLong())).thenReturn(true);
        when(couponRepository.getCouponByCouponId(anyLong())).thenReturn(couponResponseDto);

        couponService.getCouponByCouponId(1L);

        verify(couponRepository, times(1))
                .existsById(any());
        verify(couponRepository, times(1))
                .getCouponByCouponId(anyLong());
    }

    @Test
    @DisplayName("쿠폰 조회 테스트 : 쿠폰 아이디 (실패)")
    void testGetCouponByCouponId_X() {
        when(couponRepository.existsById(anyLong())).thenReturn(false);
        when(couponRepository.getCouponByCouponId(anyLong())).thenReturn(couponResponseDto);

        assertThrows(CouponNotFoundException.class, () -> couponService.getCouponByCouponId(1L));

    }

    @Test
    @DisplayName("쿠폰 조회 테스트 : 회원 아이디")
    void testGetCouponByMember() {
        when(couponRepository.getCouponByMember(any(), anyLong())).thenReturn(page);

        couponService.getCouponByMember(pageable, 1L);

        verify(couponRepository, times(1))
                .getCouponByMember(any(), anyLong());
    }
}