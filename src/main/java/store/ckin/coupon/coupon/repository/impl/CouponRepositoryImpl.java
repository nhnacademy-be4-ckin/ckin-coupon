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
import store.ckin.coupon.coupontemplate.model.QCouponTemplateType;
import store.ckin.coupon.policy.model.QCouponPolicy;

import java.util.List;
import java.util.Optional;


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
    QCouponTemplateType couponTemplateType = QCouponTemplateType.couponTemplateType;
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
                .select(coupon.count())
                .where(coupon.memberId.eq(memberId))
                .fetchOne();
        return new PageImpl<>(results, pageable, count);
    }
}
