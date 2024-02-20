package store.ckin.coupon.config;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 20
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.ckin.coupon.policy.model.CouponCode;
import store.ckin.coupon.policy.model.CouponPolicy;

public class MyApp1 {
    final static Logger logger = LoggerFactory.getLogger(MyApp1.class);

    public static void main(String[] args) {
        logger.info("Entering application.");

        CouponPolicy couponPolicy = new CouponPolicy(1L, new CouponCode("정액"), 10000, 3000, null, 20000, true);
        logger.info("Exiting application.");
    }
}
