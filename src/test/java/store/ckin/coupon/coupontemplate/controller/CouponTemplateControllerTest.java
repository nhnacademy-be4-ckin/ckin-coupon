package store.ckin.coupon.coupontemplate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.exception.CouponTemplateNotFoundException;
import store.ckin.coupon.coupontemplate.service.CouponTemplateService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.web.servlet.function.RequestPredicates.param;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 16
 */
@WebMvcTest(CouponTemplateController.class)
class CouponTemplateControllerTest {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @MockBean
    CouponTemplateService couponTemplateService;

    CreateCouponTemplateRequestDto couponTemplateRequestDto;
    GetCouponTemplateResponseDto couponTemplateResponseDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        couponTemplateRequestDto = new CreateCouponTemplateRequestDto();
        couponTemplateResponseDto = new GetCouponTemplateResponseDto(1L, 1L, 1L, null, "사람은 무엇으로 사는가  - 도서 쿠폰", 100L);
    }

    @Test
    @DisplayName("쿠폰 템플릿 목록 조회 테스트")
    void getAllCouponTemplateTest() throws Exception {
        PageRequest pageable = PageRequest.of(0, 5);
        PageImpl<GetCouponTemplateResponseDto> page = new PageImpl<>(List.of(couponTemplateResponseDto), pageable, 1);

        when(couponTemplateService.getCouponTemplateList(pageable))
                .thenReturn(page);

        mockMvc.perform(get("/couponTemplate")
                        .param("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .param("size", objectMapper.writeValueAsString(pageable.getPageSize()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id",is(couponTemplateResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.content[0].policyId",is(couponTemplateResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].bookId",is(couponTemplateResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.content[0].categoryId",is(couponTemplateResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.content[0].name",equalTo(couponTemplateResponseDto.getName())))
                .andExpect(jsonPath("$.content[0].amount",is(couponTemplateResponseDto.getAmount()), Long.class))
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 템플릿 단일 조회 테스트")
    void getCouponTemplateByIdTest() throws Exception {
        when(couponTemplateService.getCouponTemplate(anyLong())).thenReturn(couponTemplateResponseDto);

        mockMvc.perform(get("/couponTemplate/{couponTemplateId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(couponTemplateResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.policyId",is(couponTemplateResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.bookId",is(couponTemplateResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.categoryId",is(couponTemplateResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.name",equalTo(couponTemplateResponseDto.getName())))
                .andExpect(jsonPath("$.amount",is(couponTemplateResponseDto.getAmount()), Long.class))
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 템플릿 등록 테스트: 성공")
    void createCouponTemplateTest() throws Exception {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "해리포터 전집");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        mockMvc.perform(post("/couponTemplate")
                .content(objectMapper.writeValueAsString(couponTemplateRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 템플릿 등록 테스트: 실패")
    void createCouponTemplateTest_X() throws Exception {

        mockMvc.perform(post("/couponTemplate")
                        .content(objectMapper.writeValueAsString(couponTemplateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 템플릿 수정 테스트: 성공")
    void updateCouponTemplateTest() throws Exception {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "해리포터 전집");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);

        mockMvc.perform(put("/couponTemplate/{couponTemplateId}", 1L)
                        .content(objectMapper.writeValueAsString(couponTemplateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 템플릿 수정 테스트: 실패")
    void updateCouponTemplateTest_X() throws Exception {

        mockMvc.perform(put("/couponTemplate/{couponTemplateId}", 1L)
                        .content(objectMapper.writeValueAsString(couponTemplateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 템플릿 삭제 테스트")
    void deleteCouponTemplateTest() throws Exception {

        mockMvc.perform(delete("/couponTemplate/{couponTemplateId}", 1L))
                .andExpect(status().isOk())
                .andDo(print());
    }


}