package store.ckin.coupon.coupontemplate.repository.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.dto.response.QGetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.model.QCouponTemplate;
import store.ckin.coupon.coupontemplate.model.QCouponTemplateType;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateRepositoryCustom;
import store.ckin.coupon.policy.model.QCouponPolicy;


/**
 * CouponTemplateRepositoryImpl
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
public class CouponTemplateRepositoryImpl extends QuerydslRepositorySupport implements CouponTemplateRepositoryCustom {

    public CouponTemplateRepositoryImpl() {
        super(CouponTemplate.class);
    }

    QCouponTemplate couponTemplate = QCouponTemplate.couponTemplate;
    QCouponTemplateType couponTemplateType = QCouponTemplateType.couponTemplateType;
    QCouponPolicy couponPolicy = QCouponPolicy.couponPolicy;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable, Long typeId) {
        List<GetCouponTemplateResponseDto> results = from(couponTemplate)
                .innerJoin(couponTemplateType)
                .on(couponTemplate.type().eq(couponTemplateType))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(new QGetCouponTemplateResponseDto(
                        couponTemplate.id,
                        couponTemplate.policyId,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        couponTemplate.amount,
                        couponTemplate.type().id,
                        couponTemplate.duration,
                        couponTemplate.expirationDate,
                        couponTemplate.state
                ))
                .where(couponTemplateType.id.eq(typeId))
                .orderBy(couponTemplate.expirationDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(couponTemplate)
                .innerJoin(couponTemplateType)
                .on(couponTemplate.type().eq(couponTemplateType))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(couponTemplate.count())
                .where(couponTemplateType.id.eq(typeId))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GetCouponTemplateResponseDto> getCouponTemplate(Long couponTemplateId) {
        GetCouponTemplateResponseDto results = from(couponTemplate)
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(new QGetCouponTemplateResponseDto(
                        couponTemplate.id,
                        couponTemplate.policyId,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        couponTemplate.amount,
                        couponTemplate.type().id,
                        couponTemplate.duration,
                        couponTemplate.expirationDate,
                        couponTemplate.state
                ))
                .where(couponTemplate.id.eq(couponTemplateId))
                .fetchOne();
        return Optional.of(results);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetCouponTemplateResponseDto getCouponTemplateByTypeId(Long typeId) {
        return from(couponTemplate)
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(new QGetCouponTemplateResponseDto(
                        couponTemplate.id,
                        couponTemplate.policyId,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        couponTemplate.amount,
                        couponTemplate.type().id,
                        couponTemplate.duration,
                        couponTemplate.expirationDate,
                        couponTemplate.state
                ))
                .where(couponTemplate.type().id.eq(typeId))
                .where(couponTemplate.state.eq(true))
                .fetchOne();
    }
}
