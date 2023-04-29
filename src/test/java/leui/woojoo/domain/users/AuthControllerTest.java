package leui.woojoo.domain.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import leui.woojoo.DataNotFoundException;
import leui.woojoo.domain.groups.entity.GroupsRepository;
import leui.woojoo.domain.sms.Sms;
import leui.woojoo.domain.sms.SmsRepository;
import leui.woojoo.domain.sms.SmsService;
import leui.woojoo.domain.users.controller.AuthController;
import leui.woojoo.domain.users.dto.web.LoginRequest;
import leui.woojoo.domain.users.entity.Users;
import leui.woojoo.domain.users.entity.UsersRepository;
import leui.woojoo.domain.users.dto.web.SignupResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SmsRepository smsRepository;

    @Value("${file.path}")
    private String filepath;

    @Test
    @DisplayName("유저 회원가입 테스트")
    void t001() throws IOException {
        //given
        String name = "이의찬";
        String phoneNumber = "11111111";
        String fcmToken = "1234";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", name);
        body.add("phone_number", phoneNumber);
        body.add("file", new ClassPathResource("/static/profile/test_image.png"));
        body.add("groups", "인천대학교");
        body.add("detail1", "3");
        body.add("fcm_token", fcmToken);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        String url = "http://localhost:" + port + "/api/auth/signup";

        //when
        ResponseEntity<SignupResponse> response = restTemplate
                .postForEntity(url, requestEntity, SignupResponse.class);

        /**
         * HTTP status code: 200
         * Access token: not null
         */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAccessToken()).isNotNull();

        /**
         * Users database test
         */
        List<Users> userList = usersRepository.findAll();
        Users user = userList.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("데이터 없음"));

        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(userList.get(0).getId()).isGreaterThan(0L);

        /**
         * File test(실제 디렉토리 검사)
         */
        File dir = new File(filepath + "profile/");
        File[] files = dir.listFiles();
        List<String> fileList = Arrays.stream(files).map(File::getName).toList();
        assertThat(fileList.contains(userList.get(0).getProfileImageName())).isTrue();
    }

    @Test
    @DisplayName("로그인 테스트")
    void t002() throws Exception {
        //given
        //cp 데이터 자동으로 생성됨
        String phoneNumber = "1111";
        String smsCode = "000000";
        String modifiedFcm = "2345";

        LoginRequest loginRequest = LoginRequest
                .builder()
                .phoneNumber(phoneNumber)
                .smsCode(smsCode)
                .fcmToken(modifiedFcm)
                .build();

        String json = new ObjectMapper().writeValueAsString(loginRequest);

        //when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));

        //then
        resultActions
                .andExpect(handler().handlerType(AuthController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(status().is2xxSuccessful());

        Users user = usersRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataNotFoundException("데이터 없음"));
        assertEquals(modifiedFcm, user.getFcmToken());

        Optional<Sms> oSms = smsRepository.findByPhoneNumber(phoneNumber);
        assertFalse(oSms.isPresent());
    }
}
