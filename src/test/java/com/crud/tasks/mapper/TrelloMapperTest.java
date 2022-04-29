package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrelloMapperTest {

    @Autowired
    private TrelloMapper mapper;

    @Test
    void mapToBoards() {
        //given
        List<TrelloListDto> listDtos = List.of(new TrelloListDto("1","name",false));
        List<TrelloBoardDto> dtos = List.of(new TrelloBoardDto("name","1",listDtos));
        //when
        List<TrelloBoard> result = mapper.mapToBoards(dtos);
        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        result.forEach(x->{
            assertThat(x.getId()).isEqualTo("1");
            assertThat(x.getName()).isEqualTo("name");

            x.getLists().forEach(list->{
                assertThat(list.getId()).isEqualTo("1");
                assertThat(list.getName()).isEqualTo("name");
                assertThat(list.isClosed()).isEqualTo(false);
            });
        });

    }

    @Test
    void mapToBoardsDto() {
        //given
        List<TrelloList> trelloList = List.of(new TrelloList("1","name",false));
        List<TrelloBoard> trelloBoards = List.of(new TrelloBoard("name","1",trelloList));
        //when
        List<TrelloBoardDto> result = mapper.mapToBoardsDto(trelloBoards);
        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        result.forEach(x->{
            assertThat(x.getId()).isEqualTo("1");
            assertThat(x.getName()).isEqualTo("name");

            x.getLists().forEach(list->{
                assertThat(list.getId()).isEqualTo("1");
                assertThat(list.getName()).isEqualTo("name");
                assertThat(list.isClosed()).isEqualTo(false);
            });
        });
    }

    @Test
    void mapToList() {
        //given
        List<TrelloListDto> listDto = List.of(new TrelloListDto("1","name",false));
        //when
        List<TrelloList> result = mapper.mapToList(listDto);
        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        result.forEach(x->{
            assertThat(x.getId()).isEqualTo("1");
            assertThat(x.getName()).isEqualTo("name");
            assertThat(x.isClosed()).isEqualTo(false);
        });
    }

    @Test
    void mapToListDto() {
        //given
        List<TrelloList> list = List.of(new TrelloList("1","name",false));
        //when
        List<TrelloListDto> result = mapper.mapToListDto(list);
        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        result.forEach(x->{
            assertThat(x.getId()).isEqualTo("1");
            assertThat(x.getName()).isEqualTo("name");
            assertThat(x.isClosed()).isEqualTo(false);
        });
    }

    @Test
    void mapToCardDto() {
        //given
        TrelloCard card = new TrelloCard("name","desc","pos","id");
        //when
        TrelloCardDto result = mapper.mapToCardDto(card);
        //then
        assertEquals(card.getName(),result.getName());
        assertEquals(card.getDescription(),result.getDescription());
        assertEquals(card.getPos(),result.getPos());
        assertEquals(card.getListId(),result.getListId());
    }

    @Test
    void mapToCard() {
        //given
        TrelloCardDto cardDto = new TrelloCardDto("name","desc","pos","id");
        //when
        TrelloCard result = mapper.mapToCard(cardDto);
        //then
        assertEquals(cardDto.getName(),result.getName());
        assertEquals(cardDto.getDescription(),result.getDescription());
        assertEquals(cardDto.getPos(),result.getPos());
        assertEquals(cardDto.getListId(),result.getListId());
    }
}