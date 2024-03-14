package store.ckin.coupon.coupon.adapter;

import java.util.List;

/**
 * CouponAdapter
 *
 * @author : gaeun
 * @version : 2024. 03. 11
 */
public interface CouponAdapter {
    List<Long> getCategoryIds(List<Long> bookIds);
}
