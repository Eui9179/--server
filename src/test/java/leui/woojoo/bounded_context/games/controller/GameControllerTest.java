package leui.woojoo.bounded_context.games.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import leui.woojoo.bounded_context.games.dto.Game;
import leui.woojoo.bounded_context.games.entity.Games;
import leui.woojoo.bounded_context.games.service.GamesService;
import leui.woojoo.bounded_context.users.entity.Users;
import leui.woojoo.bounded_context.users.service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Autowired
    private GamesService gamesService;

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
        assertTrue(contentAsString.contains("leagueoflegends"));
        assertTrue(contentAsString.contains("overwatch"));
    }

    @Test
    @DisplayName("게임 등록 테스트")
    void t002() throws Exception {
        /**
         * 기존 데이터
         * leagueoflegends
         * overwatch
         *
         * 이후 데이터
         * leagueoflegends
         * valorant
         */
        //given
        String phoneNumber = "1111";
        Users users = usersService.findByPhoneNumber(phoneNumber);
        List<String> givenGames = List.of("leagueoflegends", "valorant");
        String json = new ObjectMapper().writeValueAsString(givenGames);

        //when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/api/games")
                        .with(SecurityMockMvcRequestPostProcessors.user(users.getId().toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print());

        //then
        MvcResult mvcResult = resultActions
                .andExpect(handler().handlerType(GameController.class))
                .andExpect(handler().methodName("updateMyGames"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertTrue(result.contains("leagueoflegends"));
        assertTrue(result.contains("valorant"));
        assertFalse(result.contains("overwatch"));
    }
}
