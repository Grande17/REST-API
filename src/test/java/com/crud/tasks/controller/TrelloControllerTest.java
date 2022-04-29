package com.crud.tasks.controller;


import com.crud.tasks.domain.*;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TrelloController.class)
class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrelloService trelloService;
    @MockBean
    private TrelloFacade trelloFacade;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void getTrelloBoards() throws Exception {
        //given
        List<TrelloListDto> listDtos = List.of(new TrelloListDto("1","name",false));
        List<TrelloBoardDto> dtos = List.of(new TrelloBoardDto("name","1",listDtos));
        when(trelloService.fetchTrelloBoards()).thenReturn(dtos);
        //when
        MockHttpServletResponse response = mockMvc.perform(get("/v1/trello/boards")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void createTrelloCard() throws Exception {
        //given
        TrelloCardDto card = new TrelloCardDto("name","desc","pos","listid");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("idd","name","url.com");
        when(trelloService.createTrelloCard(card)).thenReturn(createdCard);
        //when
        String content = mapToJson(createdCard);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                        .content(content);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }
}