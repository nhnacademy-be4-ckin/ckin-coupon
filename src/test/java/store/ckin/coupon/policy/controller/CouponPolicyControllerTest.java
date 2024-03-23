package store.ckin.coupon.policy.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import store.ckin.coupon.policy.dto.request.CreateCouponPolicyRequestDto;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;
import store.ckin.coupon.policy.service.CouponPolicyService;

/**
 * CouponPolicyControllerTest
 *
 * @author : gaeun
 * @version : 2024. 02. 10
 */

@WebMvcTest(CouponPolicyController.class)
class CouponPolicyControllerTest {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @MockBean
    CouponPolicyService couponPolicyService;

    CreateCouponPolicyRequestDto couponPolicyRequestDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        couponPolicyRequestDto = new CreateCouponPolicyRequestDto();
    }

    @Test
    @DisplayName("쿠폰 정책 목록 조회 테스트")
    void getCouponPolicyTest() throws Exception {
        GetCouponPolicyResponseDto dto = new GetCouponPolicyResponseDto(1L, 10000, 3000, null, 10000);
        GetCouponPolicyResponseDto dto2 = new GetCouponPolicyResponseDto(2L, 10000, 3000, null, 10000);

        given(couponPolicyService.getCouponPolicyList()).willReturn(List.of(dto, dto2));

        mockMvc.perform(get("/coupon/couponPolicy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(dto.getId()), Long.class))
                .andExpect(jsonPath("$[0].minOrderPrice", equalTo(dto.getMinOrderPrice())))
                .andExpect(jsonPath("$[0].discountPrice", equalTo(dto.getDiscountPrice())))
                .andExpect(jsonPath("$[0].discountRate", equalTo(dto.getDiscountRate())))
                .andExpect(jsonPath("$[0].maxDiscountPrice", equalTo(dto.getMaxDiscountPrice())))
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 정책 등록 테스트: 성공")
    void couponPolicyCreateTest_O() throws Exception {

        ReflectionTestUtils.setField(couponPolicyRequestDto, "couponCodeId", 1L);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "minOrderPrice", 10000);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "discountPrice", 3000);
        ReflectionTestUtils.setField(couponPolicyRequestDto, "maxDiscountPrice", 10000);


        mockMvc.perform(post("/coupon/couponPolicy")
                        .content(objectMapper.writeValueAsString(couponPolicyRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 정책 등록 테스트: 실패")
    void couponPolicyCreateTest_X() throws Exception {

        mockMvc.perform(post("/coupon/couponPolicy")
                        .content(objectMapper.writeValueAsString(couponPolicyRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

}