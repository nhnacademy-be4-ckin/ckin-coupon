package store.ckin.coupon.policy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import store.ckin.coupon.policy.dto.request.CreateCouponPolicyRequestDto;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;
import store.ckin.coupon.policy.exception.CouponCodeNotFoundException;
import store.ckin.coupon.policy.model.CouponCode;
import store.ckin.coupon.policy.repository.CouponCodeRepository;
import store.ckin.coupon.policy.repository.CouponPolicyRepository;
import store.ckin.coupon.policy.service.impl.CouponPolicyServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 16
 */
@ExtendWith(SpringExtension.class)
@Import(CouponPolicyServiceImpl.class)
class CouponPolicyServiceTest {
    CouponPolicyService couponPolicyService;

    @MockBean
    CouponPolicyRepository couponPolicyRepository;
    @MockBean
    CouponCodeRepository couponCodeRepository;
    @MockBean
    ObjectMapper objectMapper;

    CouponCode couponCode;
    CreateCouponPolicyRequestDto couponPolicyRequestDto;
    GetCouponPolicyResponseDto couponPolicyResponseDto;

    @BeforeEach
    void setUp() {
        couponPolicyService = new CouponPolicyServiceImpl(couponPolicyRepository, couponCodeRepository);
        couponCode = new CouponCode("정액");
        couponPolicyRequestDto = new CreateCouponPolicyRequestDto();
        couponPolicyResponseDto = new GetCouponPolicyResponseDto(1L, 10000, 3000, null, 10000);
    }

    @Test
    @DisplayName("쿠폰 정책 등록 테스트: 성공")
    void testCreateCouponPolicy() {
        ReflectionTestUtils.setField(couponPolicyRequestDto, "couponCodeId", 1L);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "minOrderPrice", 10000);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "discountPrice", 3000);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "discountRate", null);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "maxDiscountPrice", 10000);

        when(couponCodeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(couponCode));

        couponPolicyService.createCouponPolicy(couponPolicyRequestDto);

        verify(couponCodeRepository, times(1))
                .findById(anyLong());
        verify(couponPolicyRepository, times(1))
                .save(any());
    }

    @Test
    @DisplayName("쿠폰 정책 등록 테스트: 실패")
    void testCreateCouponPolicy_X() {
        ReflectionTestUtils.setField(couponPolicyRequestDto, "couponCodeId", 1L);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "minOrderPrice", 10000);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "discountPrice", 3000);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "discountRate", null);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "maxDiscountPrice", 10000);

        when(couponCodeRepository.findById(anyLong())).thenThrow(new CouponCodeNotFoundException());

        assertThrows(CouponCodeNotFoundException.class, () -> couponPolicyService.createCouponPolicy(couponPolicyRequestDto));
    }

    @Test
    @DisplayName("쿠폰 정책 목록 조회 테스트")
    void testGetCouponPolicyList() {
        when(couponPolicyRepository.getCouponPolicy()).thenReturn(List.of(couponPolicyResponseDto));

        couponPolicyService.getCouponPolicyList();

        verify(couponPolicyRepository, times(1))
                .getCouponPolicy();
    }

}