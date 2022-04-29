package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {
    @InjectMocks
    private TrelloService service;
    @Mock
    private TrelloClient client;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private AdminConfig adminConfig;

    @Test
    void fetchTrelloBoards() {
        //given
        List<TrelloListDto> l1 = List.of(new TrelloListDto("1","name1",false));
        List<TrelloBoardDto> b1 = List.of(new TrelloBoardDto("name","1",l1));
        when(service.fetchTrelloBoards()).thenReturn(b1);
        //when
        List<TrelloBoardDto> result = service.fetchTrelloBoards();
        //then
        assertEquals(1,result.size());
        assertTrue(result.get(0).getName().equals("name"));


    }

    @Test
    void createTrelloCard() {
        //given
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1","name",".com");
        TrelloCardDto trelloCardDto = new TrelloCardDto("name","desc","pos","id");
        when(service.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(client.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //when
        CreatedTrelloCardDto result = service.createTrelloCard(trelloCardDto);
        //then
        assertEquals("1",result.getId());
        assertEquals("name",result.getName());
        assertEquals(".com",result.getShortUrl());
    }
}