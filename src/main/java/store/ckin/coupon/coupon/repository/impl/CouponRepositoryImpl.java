package store.ckin.coupon.coupon.repository.impl;

import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.dto.response.QGetCouponResponseDto;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupon.model.QCoupon;
import store.ckin.coupon.coupon.repository.CouponRepositoryCustom;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.model.QCouponTemplate;

import java.util.List;
import java.util.Optional;


/**
 * CouponRepositoryImpl
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {

    /**
     * Instantiates a new Coupon repository.
     */
    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    /**
     * The Coupon.
     */
    QCoupon coupon = QCoupon.coupon;
    /**
     * The Coupon template.
     */
    QCouponTemplate couponTemplate = QCouponTemplate.couponTemplate;

    @Override
    public Page<GetCouponResponseDto> getUsedCouponByMember(Pageable pageable, Long memberId) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(coupon.memberId.eq(memberId))
                .where(coupon.usedDate.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(coupon)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(coupon.count())
                .where(coupon.memberId.eq(memberId))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public Page<GetCouponResponseDto> getUnUsedCouponByMember(Pageable pageable, Long memberId) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(coupon.memberId.eq(memberId))
                .where(coupon.usedDate.isNotNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(coupon)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(coupon.count())
                .where(coupon.memberId.eq(memberId))
                .where(coupon.usedDate.isNotNull())
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public Page<GetCouponResponseDto> getBirthCouponAll(Pageable pageable) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(couponTemplate.bookId.isNull().and(couponTemplate.categoryId.isNull()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(coupon.count())
                .where(couponTemplate.bookId.isNull().and(couponTemplate.categoryId.isNull()))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public Page<GetCouponResponseDto> getBookCouponAll(Pageable pageable) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(couponTemplate.bookId.isNotNull().and(couponTemplate.categoryId.isNull()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(coupon.count())
                .where(couponTemplate.bookId.isNotNull().and(couponTemplate.categoryId.isNull()))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public Page<GetCouponResponseDto> getCategoryCouponAll(Pageable pageable) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .where(couponTemplate.bookId.isNull().and(couponTemplate.categoryId.isNotNull()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(coupon.count())
                .where(couponTemplate.bookId.isNull().and(couponTemplate.categoryId.isNotNull()))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public Page<GetCouponResponseDto> getAllCouponList(Pageable pageable) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
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
                .select(coupon.count())
                .fetchOne();
        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public GetCouponResponseDto getCouponByCouponId(Long couponId) {
        return from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate
                ))
                .where(coupon.id.eq(couponId))
                .fetchOne();
    }

    @Override
    public Page<GetCouponResponseDto> getCouponByMember(Pageable pageable, Long memberId) {
        List<GetCouponResponseDto> results = from(coupon)
                .innerJoin(couponTemplate)
                .on(coupon.couponTemplateId.eq(couponTemplate.id))
                .select(new QGetCouponResponseDto(
                        coupon.id,
                        coupon.memberId,
                        coupon.couponTemplateId,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
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
                .select(coupon.count())
                .where(coupon.memberId.eq(memberId))
                .fetchOne();
        return new PageImpl<>(results, pageable, count);
    }
}
