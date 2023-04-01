package leui.woojoo.web.controller.users;

import leui.woojoo.domain.groups.entity.GroupsRepository;
import leui.woojoo.domain.users.entity.Users;
import leui.woojoo.domain.users.entity.UsersRepository;
import leui.woojoo.domain.users.dto.web.SignupResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Value("${file.path}")
    private String filepath;

    @AfterEach
    void tearDown() throws Exception {
        usersRepository.deleteAll();
        groupsRepository.deleteAll();
    }

    @Test
    void 유저가_회원가입을_하다() throws IOException {
        //given
        String name = "이의찬";
        String phoneNumber = "+1026649179";
        String fcmToken = "1234";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", name);
        body.add("phone_number", phoneNumber);
        body.add("file", new ClassPathResource("/static/test_image.png"));
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
        assertThat(userList.get(0).getId()).isGreaterThan(0L);
        assertThat(userList.get(0).getName()).isEqualTo(name);

        /**
         * File test(실제 디렉토리 검사)
         */
        File dir = new File(filepath + "profile/");
        File[] files = dir.listFiles();
        List<String> fileList = Arrays.stream(files).map(File::getName).toList();
        assertThat(fileList.contains(userList.get(0).getProfileImageName())).isTrue();
    }
}
