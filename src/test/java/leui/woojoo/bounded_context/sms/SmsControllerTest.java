package leui.woojoo.bounded_context.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import leui.woojoo.bounded_context.users.dto.web.CpRequest;
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
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @DisplayName("인증문자 보내기 테스트")
    void t001() throws Exception {
        //given
        String phoneNumber = "2222";

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
    @DisplayName("문자 인증 테스트")
    void t002() throws Exception {
        //given
        String phoneNumber = "2222";
        String cp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        smsService.save(phoneNumber, cp);

        //when
        CpRequest cpRequest = CpRequest
                .builder()
                .phoneNumber(phoneNumber)
                .cp(cp)
                .build();
        String json = new ObjectMapper().writeValueAsString(cpRequest);

        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/api/sms-auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));

        //then
        resultActions
                .andExpect(handler().handlerType(SmsController.class))
                .andExpect(handler().methodName("authenticateSms"))
                .andExpect(status().is2xxSuccessful());

    }
}