package store.ckin.coupon.policy.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;
import store.ckin.coupon.policy.model.CouponPolicy;
import store.ckin.coupon.policy.model.QCouponCode;
import store.ckin.coupon.policy.model.QCouponPolicy;
import store.ckin.coupon.policy.repository.CouponPolicyRepositoryCustom;

import java.util.List;


/**
 * CouponPolicyRepositoryImpl
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
public class CouponPolicyRepositoryImpl extends QuerydslRepositorySupport implements CouponPolicyRepositoryCustom {

    /**
     * Instantiates a new Coupon policy repository.
     */
    public CouponPolicyRepositoryImpl() {
        super(CouponPolicy.class);
    }

    /**
     * The Coupon policy.
     */
    QCouponPolicy couponPolicy = QCouponPolicy.couponPolicy;

    /**
     * {@inheritDoc}
     *
     * @return 쿠폰 정책 목록
     */
    @Override
    public List<GetCouponPolicyResponseDto> getCouponPolicy() {
        List<GetCouponPolicyResponseDto> response = from(couponPolicy)
                .select(Projections.fields(GetCouponPolicyResponseDto.class,
                        couponPolicy.id,
                        couponPolicy.minOrderPrice,
                        couponPolicy.discountPrice,
                        couponPolicy.discountRate,
                        couponPolicy.maxDiscountPrice))
                .where(couponPolicy.state.eq(true))
                .fetch();
        return response;
    }
}
