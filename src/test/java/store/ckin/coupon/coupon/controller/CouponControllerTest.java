package store.ckin.coupon.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.service.CouponService;
import store.ckin.coupon.coupontemplate.controller.CouponTemplateController;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.service.CouponTemplateService;

import java.sql.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 23
 */
@WebMvcTest(CouponController.class)
class CouponControllerTest {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @MockBean
    CouponService couponService;

    CreateCouponRequestDto couponRequestDto;
    GetCouponResponseDto couponResponseDto;
    Pageable pageable;
    PageImpl<GetCouponResponseDto> page;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
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
    }

    @Test
    @DisplayName("쿠폰 생성 테스트")
    void testCreateCoupon() throws Exception {

        mockMvc.perform(post("/coupon")
                        .param("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .param("size", objectMapper.writeValueAsString(pageable.getPageSize()))
                        .content(objectMapper.writeValueAsString(couponRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 목록 조회 테스트 : typeId가 있는 경우")
    void testGetAllCouponList() throws Exception {
        when(couponService.getCouponList(any(), anyLong())).thenReturn(page);

        mockMvc.perform(get("/coupon")
                        .param("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .param("size", objectMapper.writeValueAsString(pageable.getPageSize()))
                        .param("typeId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id",is(couponResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.content[0].memberId",is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].couponTemplateId",is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].policyId",is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].bookId",is(couponResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.content[0].categoryId",is(couponResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.content[0].typeId",is(couponResponseDto.getTypeId()), Long.class))
                .andExpect(jsonPath("$.content[0].name",equalTo(couponResponseDto.getName())))
                .andExpect(jsonPath("$.content[0].expirationDate",equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate",is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate",is(couponResponseDto.getUsedDate())))
                .andDo(print());
    }

}
