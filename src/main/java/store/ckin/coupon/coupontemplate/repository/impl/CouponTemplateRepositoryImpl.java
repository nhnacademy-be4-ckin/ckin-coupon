package store.ckin.coupon.coupontemplate.repository.impl;

import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import store.ckin.coupon.coupontemplate.dto.response.GetCouponTemplateResponseDto;
import store.ckin.coupon.coupontemplate.model.CouponTemplate;
import store.ckin.coupon.coupontemplate.model.QCouponTemplate;
import store.ckin.coupon.coupontemplate.repository.CouponTemplateRepositoryCustom;

import java.util.List;
import java.util.Optional;


/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
public class CouponTemplateRepositoryImpl extends QuerydslRepositorySupport implements CouponTemplateRepositoryCustom {

    public CouponTemplateRepositoryImpl() {
        super(CouponTemplate.class);
    }

    QCouponTemplate couponTemplate = QCouponTemplate.couponTemplate;


    @Override
    public Page<GetCouponTemplateResponseDto> getCouponTemplateList(Pageable pageable) {
        List<GetCouponTemplateResponseDto> results = from(couponTemplate)
                .select(Projections.fields(GetCouponTemplateResponseDto.class,
                        couponTemplate.id,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        couponTemplate.amount))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long count = from(couponTemplate).stream().count();
        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public Optional<GetCouponTemplateResponseDto> getCouponTemplate(Long couponTemplateId) {
        GetCouponTemplateResponseDto results = from(couponTemplate)
                .select(Projections.fields(GetCouponTemplateResponseDto.class,
                        couponTemplate.id,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        couponTemplate.amount))
                .where(couponTemplate.id.eq(couponTemplateId))
                .fetchOne();
        return Optional.of(results);
    }

    @Override
    public Page<GetCouponTemplateResponseDto> getBirthCouponTemplate(Pageable pageable) {
        List<GetCouponTemplateResponseDto> results = from(couponTemplate)
                .select(Projections.fields(GetCouponTemplateResponseDto.class,
                        couponTemplate.id,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        couponTemplate.amount))
                .where(couponTemplate.bookId.isNull().and(couponTemplate.categoryId.isNull()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(couponTemplate)
                .where(couponTemplate.bookId.isNull().and(couponTemplate.categoryId.isNotNull()))
                .fetchCount();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public Page<GetCouponTemplateResponseDto> getBookCouponTemplate(Pageable pageable) {
        List<GetCouponTemplateResponseDto> results = from(couponTemplate)
                .select(Projections.fields(GetCouponTemplateResponseDto.class,
                        couponTemplate.id,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        couponTemplate.amount))
                .where(couponTemplate.bookId.isNotNull().and(couponTemplate.categoryId.isNull()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(couponTemplate)
                .select(couponTemplate.count())
                .where(couponTemplate.bookId.isNotNull().and(couponTemplate.categoryId.isNull()))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public Page<GetCouponTemplateResponseDto> getCategoryTemplate(Pageable pageable) {
        List<GetCouponTemplateResponseDto> results = from(couponTemplate)
                .select(Projections.fields(GetCouponTemplateResponseDto.class,
                        couponTemplate.id,
                        couponTemplate.policyId,
                        couponTemplate.bookId,
                        couponTemplate.categoryId,
                        couponTemplate.name,
                        couponTemplate.amount))
                .where(couponTemplate.bookId.isNull().and(couponTemplate.categoryId.isNotNull()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(couponTemplate)
                .where(couponTemplate.bookId.isNull().and(couponTemplate.categoryId.isNotNull()))
                .fetchCount();

        return new PageImpl<>(results, pageable, count);
    }
}
