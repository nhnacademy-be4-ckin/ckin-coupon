package store.ckin.coupon.coupon.adapter;

import java.util.List;

/**
 * CouponAdapter
 *
 * @author : gaeun
 * @version : 2024. 03. 11
 */
public interface CouponAdapter {

    /**
     * 도서 아이디에 해당하는 모든 카테고리를 가져오는 메소드 입니다.
     *
     * @param bookIds 도서 아이디 목록
     * @return 해당하는 카테고리 목록
     */
    List<Long> getCategoryIds(List<Long> bookIds);
}
