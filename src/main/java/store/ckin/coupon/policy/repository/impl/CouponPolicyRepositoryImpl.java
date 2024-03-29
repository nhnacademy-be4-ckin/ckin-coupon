package store.ckin.coupon.policy.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.ckin.coupon.policy.dto.response.GetCouponPolicyResponseDto;
import store.ckin.coupon.policy.model.CouponPolicy;
import store.ckin.coupon.policy.model.QCouponPolicy;
import store.ckin.coupon.policy.repository.CouponPolicyRepositoryCustom;


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
