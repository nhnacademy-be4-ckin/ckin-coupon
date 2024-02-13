package store.ckin.coupon.coupon.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupon.repository.CouponRepositoryCustom;
import store.ckin.coupon.policy.model.QCouponCode;
import store.ckin.coupon.policy.model.QCouponPolicy;

import java.util.List;


/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
@Repository
public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {

    @Autowired
    private final JPAQueryFactory queryFactory;
    public CouponRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Coupon.class);
        this.queryFactory = jpaQueryFactory;
    }

    QCouponPolicy couponPolicy = QCouponPolicy.couponPolicy;
    QCouponCode couponCode = QCouponCode.couponCode;


    @Override
    public Page<GetCouponResponseDto> getCouponPolicy(Pageable pageable) {
        List<GetCouponResponseDto> results = queryFactory
                .select(Projections.fields(GetCouponResponseDto.class,
                        couponPolicy.id,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice))
                .from(couponPolicy)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long count = queryFactory.selectFrom(couponPolicy).stream().count();
        return new PageImpl<>(results, pageable, count);
    }
}
