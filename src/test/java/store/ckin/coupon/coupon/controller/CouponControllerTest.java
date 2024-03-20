package store.ckin.coupon.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
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

import java.sql.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 23
 */
@AutoConfigureRestDocs
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
    List<GetCouponResponseDto> couponList;

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
        couponList = List.of(couponResponseDto);
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
//                .andDo(document("coupon/createCoupon/success",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestParameters(
//                                parameterWithName("page").description("지정할 페이지"),
//                                parameterWithName("size").description("한 페이지 당 표시할 개수")
//                        ),
//                        responseFields(
//
//
//                        )));
    }

    @Test
    @DisplayName("쿠폰 발급 기록을 확인하고 등록 테스트")
    void testCreateCouponByIds() throws Exception {

        mockMvc.perform(post("/coupon/members/{memberId}/{couponTemplateId}", 1L, 1L))
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
                .andExpect(jsonPath("$.content[0].id", is(couponResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.content[0].memberId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].couponTemplateId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].policyId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].bookId", is(couponResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.content[0].categoryId", is(couponResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.content[0].typeId", is(couponResponseDto.getTypeId()), Long.class))
                .andExpect(jsonPath("$.content[0].name", equalTo(couponResponseDto.getName())))
                .andExpect(jsonPath("$.content[0].expirationDate", equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate", is(couponResponseDto.getUsedDate())))
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 목록 조회 테스트 : typeId가 없는 경우")
    void testGetAllCouponList_X() throws Exception {
        when(couponService.getAllCouponList(any())).thenReturn(page);

        mockMvc.perform(get("/coupon")
                        .param("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .param("size", objectMapper.writeValueAsString(pageable.getPageSize()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(couponResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.content[0].memberId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].couponTemplateId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].policyId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].bookId", is(couponResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.content[0].categoryId", is(couponResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.content[0].typeId", is(couponResponseDto.getTypeId()), Long.class))
                .andExpect(jsonPath("$.content[0].name", equalTo(couponResponseDto.getName())))
                .andExpect(jsonPath("$.content[0].expirationDate", equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate", is(couponResponseDto.getUsedDate())))
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 조회 테스트 : 쿠폰 아이디")
    void testGetCouponByCouponId() throws Exception {
        when(couponService.getCouponByCouponId(anyLong())).thenReturn(couponResponseDto);

        mockMvc.perform(get("/coupon/{couponId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(couponResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.memberId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.couponTemplateId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.policyId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.bookId", is(couponResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.categoryId", is(couponResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.typeId", is(couponResponseDto.getTypeId()), Long.class))
                .andExpect(jsonPath("$.name", equalTo(couponResponseDto.getName())))
                .andExpect(jsonPath("$.expirationDate", equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.usedDate", is(couponResponseDto.getUsedDate())))
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 조회 테스트 : 회원 아이디")
    void testGetAllCouponByMember() throws Exception {
        when(couponService.getCouponByMember(any(), anyLong())).thenReturn(page);

        mockMvc.perform(get("/coupon/members/{memberId}", 1L)
                        .param("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .param("size", objectMapper.writeValueAsString(pageable.getPageSize()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(couponResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.content[0].memberId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].couponTemplateId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].policyId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].bookId", is(couponResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.content[0].categoryId", is(couponResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.content[0].typeId", is(couponResponseDto.getTypeId()), Long.class))
                .andExpect(jsonPath("$.content[0].name", equalTo(couponResponseDto.getName())))
                .andExpect(jsonPath("$.content[0].expirationDate", equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate", is(couponResponseDto.getUsedDate())))
                .andDo(print());
    }

    @Test
    @DisplayName("사용된 쿠폰 조회 테스트 : 회원 아이디")
    void testGetUsedCouponByMember() throws Exception {
        ReflectionTestUtils.setField(couponResponseDto, "usedDate", Date.valueOf("2024-03-03"));

        when(couponService.getUsedCouponByMember(any(), anyLong())).thenReturn(page);

        mockMvc.perform(get("/coupon/members/used/{memberId}", 1L)
                        .param("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .param("size", objectMapper.writeValueAsString(pageable.getPageSize()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(couponResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.content[0].memberId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].couponTemplateId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].policyId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].bookId", is(couponResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.content[0].categoryId", is(couponResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.content[0].typeId", is(couponResponseDto.getTypeId()), Long.class))
                .andExpect(jsonPath("$.content[0].name", equalTo(couponResponseDto.getName())))
                .andExpect(jsonPath("$.content[0].expirationDate", equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate", is(couponResponseDto.getUsedDate().toString())))
                .andDo(print());
    }

    @Test
    @DisplayName("미사용인 쿠폰 조회 테스트 : 회원 아이디")
    void testGetUnUsedCouponByMember() throws Exception {
        ReflectionTestUtils.setField(couponResponseDto, "usedDate", null);

        when(couponService.getUnUsedCouponByMember(any(), anyLong())).thenReturn(page);

        mockMvc.perform(get("/coupon/members/unUsed/{memberId}", 1L)
                        .param("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .param("size", objectMapper.writeValueAsString(pageable.getPageSize()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(couponResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.content[0].memberId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].couponTemplateId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].policyId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].bookId", is(couponResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.content[0].categoryId", is(couponResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.content[0].typeId", is(couponResponseDto.getTypeId()), Long.class))
                .andExpect(jsonPath("$.content[0].name", equalTo(couponResponseDto.getName())))
                .andExpect(jsonPath("$.content[0].expirationDate", equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate", is(couponResponseDto.getUsedDate())))
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 수정 테스트")
    void testUpdateCouponUsedDate() throws Exception {
        mockMvc.perform(put("/coupon?couponId=1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("도서에 해당하는 쿠폰 리스트 반환 테스트")
    void testGetCouponForBuyList() throws Exception {
        when(couponService.getCouponForBuyList(any(), any())).thenReturn(couponList);

        mockMvc.perform(get("/coupon/sale")
                        .param("memberId", "1")
                        .param("bookId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(couponResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].memberId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$[0].couponTemplateId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$[0].policyId", is(couponResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$[0].bookId", is(couponResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$[0].categoryId", is(couponResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$[0].typeId", is(couponResponseDto.getTypeId()), Long.class))
                .andExpect(jsonPath("$[0].name", equalTo(couponResponseDto.getName())))
                .andExpect(jsonPath("$[0].expirationDate", equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$[0].usedDate", is(couponResponseDto.getUsedDate())))
                .andDo(print());
    }

}
