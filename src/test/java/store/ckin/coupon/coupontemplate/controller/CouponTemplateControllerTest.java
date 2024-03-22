package store.ckin.coupon.coupontemplate.controller;

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
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import store.ckin.coupon.coupontemplate.dto.request.CreateCouponTemplateRequestDto;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.service.CouponTemplateService;

/**
 * 쿠폰 템플릿 컨트롤러 테스트 입니다.
 *
 * @author : gaeun
 * @version : 2024. 02. 16
 */

@AutoConfigureRestDocs
@WebMvcTest(CouponTemplateController.class)
class CouponTemplateControllerTest {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @MockBean
    CouponTemplateService couponTemplateService;

    CreateCouponTemplateRequestDto couponTemplateRequestDto;
    GetCouponTemplateResponseDto couponTemplateResponseDto;
    Long typeId;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        typeId = 1L;
        couponTemplateRequestDto = new CreateCouponTemplateRequestDto();
        couponTemplateResponseDto =
                new GetCouponTemplateResponseDto(1L, 1L, 3000, 3000, 10000, null, 1L, null, "사람은 무엇으로 사는가  - 도서 쿠폰",
                        100L, 2L, 30,
                        Date.valueOf("2023-03-04"), true);
    }

    @Test
    @DisplayName("쿠폰 템플릿 목록 조회 테스트")
    void getAllCouponTemplateTest() throws Exception {
        PageRequest pageable = PageRequest.of(0, 5);
        PageImpl<GetCouponTemplateResponseDto> page = new PageImpl<>(List.of(couponTemplateResponseDto), pageable, 1);

        when(couponTemplateService.getCouponTemplateList(any(), anyLong()))
                .thenReturn(page);

        mockMvc.perform(get("/coupon/couponTemplate")
                        .param("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .param("size", objectMapper.writeValueAsString(pageable.getPageSize()))
                        .param("type", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(couponTemplateResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.content[0].policyId", is(couponTemplateResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.content[0].bookId", is(couponTemplateResponseDto.getBookId()), Long.class))
                .andExpect(
                        jsonPath("$.content[0].categoryId", is(couponTemplateResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.content[0].name", equalTo(couponTemplateResponseDto.getName())))
                .andExpect(jsonPath("$.content[0].amount", is(couponTemplateResponseDto.getAmount()), Long.class))
                .andDo(document("couponTemplate/getAllCouponTemplate/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("지정할 페이지"),
                                parameterWithName("size").description("한 페이지 당 표시할 개수"),
                                parameterWithName("type").description("템플릿을 조회하기 위한 타입 아이디")
                        ),
                        responseFields(
                                fieldWithPath("content.[].state").description("쿠폰 템플릿 상태"),
                                fieldWithPath("content.[].id").description("쿠폰 템플릿 아이디"),
                                fieldWithPath("content.[].policyId").description("쿠폰 템플릿에 적용된 정책 아이디"),
                                fieldWithPath("content.[].minOrderPrice").description("최소 주문 금액"),
                                fieldWithPath("content.[].discountPrice").description("할인 금액"),
                                fieldWithPath("content.[].discountRate").description("할인율"),
                                fieldWithPath("content.[].maxDiscountPrice").description("최대 할인 금액"),
                                fieldWithPath("content.[].bookId").description("쿠폰을 적용 가능한 도서 아이디"),
                                fieldWithPath("content.[].categoryId").description("쿠폰을 적용 가능한 카테고리 아이디"),
                                fieldWithPath("content.[].name").description("쿠폰 템플릿 이름"),
                                fieldWithPath("content.[].amount").description("쿠폰 템플릿 수량"),
                                fieldWithPath("content.[].typeId").description("쿠폰 템플릿 타입 아이디"),
                                fieldWithPath("content.[].duration").description("쿠폰 템플릿 사용기한"),
                                fieldWithPath("content.[].expirationDate").description("쿠폰 템플릿 만료일"),
                                fieldWithPath("pageable").description("페이지 관련 정보"),
                                fieldWithPath("number").description("현재 페이지 번호"),
                                fieldWithPath("pageable.offset").description("페이지의 시작 오프셋"),
                                fieldWithPath("pageable.pageNumber").description("현재 페이지가 마지막 페이지인지 여부"),
                                fieldWithPath("pageable.pageSize").description("페이지당 요소 개수"),
                                fieldWithPath("pageable.paged").description("페이징된 결과인지 여부"),
                                fieldWithPath("pageable.unpaged").description("페이징되지 않은 결과인지 여부"),
                                fieldWithPath("pageable.sort.empty").description("정렬된 요소가 비어있는지 여부"),
                                fieldWithPath("pageable.sort.sorted").description("정렬된 요소가 있는지 여부"),
                                fieldWithPath("pageable.sort.unsorted").description("정렬되지 않은 요소가 있는지 여부"),
                                fieldWithPath("last").description("현재 페이지가 마지막 페이지인지 여부"),
                                fieldWithPath("totalElements").description("전체 요소 개수"),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("sort.empty").description("정렬된 요소가 비어있는지 여부"),
                                fieldWithPath("sort.unsorted").description("정렬되지 않은 요소가 있는지 여부"),
                                fieldWithPath("sort.sorted").description("정렬된 요소가 있는지 여부"),
                                fieldWithPath("first").description("현재 페이지의 첫 번째 요소 여부"),
                                fieldWithPath("size").description("현재 페이지의 요소 개수"),
                                fieldWithPath("numberOfElements").description("현재 페이지의 요소 개수"),
                                fieldWithPath("empty").description("현재 페이지의 요소가 비어있는지 여부")
                        )));
    }

    @Test
    @DisplayName("쿠폰 템플릿 단일 조회 테스트")
    void getCouponTemplateByIdTest() throws Exception {
        when(couponTemplateService.getCouponTemplate(anyLong())).thenReturn(couponTemplateResponseDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/couponTemplate/{couponTemplateId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(couponTemplateResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.policyId", is(couponTemplateResponseDto.getPolicyId()), Long.class))
                .andExpect(jsonPath("$.bookId", is(couponTemplateResponseDto.getBookId()), Long.class))
                .andExpect(jsonPath("$.categoryId", is(couponTemplateResponseDto.getCategoryId()), Long.class))
                .andExpect(jsonPath("$.name", equalTo(couponTemplateResponseDto.getName())))
                .andExpect(jsonPath("$.amount", is(couponTemplateResponseDto.getAmount()), Long.class))
                .andDo(document("couponTemplate/getCouponTemplateById/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("couponTemplateId").description("조회할 쿠폰 템플릿의 아이디")
                        ),
                        responseFields(
                                fieldWithPath("state").description("쿠폰 템플릿 상태"),
                                fieldWithPath("id").description("쿠폰 템플릿 아이디"),
                                fieldWithPath("policyId").description("쿠폰 템플릿에 적용된 정책 아이디"),
                                fieldWithPath("minOrderPrice").description("최소 주문 금액"),
                                fieldWithPath("discountPrice").description("할인 금액"),
                                fieldWithPath("discountRate").description("할인율"),
                                fieldWithPath("maxDiscountPrice").description("최대 할인 금액"),
                                fieldWithPath("bookId").description("쿠폰을 적용 가능한 도서 아이디"),
                                fieldWithPath("categoryId").description("쿠폰을 적용 가능한 카테고리 아이디"),
                                fieldWithPath("name").description("쿠폰 템플릿 이름"),
                                fieldWithPath("amount").description("쿠폰 템플릿 수량"),
                                fieldWithPath("typeId").description("쿠폰 템플릿 타입 아이디"),
                                fieldWithPath("duration").description("쿠폰 템플릿 사용기한"),
                                fieldWithPath("expirationDate").description("쿠폰 템플릿 만료일")
                        )));
    }

    @Test
    @DisplayName("쿠폰 템플릿 등록 테스트: 성공")
    void createCouponTemplateTest() throws Exception {
        ReflectionTestUtils.setField(couponTemplateRequestDto, "policyId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "categoryId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "typeId", 1L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "name", "해리포터 전집");
        ReflectionTestUtils.setField(couponTemplateRequestDto, "amount", 100L);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "duration", 30);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "expirationDate", null);
        ReflectionTestUtils.setField(couponTemplateRequestDto, "state", true);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/couponTemplate")
                        .content(objectMapper.writeValueAsString(couponTemplateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("couponTemplate/createCouponTemplate/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("state").description("쿠폰 템플릿 상태"),
                                fieldWithPath("policyId").description("쿠폰 템플릿에 적용된 정책 아이디"),
                                fieldWithPath("bookId").description("쿠폰을 적용 가능한 도서 아이디"),
                                fieldWithPath("categoryId").description("쿠폰을 적용 가능한 카테고리 아이디"),
                                fieldWithPath("name").description("쿠폰 템플릿 이름"),
                                fieldWithPath("amount").description("쿠폰 템플릿 수량"),
                                fieldWithPath("typeId").description("쿠폰 템플릿 타입 아이디"),
                                fieldWithPath("duration").description("쿠폰 템플릿 사용기한"),
                                fieldWithPath("expirationDate").description("쿠폰 템플릿 만료일")
                        )));
    }

    @Test
    @DisplayName("쿠폰 템플릿 등록 테스트: 실패")
    void createCouponTemplateTest_X() throws Exception {

        mockMvc.perform(post("/coupon/couponTemplate")
                        .content(objectMapper.writeValueAsString(couponTemplateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(document("couponTemplate/createCouponTemplate/miss-parameter-failed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("쿠폰 템플릿 삭제 테스트")
    void deleteCouponTemplateTest() throws Exception {

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/coupon/couponTemplate/{couponTemplateId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("couponTemplate/deleteCouponTemplate/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("couponTemplateId").description("삭제할 쿠폰 템플릿의 아이디")
                        )));
    }

    @Test
    @DisplayName("쿠폰 템플릿 사용여부 변경 테스트")
    void testUpdateTemplateStatus() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.put("/coupon/couponTemplate/{templateId}", 1L)
                        .param("state", "true")
                ).andExpect(status().isOk())
                .andDo(document("couponTemplate/updateTemplateStatus/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("templateId").description("수정할 쿠폰 템플릿의 아이디")
                        ),
                        requestParameters(
                                parameterWithName("state").description("수정할 쿠폰 템플릿의 상태")
                        )));
    }


}