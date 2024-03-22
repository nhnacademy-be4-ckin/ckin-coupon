package store.ckin.coupon.coupon.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.util.List;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.service.CouponService;

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
        ReflectionTestUtils.setField(couponResponseDto, "couponCodeId", 1L);
        ReflectionTestUtils.setField(couponResponseDto, "minOrderPrice", 10000);
        ReflectionTestUtils.setField(couponResponseDto, "discountPrice", null);
        ReflectionTestUtils.setField(couponResponseDto, "discountRate", 10);
        ReflectionTestUtils.setField(couponResponseDto, "maxDiscountPrice", 20000);
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
                .andDo(document("coupon/createCoupon/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("지정할 페이지"),
                                parameterWithName("size").description("한 페이지 당 표시할 개수")
                        ),
                        requestFields(
                                fieldWithPath("memberId").description("쿠폰을 지급받을 회원 아이디"),
                                fieldWithPath("couponTemplateId").description("쿠폰을 찍어낼 쿠폰 템플릿 아이디"),
                                fieldWithPath("expirationDate").description("쿠폰 만료일"),
                                fieldWithPath("issueDate").description("쿠폰 발급일"),
                                fieldWithPath("usedDate").description("쿠폰 사용일")
                        )));
    }

    @Test
    @DisplayName("Welcome 쿠폰 생성 테스트")
    void testCreateWelcomeCoupon() throws Exception {

        mockMvc.perform(post("/coupon/welcome")
                        .param("memberId", objectMapper.writeValueAsString(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("coupon/createWelcomeCoupon/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("memberId").description("생일 쿠폰을 지급할 회원 아이디")
                        )));
    }

    @Test
    @DisplayName("쿠폰 발급 기록을 확인하고 등록 테스트")
    void testCreateCouponByIds() throws Exception {
        when(couponService.createCouponByIds(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/members/{memberId}/{couponTemplateId}", 1L, 1L))
                .andExpect(status().isCreated())
                .andDo(document("coupon/createCouponByIds/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("쿠폰을 발급받는 회원의 아이디"),
                                parameterWithName("couponTemplateId").description("지급할 쿠폰의 템플릿 번호")
                        )));
//                        responseFields(
//                                fieldWithPath(".").description("쿠폰이 성공적으로 발급되었는지 여부")
//                        )));
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
                .andExpect(jsonPath("$.content[0].couponCodeId", is(couponResponseDto.getCouponCodeId()), Long.class))
                .andExpect(jsonPath("$.content[0].minOrderPrice", is(couponResponseDto.getMinOrderPrice()), Integer.class))
                .andExpect(jsonPath("$.content[0].discountPrice", is(couponResponseDto.getDiscountPrice()), Integer.class))
                .andExpect(jsonPath("$.content[0].discountRate", is(couponResponseDto.getDiscountRate()), Integer.class))
                .andExpect(jsonPath("$.content[0].maxDiscountPrice", is(couponResponseDto.getMaxDiscountPrice()), Integer.class))
                .andExpect(jsonPath("$.content[0].bookId", is(couponResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.content[0].categoryId", is(couponResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.content[0].typeId", is(couponResponseDto.getTypeId()), Long.class))
                .andExpect(jsonPath("$.content[0].name", equalTo(couponResponseDto.getName())))
                .andExpect(jsonPath("$.content[0].expirationDate", equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate", is(couponResponseDto.getUsedDate())))
                .andDo(document("coupon/getAllCouponList/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("지정할 페이지"),
                                parameterWithName("size").description("한 페이지 당 표시할 개수"),
                                parameterWithName("typeId").description("쿠폰 목록을 조회하기 위한 타입 아이디")
                        ),
                        responseFields(
                                fieldWithPath("content.[].id").description("쿠폰 아이디"),
                                fieldWithPath("content.[].memberId").description("쿠폰을 지급받은 회원 아이디"),
                                fieldWithPath("content.[].couponTemplateId").description("쿠폰 템플릿 아이디"),
                                fieldWithPath("content.[].policyId").description("쿠폰 정책 아이디"),
                                fieldWithPath("content.[].couponCodeId").description("쿠폰 정책 코드 아이디"),
                                fieldWithPath("content.[].minOrderPrice").description("최소 주문 금액"),
                                fieldWithPath("content.[].discountPrice").description("할인 금액"),
                                fieldWithPath("content.[].discountRate").description("할인율"),
                                fieldWithPath("content.[].maxDiscountPrice").description("최대 할인 금액"),
                                fieldWithPath("content.[].bookId").description("쿠폰을 적용 가능한 도서 아이디"),
                                fieldWithPath("content.[].categoryId").description("쿠폰을 적용 가능한 카테고리 아이디"),
                                fieldWithPath("content.[].typeId").description("쿠폰 타입 아이디"),
                                fieldWithPath("content.[].name").description("쿠폰 이름"),
                                fieldWithPath("content.[].expirationDate").description("쿠폰 만료일"),
                                fieldWithPath("content.[].issueDate").description("쿠폰 발급일"),
                                fieldWithPath("content.[].usedDate").description("쿠폰 사용일"),
                                fieldWithPath("totalPages").description("총 페이지 수"),
                                fieldWithPath("number").description("현재 페이지 번호"),
                                fieldWithPath("pageable").description("페이지 정보"),
                                fieldWithPath("last").description("마지막 페이지인지 여부"),
                                fieldWithPath("totalElements").description("총 요소 개수"),
                                fieldWithPath("empty").description("요소가 비어있는지 여부"),
                                fieldWithPath("sort.sorted").description("정렬된 요소가 있는지 여부"),
                                fieldWithPath("sort.unsorted").description("정렬되지 않은 요소가 있는지 여부"),
                                fieldWithPath("size").description("총 요소 개수"),
                                fieldWithPath("first").description("페이지의 첫 번째 요소"),
                                fieldWithPath("numberOfElements").description("현재 페이지에 있는 요소의 수"),
                                fieldWithPath("sort.empty").description(" 정렬된 요소가 비어있는지 여부")
                        )));
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
                .andExpect(jsonPath("$.content[0].expirationDate",
                        equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate", is(couponResponseDto.getUsedDate())))
                .andDo(document("coupon/getAllCouponList/miss-parameter-failed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("쿠폰 조회 테스트 : 쿠폰 아이디")
    void testGetCouponByCouponId() throws Exception {
        when(couponService.getCouponByCouponId(anyLong())).thenReturn(couponResponseDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/{couponId}", 1L)
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
                .andDo(document("coupon/getCouponByCouponId/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("couponId").description("쿠폰 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").description("쿠폰 아이디"),
                                fieldWithPath("memberId").description("쿠폰을 지급받은 회원 아이디"),
                                fieldWithPath("couponTemplateId").description("쿠폰 템플릿 아이디"),
                                fieldWithPath("policyId").description("쿠폰 정책 아이디"),
                                fieldWithPath("couponCodeId").description("쿠폰 정책 코드 아이디"),
                                fieldWithPath("minOrderPrice").description("최소 주문 금액"),
                                fieldWithPath("discountPrice").description("할인 금액"),
                                fieldWithPath("discountRate").description("할인율"),
                                fieldWithPath("maxDiscountPrice").description("최대 할인 금액"),
                                fieldWithPath("bookId").description("쿠폰을 적용 가능한 도서 아이디"),
                                fieldWithPath("categoryId").description("쿠폰을 적용 가능한 카테고리 아이디"),
                                fieldWithPath("typeId").description("쿠폰 타입 아이디"),
                                fieldWithPath("name").description("쿠폰 이름"),
                                fieldWithPath("expirationDate").description("쿠폰 만료일"),
                                fieldWithPath("issueDate").description("쿠폰 발급일"),
                                fieldWithPath("usedDate").description("쿠폰 사용일")
                        )));
    }

    @Test
    @DisplayName("쿠폰 조회 테스트 : 회원 아이디")
    void testGetAllCouponByMember() throws Exception {
        when(couponService.getCouponByMember(any(), anyLong())).thenReturn(page);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/members/{memberId}", 1L)
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
                .andExpect(jsonPath("$.content[0].expirationDate",
                        equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate", is(couponResponseDto.getUsedDate())))
                .andDo(document("coupon/getAllCouponByMember/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("지정할 페이지"),
                                parameterWithName("size").description("한 페이지 당 표시할 개수")
                        ),
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("content.[].id").description("쿠폰 아이디"),
                                fieldWithPath("content.[].memberId").description("쿠폰을 지급받은 회원 아이디"),
                                fieldWithPath("content.[].couponTemplateId").description("쿠폰 템플릿 아이디"),
                                fieldWithPath("content.[].policyId").description("쿠폰 정책 아이디"),
                                fieldWithPath("content.[].couponCodeId").description("쿠폰 정책 코드 아이디"),
                                fieldWithPath("content.[].minOrderPrice").description("최소 주문 금액"),
                                fieldWithPath("content.[].discountPrice").description("할인 금액"),
                                fieldWithPath("content.[].discountRate").description("할인율"),
                                fieldWithPath("content.[].maxDiscountPrice").description("최대 할인 금액"),
                                fieldWithPath("content.[].bookId").description("쿠폰을 적용 가능한 도서 아이디"),
                                fieldWithPath("content.[].categoryId").description("쿠폰을 적용 가능한 카테고리 아이디"),
                                fieldWithPath("content.[].typeId").description("쿠폰 타입 아이디"),
                                fieldWithPath("content.[].name").description("쿠폰 이름"),
                                fieldWithPath("content.[].expirationDate").description("쿠폰 만료일"),
                                fieldWithPath("content.[].issueDate").description("쿠폰 발급일"),
                                fieldWithPath("content.[].usedDate").description("쿠폰 사용일"),
                                fieldWithPath("pageable").description("페이지 정보"),
                                fieldWithPath("last").description("마지막 페이지인지 여부"),
                                fieldWithPath("totalElements").description("총 요소 개수"),
                                fieldWithPath("empty").description("요소가 비어있는지 여부"),
                                fieldWithPath("sort.sorted").description("정렬된 요소가 있는지 여부"),
                                fieldWithPath("sort.unsorted").description("정렬되지 않은 요소가 있는지 여부"),
                                fieldWithPath("size").description("총 요소 개수"),
                                fieldWithPath("first").description("페이지의 첫 번째 요소"),
                                fieldWithPath("numberOfElements").description("현재 페이지에 있는 요소의 수"),
                                fieldWithPath("sort.empty").description("정렬된 요소가 비어있는지 여부"),
                                fieldWithPath("totalPages").description("총 페이지 수"),
                                fieldWithPath("number").description("페이지의 번호")
                        )));
    }

    @Test
    @DisplayName("사용된 쿠폰 조회 테스트 : 회원 아이디")
    void testGetUsedCouponByMember() throws Exception {
        ReflectionTestUtils.setField(couponResponseDto, "usedDate", Date.valueOf("2024-03-03"));

        when(couponService.getUsedCouponByMember(any(), anyLong())).thenReturn(page);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/members/used/{memberId}", 1L)
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
                .andExpect(jsonPath("$.content[0].expirationDate",
                        equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate", is(couponResponseDto.getUsedDate().toString())))
                .andDo(document("coupon/getUsedCouponByMember/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("지정할 페이지"),
                                parameterWithName("size").description("한 페이지 당 표시할 개수")
                        ),
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("content.[].id").description("쿠폰 아이디"),
                                fieldWithPath("content.[].memberId").description("쿠폰을 지급받은 회원 아이디"),
                                fieldWithPath("content.[].couponTemplateId").description("쿠폰 템플릿 아이디"),
                                fieldWithPath("content.[].policyId").description("쿠폰 정책 아이디"),
                                fieldWithPath("content.[].couponCodeId").description("쿠폰 정책 코드 아이디"),
                                fieldWithPath("content.[].minOrderPrice").description("최소 주문 금액"),
                                fieldWithPath("content.[].discountPrice").description("할인 금액"),
                                fieldWithPath("content.[].discountRate").description("할인율"),
                                fieldWithPath("content.[].maxDiscountPrice").description("최대 할인 금액"),
                                fieldWithPath("content.[].bookId").description("쿠폰을 적용 가능한 도서 아이디"),
                                fieldWithPath("content.[].categoryId").description("쿠폰을 적용 가능한 카테고리 아이디"),
                                fieldWithPath("content.[].typeId").description("쿠폰 타입 아이디"),
                                fieldWithPath("content.[].name").description("쿠폰 이름"),
                                fieldWithPath("content.[].expirationDate").description("쿠폰 만료일"),
                                fieldWithPath("content.[].issueDate").description("쿠폰 발급일"),
                                fieldWithPath("content.[].usedDate").description("쿠폰 사용일"),
                                fieldWithPath("pageable").description("페이지 정보"),
                                fieldWithPath("last").description("마지막 페이지인지 여부"),
                                fieldWithPath("totalElements").description("총 요소 개수"),
                                fieldWithPath("empty").description("요소가 비어있는지 여부"),
                                fieldWithPath("sort.sorted").description("정렬된 요소가 있는지 여부"),
                                fieldWithPath("sort.unsorted").description("정렬되지 않은 요소가 있는지 여부"),
                                fieldWithPath("size").description("총 요소 개수"),
                                fieldWithPath("first").description("페이지의 첫 번째 요소"),
                                fieldWithPath("numberOfElements").description("현재 페이지에 있는 요소의 수"),
                                fieldWithPath("sort.empty").description("정렬된 요소가 비어있는지 여부"),
                                fieldWithPath("totalPages").description("총 페이지 수"),
                                fieldWithPath("number").description("페이지의 번호")
                        )));
    }

    @Test
    @DisplayName("미사용인 쿠폰 조회 테스트 : 회원 아이디")
    void testGetUnUsedCouponByMember() throws Exception {
        ReflectionTestUtils.setField(couponResponseDto, "usedDate", null);

        when(couponService.getUnUsedCouponByMember(any(), anyLong())).thenReturn(page);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/members/unUsed/{memberId}", 1L)
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
                .andExpect(jsonPath("$.content[0].expirationDate",
                        equalTo(couponResponseDto.getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].issueDate", is(couponResponseDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.content[0].usedDate", is(couponResponseDto.getUsedDate())))
                .andDo(document("coupon/getUnUsedCouponByMember/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("지정할 페이지"),
                                parameterWithName("size").description("한 페이지 당 표시할 개수")
                        ),
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("content.[].id").description("쿠폰 아이디"),
                                fieldWithPath("content.[].memberId").description("쿠폰을 지급받은 회원 아이디"),
                                fieldWithPath("content.[].couponTemplateId").description("쿠폰 템플릿 아이디"),
                                fieldWithPath("content.[].policyId").description("쿠폰 정책 아이디"),
                                fieldWithPath("content.[].couponCodeId").description("쿠폰 정책 코드 아이디"),
                                fieldWithPath("content.[].minOrderPrice").description("최소 주문 금액"),
                                fieldWithPath("content.[].discountPrice").description("할인 금액"),
                                fieldWithPath("content.[].discountRate").description("할인율"),
                                fieldWithPath("content.[].maxDiscountPrice").description("최대 할인 금액"),
                                fieldWithPath("content.[].bookId").description("쿠폰을 적용 가능한 도서 아이디"),
                                fieldWithPath("content.[].categoryId").description("쿠폰을 적용 가능한 카테고리 아이디"),
                                fieldWithPath("content.[].typeId").description("쿠폰 타입 아이디"),
                                fieldWithPath("content.[].name").description("쿠폰 이름"),
                                fieldWithPath("content.[].expirationDate").description("쿠폰 만료일"),
                                fieldWithPath("content.[].issueDate").description("쿠폰 발급일"),
                                fieldWithPath("content.[].usedDate").description("쿠폰 사용일"),
                                fieldWithPath("pageable").description("페이지 정보"),
                                fieldWithPath("last").description("마지막 페이지인지 여부"),
                                fieldWithPath("totalElements").description("총 요소 개수"),
                                fieldWithPath("empty").description("요소가 비어있는지 여부"),
                                fieldWithPath("sort.sorted").description("정렬된 요소가 있는지 여부"),
                                fieldWithPath("sort.unsorted").description("정렬되지 않은 요소가 있는지 여부"),
                                fieldWithPath("size").description("총 요소 개수"),
                                fieldWithPath("first").description("페이지의 첫 번째 요소"),
                                fieldWithPath("numberOfElements").description("현재 페이지에 있는 요소의 수"),
                                fieldWithPath("sort.empty").description("정렬된 요소가 비어있는지 여부"),
                                fieldWithPath("totalPages").description("총 페이지 수"),
                                fieldWithPath("number").description("페이지의 번호")
                        )));
    }

    @Test
    @DisplayName("쿠폰 수정 테스트")
    void testUpdateCouponUsedDate() throws Exception {
        mockMvc.perform(put("/coupon?couponId=1"))
                .andExpect(status().isOk())
                .andDo(document("coupon/updateCouponUsedDate/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("couponId").description("사용한 쿠폰 목록")
                        )));
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
                .andDo(document("coupon/getUnUsedCouponByMember/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("memberId").description("회원 아이디"),
                                parameterWithName("bookId").description("도서 아이디 목록")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("쿠폰 아이디"),
                                fieldWithPath("[].memberId").description("쿠폰을 지급받은 회원 아이디"),
                                fieldWithPath("[].couponTemplateId").description("쿠폰 템플릿 아이디"),
                                fieldWithPath("[].policyId").description("쿠폰 정책 아이디"),
                                fieldWithPath("[].couponCodeId").description("쿠폰 정책 코드 아이디"),
                                fieldWithPath("[].minOrderPrice").description("최소 주문 금액"),
                                fieldWithPath("[].discountPrice").description("할인 금액"),
                                fieldWithPath("[].discountRate").description("할인율"),
                                fieldWithPath("[].maxDiscountPrice").description("최대 할인 금액"),
                                fieldWithPath("[].bookId").description("쿠폰을 적용 가능한 도서 아이디"),
                                fieldWithPath("[].categoryId").description("쿠폰을 적용 가능한 카테고리 아이디"),
                                fieldWithPath("[].typeId").description("쿠폰 타입 아이디"),
                                fieldWithPath("[].name").description("쿠폰 이름"),
                                fieldWithPath("[].expirationDate").description("쿠폰 만료일"),
                                fieldWithPath("[].issueDate").description("쿠폰 발급일"),
                                fieldWithPath("[].usedDate").description("쿠폰 사용일")
                        )));

    }

}
