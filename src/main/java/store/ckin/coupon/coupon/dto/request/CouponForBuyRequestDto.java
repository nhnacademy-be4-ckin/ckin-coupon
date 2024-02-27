package store.ckin.coupon.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 27
 */
@Getter
@NoArgsConstructor
public class CouponForBuyRequestDto {
    @NotNull(message = "회원 아이디를 입력해주세요.")
    @PositiveOrZero(message = "0보다 큰 값을 입력해주세요")
    private Long memberId;
    private List<Long> bookIdList;
}
