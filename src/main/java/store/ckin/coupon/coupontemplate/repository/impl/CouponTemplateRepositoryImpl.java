package store.ckin.coupon.coupontemplate.repository.impl;

import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.model.QCouponTemplate;
import store.ckin.coupon.coupontemplate.model.QCouponTemplateType;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateRepositoryCustom;
import store.ckin.coupon.policy.model.QCouponPolicy;

import java.util.List;
import java.util.Optional;


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
     *
     * @param pageable 페이지 정보
     * @param typeId   쿠폰 템플릿 타입 ID
     * @return
     */
    @Override
    public Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable, Long typeId) {
        List<GetCouponTemplateResponseDto> results = from(couponTemplate)
                .innerJoin(couponTemplateType)
                .on(couponTemplate.type().eq(couponTemplateType))
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(Projections.fields(GetCouponTemplateResponseDto.class,
                        couponTemplate.id,
                        couponTemplate.policyId,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        couponTemplate.amount))
                .where(couponTemplateType.id.eq(typeId))
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
     *
     * @param couponTemplateId 쿠폰 템플릿 ID
     * @return
     */
    @Override
    public Optional<GetCouponTemplateResponseDto> getCouponTemplate(Long couponTemplateId) {
        GetCouponTemplateResponseDto results = from(couponTemplate)
                .leftJoin(couponPolicy)
                .on(couponTemplate.policyId.eq(couponPolicy.id))
                .select(Projections.fields(GetCouponTemplateResponseDto.class,
                        couponTemplate.id,
                        couponTemplate.policyId,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        couponTemplate.amount))
                .where(couponTemplate.id.eq(couponTemplateId))
                .fetchOne();
        return Optional.of(results);
    }
}
