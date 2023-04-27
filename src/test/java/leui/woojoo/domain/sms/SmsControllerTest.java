package leui.woojoo.domain.sms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class SmsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsRepository smsRepository;

    @Test
    @DisplayName("인증문자 보내기")
    void 인증문자를_보내다() throws Exception {
        //given
        String phoneNumber = "1111";

        String requestJson = "{\"phone_number\":\"" + phoneNumber + "\"}";

        //when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/api/sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print());

        //then
        resultActions
                .andExpect(handler().handlerType(SmsController.class))
                .andExpect(handler().methodName("sendSms"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        Optional<Sms> oSms = smsRepository.findByPhoneNumber(phoneNumber);
        assertNotNull(oSms);

        assertEquals(6, oSms.get().getCp().length());
    }

    @Test
    void authenticateSms() {
    }
}