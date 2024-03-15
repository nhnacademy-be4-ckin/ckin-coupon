package store.ckin.coupon.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import store.ckin.coupon.coupon.exception.CouponNotFoundException;
import store.ckin.coupon.coupontemplate.exception.CouponTemplateNotFoundException;
import store.ckin.coupon.policy.exception.CouponCodeNotFoundException;

/**
 * ControllerAdvice
 *
 * @author : gaeun
 * @version : 2024. 02. 19
 */
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> getValidException() {
        ErrorResponse content = new ErrorResponse("Valid Error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(content);
    }

    @ExceptionHandler({CouponNotFoundException.class, CouponCodeNotFoundException.class,
            CouponTemplateNotFoundException.class, CouponNotFoundException.class})
    public ResponseEntity<ErrorResponse> getRuntimeException(RuntimeException runtimeException) {
        ErrorResponse content = new ErrorResponse(runtimeException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(content);
    }
}
