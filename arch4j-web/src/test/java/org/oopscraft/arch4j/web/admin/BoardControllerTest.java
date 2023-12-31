package org.oopscraft.arch4j.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.oopscraft.arch4j.web.support.WebTestSupport;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
class BoardControllerTest extends WebTestSupport {

    private final BoardController boardController;

    @Test
    public void getSkinNames() throws IOException {
        Set<String> skinNames = boardController.getSkinNames();
        skinNames.forEach(skinName -> log.info("{}", skinName));
    }

}