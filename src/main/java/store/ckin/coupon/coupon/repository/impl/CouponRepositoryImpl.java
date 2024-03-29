package store.ckin.coupon.coupon.repository.impl;

import java.util.Calendar;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.dto.response.QGetCouponResponseDto;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupon.model.QCoupon;
import store.ckin.coupon.coupon.repository.CouponRepositoryCustom;
import store.ckin.coupon.coupontemplate.model.QCouponTemplate;
import store.ckin.coupon.policy.model.QCouponPolicy;


/**
 * CouponRepositoryImpl
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    QCoupon coupon = QCoupon.coupon;
    QCouponTemplate couponTemplate = QCouponTemplate.couponTemplate;
    QCouponPolicy couponPolicy = QCouponPolicy.couponPolicy;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GetCouponResponseDto> getUsedCouponByMember(Pageable pageable, Long memberId) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponPolicy.couponCode().id,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.type().id,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(coupon.memberId.eq(memberId))
                .where(coupon.usedDate.isNotNull())
                .orderBy(coupon.issueDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(coupon.count())
                .where(coupon.memberId.eq(memberId))
                .where(coupon.usedDate.isNotNull())
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GetCouponResponseDto> getUnUsedCouponByMember(Pageable pageable, Long memberId) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponPolicy.couponCode().id,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.type().id,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(coupon.memberId.eq(memberId))
                .where(coupon.usedDate.isNull())
                .where(coupon.expirationDate.after(Calendar.getInstance().getTime()))
                .orderBy(coupon.expirationDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(coupon.count())
                .where(coupon.memberId.eq(memberId))
                .where(coupon.usedDate.isNull())
                .where(coupon.expirationDate.after(Calendar.getInstance().getTime()))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GetCouponResponseDto> getCouponList(Pageable pageable, Long typeId) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponPolicy.couponCode().id,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.type().id,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(couponTemplate.type().id.eq(typeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(coupon.count())
                .where(couponTemplate.type().id.eq(typeId))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GetCouponResponseDto> getAllCouponList(Pageable pageable) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponPolicy.couponCode().id,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.type().id,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(coupon.count())
                .fetchOne();
        return new PageImpl<>(results, pageable, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetCouponResponseDto getCouponByCouponId(Long couponId) {
        return from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponPolicy.couponCode().id,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.type().id,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(coupon.id.eq(couponId))
                .fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GetCouponResponseDto> getCouponByMember(Pageable pageable, Long memberId) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponPolicy.couponCode().id,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.type().id,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(coupon.memberId.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(coupon.count())
                .where(coupon.memberId.eq(memberId))
                .fetchOne();
        return new PageImpl<>(results, pageable, count);
    }

    /**
     * @param memberId       회원 ID
     * @param bookIdList     도서 리스트
     * @param categoryIdList 카테고리 리스트
     * @return
     */
    @Override
    public List<GetCouponResponseDto> getCouponForBuyList(Long memberId, List<Long> bookIdList,
                                                          List<Long> categoryIdList) {
        return from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponPolicy.couponCode().id,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.type().id,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(coupon.memberId.eq(memberId)
                        .and((couponTemplate.bookId.in(bookIdList))
                                .or(couponTemplate.categoryId.in(categoryIdList))))
                .fetch();

    }

    /**
     * @param memberId         회원 ID
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @return
     */
    @Override
    public Boolean isExistCoupon(Long memberId, Long couponTemplateId) {
        long result = from(coupon)
                .where(coupon.memberId.eq(memberId).and(coupon.couponTemplateId.eq(couponTemplateId)))
                .fetchCount();
        return result > 0;
    }
}
