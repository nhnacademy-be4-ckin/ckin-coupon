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
import store.ckin.coupon.coupon.model.QCoupon;
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

    QCoupon coupon = QCoupon.coupon;


    @Override
    public Page<GetCouponResponseDto> getCouponList(Pageable pageable) {
        List<GetCouponResponseDto> results = queryFactory
                .select(Projections.fields(GetCouponResponseDto.class,
                        coupon.id,
                        coupon.policyId,
                        coupon.memberId,
                        coupon.bookId,
                        coupon.categoryId,
                        coupon.name,
                        coupon.expirationDate,
                        coupon.issueDate,
                        coupon.usedDate))
                .from(coupon)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long count = queryFactory.selectFrom(coupon).stream().count();
        return new PageImpl<>(results, pageable, count);
    }
}
