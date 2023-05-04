package leui.woojoo.bounded_context.games.controller;

import leui.woojoo.bounded_context.users.entity.Users;
import leui.woojoo.bounded_context.users.service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class GameControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UsersService usersService;

    @Test
    @DisplayName("저장되어 있는 게임 데이터 테스트")
    void t001() throws Exception {
        //given
        String phoneNumber = "1111";
        Users user = usersService.findByPhoneNumber(phoneNumber);

        //then
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/api/games/me")
                        .with(SecurityMockMvcRequestPostProcessors.user(user.getId().toString())))
                .andDo(print());

        //when
        MvcResult result = resultActions
                .andExpect(handler().handlerType(GameController.class))
                .andExpect(handler().methodName("getMyGames"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(contentAsString.contains("leagueoflegends"));
        Assertions.assertTrue(contentAsString.contains("overwatch"));
        System.out.println("contentAsString = " + contentAsString);
    }
}
