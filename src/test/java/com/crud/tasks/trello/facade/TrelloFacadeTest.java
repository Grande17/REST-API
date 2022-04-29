package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;
    @Mock
    private TrelloMapper mapper;
    @Mock
    private TrelloService service;
    @Mock
    private TrelloValidator validator;

    @Test
    void fetchTrelloBoardsTest() {
        //given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1","list1",false));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("name","1",trelloListDto));

        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1","list1",false));

        List<TrelloBoard> trelloBoard = new ArrayList<>();
        trelloBoard.add(new TrelloBoard("name","1",trelloList));

        when(service.fetchTrelloBoards()).thenReturn(trelloBoardDto);
        when(mapper.mapToBoards(trelloBoardDto)).thenReturn(trelloBoard);
        when(mapper.mapToBoardsDto(anyList())).thenReturn(trelloBoardDto);
        when(validator.validateTrelloBoards(trelloBoard)).thenReturn(trelloBoard);
        //when
        List<TrelloBoardDto> boards = trelloFacade.fetchTrelloBoards();
        //then
        assertEquals(1,boards.size());

        String id = boards.get(0).getId();
        String name = boards.get(0).getName();
        assertEquals("1",id);
        assertEquals("name",name);

    }

    @Test
    void createCard() {
        //given
        CreatedTrelloCardDto  createdTrelloCardDto = new CreatedTrelloCardDto("1","name","url.com");
        TrelloCardDto trelloCardDto = new TrelloCardDto("name","desc","pos","id");
        TrelloCard trelloCard = new TrelloCard("name","desc","pos","id");

        when(mapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(mapper.mapToCardDto(trelloCard)).thenReturn(trelloCardDto);
        when(service.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //when
        CreatedTrelloCardDto createdTrelloCardDto1 = trelloFacade.createCard(trelloCardDto);
        //then
        assertEquals("1",createdTrelloCardDto1.getId());
        assertEquals("name",createdTrelloCardDto1.getName());
    }

    @Test
    void shouldFetchEmptyList() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(service.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(mapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(mapper.mapToBoardsDto(anyList())).thenReturn(List.of());
        when(validator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());
        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        // Then
        assertThat(trelloBoardDtos).isNotNull();
        assertThat(trelloBoardDtos.size()).isEqualTo(0);

    }
    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("test", "1", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(service.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(mapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(mapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(validator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertThat(trelloBoardDtos).isNotNull();
        assertThat(trelloBoardDtos.size()).isEqualTo(1);

        trelloBoardDtos.forEach(trelloBoardDto -> {

            assertThat(trelloBoardDto.getId()).isEqualTo("1");
            assertThat(trelloBoardDto.getName()).isEqualTo("test");

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertThat(trelloListDto.getId()).isEqualTo("1");
                assertThat(trelloListDto.getName()).isEqualTo("test_list");
                assertThat(trelloListDto.isClosed()).isFalse();
            });
        });
    }

}
