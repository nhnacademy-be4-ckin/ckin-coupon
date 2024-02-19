package store.ckin.coupon.coupon.repository.impl;

import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.ckin.coupon.coupon.dto.response.GetCouponResponseDto;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupon.model.QCoupon;
import store.ckin.coupon.coupon.repository.CouponRepositoryCustom;
import store.ckin.coupon.coupontemplate.model.QCouponTemplate;

import java.util.List;
import java.util.Optional;


/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 13
 */
public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    QCoupon coupon = QCoupon.coupon;

}
