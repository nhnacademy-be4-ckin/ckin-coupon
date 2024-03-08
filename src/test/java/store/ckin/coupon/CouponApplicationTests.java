package store.ckin.coupon;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import store.ckin.coupon.coupon.dto.request.CreateCouponRequestDto;
import store.ckin.coupon.coupon.model.Coupon;
import store.ckin.coupon.coupon.service.CouponService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CouponApplicationTests {
    @Test
    void contextLoads() {
    }
}
