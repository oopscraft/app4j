package org.oopscraft.arch4j.core.data.pbe;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.oopscraft.arch4j.core.support.CoreTestSupport;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PbeStringUtilTest extends CoreTestSupport {

    @Test
    @Order(1)
    void encryptAndDecrypt() {
        // given
        String decryptedValue = "test";

        // when
        String encryptedValue = PbeStringUtil.encrypt(decryptedValue);
        log.debug("encryptedValue:" + encryptedValue);

        // then
        assertNotEquals(decryptedValue, encryptedValue);
        assertEquals(decryptedValue, PbeStringUtil.decrypt(encryptedValue));
    }

    @Test
    @Order(2)
    void encodeAndDecode() {
        // given
        String decodedValue = "DEC(test)";

        // when
        String encodedValue = PbeStringUtil.encode(decodedValue);
        log.debug("encodedValue:" + encodedValue);

        // then
        assertNotEquals(decodedValue, encodedValue);
        assertEquals(decodedValue, PbeStringUtil.decode(encodedValue));

    }

}