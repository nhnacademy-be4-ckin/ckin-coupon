package store.ckin.coupon.keymanager;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * KeyResponseDto
 *
 * @author : 이가은
 * @version : 2024. 02. 29
 */
@Getter
public class KeyResponseDto {
    private Header header;
    private Body body;

    @Getter
    @NoArgsConstructor
    public static class Body {
        private String secret;
    }

    @Getter
    @NoArgsConstructor
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private boolean isSuccessful;
    }
}