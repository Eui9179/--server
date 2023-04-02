package leui.woojoo.domain.users.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import leui.woojoo.domain.users.dto.web.*;
import leui.woojoo.domain.users.entity.Users;
import leui.woojoo.utils.SmsUtils;
import leui.woojoo.jwt.JwtProvider;
import leui.woojoo.domain.groups.service.GroupsService;
import leui.woojoo.domain.users.service.AuthService;
import leui.woojoo.domain.users.service.UsersService;
import leui.woojoo.utils.UserUtils;
import leui.woojoo.utils.FileUtils;
import leui.woojoo.domain.users.dto.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UsersService usersService;
    private final GroupsService groupsService;
    private final JwtProvider jwtProvider;
    private final FileUtils fileUtils;

    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SignupResponse> signup(SignupRequest requestDto) throws IllegalStateException, IOException {

        if (authService.findByPhoneNumber(requestDto.getPhone_number()) != null) {
            return new ResponseEntity<>(new SignupResponse(), HttpStatus.CONFLICT);
        }

        String profileImageName = fileUtils.upload(requestDto.getFile(), "profile");
        Users users = authService.save(requestDto.toUserEntity(profileImageName));

        groupsService.save(requestDto.toUserGroupEntity(users));

        String token = jwtProvider.createToken(users.getId());
        return new ResponseEntity<>(new SignupResponse(token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(HttpServletRequest request,
                                               @RequestBody LoginRequest loginRequest) {

        String phoneNumber = loginRequest.getPhoneNumber();
        UserDetail users = authService.findByPhoneNumber(phoneNumber);
        if (users == null) {
            return new ResponseEntity<>(new LoginResponse(), HttpStatus.UNAUTHORIZED);
        }

        if (!users.getFcmToken().equals(loginRequest.getFcmToken())) {
            authService.updateFcmToken(users.getId(), loginRequest.getFcmToken());
        }

        String token = jwtProvider.createToken(users.getId());

        if (!request.getSession().getAttribute(phoneNumber).equals(loginRequest.getSmsCode())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        request.getSession().removeAttribute(phoneNumber);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @DeleteMapping("/withdrawal")
    @ResponseStatus(HttpStatus.OK)
    public String withdrawUser(@AuthenticationPrincipal User user) {
        String profileImageName = authService.deleteUser(Long.parseLong(user.getUsername()));
        fileUtils.delete(profileImageName, "profile");
        return "ok";
    }

    @PostMapping("/async-token")
    @ResponseStatus(HttpStatus.OK)
    public String asyncFcmToken(@AuthenticationPrincipal User user, @RequestBody FcmRequest fcmRequest) {
        usersService.asyncFcmToken(UserUtils.resolveUserId(user), fcmRequest.getFcmToken());
        return "ok";
    }

    @PostMapping("/sms")
    public SmsResponse sendSms(HttpServletRequest request, @RequestBody PhoneNumberRequest phoneNumber) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        String cp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        log.info("cp = {}", cp);
        HttpSession session = request.getSession();
        session.setAttribute(phoneNumber.getPhoneNumber(), cp);
        return new SmsUtils().sendSms(phoneNumber.getPhoneNumber(), cp);
    }

    @PostMapping("/sms-auth")
    public ResponseEntity<String> authenticateSms(HttpServletRequest request, @RequestBody CpRequest cpRequest) {
        HttpSession session = request.getSession();
        String cpInSession = (String) session.getAttribute(cpRequest.getPhoneNumber());
        log.info("cpInSession = {}", cpInSession);
        log.info("cpInRequest = {}", cpRequest.getCp());
        if (cpRequest.getCp().equals(cpInSession)) {
            log.info("cp success? -> yes");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        log.info("cp success? -> no");
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
