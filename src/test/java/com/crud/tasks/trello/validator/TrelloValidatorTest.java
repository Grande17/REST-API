package com.crud.tasks.trello.validator;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloList;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloValidatorTest {

    @Autowired
    private TrelloValidator validator;


    @Test
    void validateCardContainsTest() {
        //given
        TrelloCard trelloCard = new TrelloCard("test","test","test","test");
        //when
        validator.validateCard(trelloCard);
    }
    @Test
    void validateCardTest(){
        //given
        TrelloCard trelloCard = new TrelloCard("hello","hello","hello","hello");
        //when
        validator.validateCard(trelloCard);
    }

    @Test
    void validateTrelloBoards() {
        //given
        List<TrelloList> list1 = List.of(new TrelloList("1","name",false),
                new TrelloList("2","name",false));
        List<TrelloList> list2 = List.of(new TrelloList("3","name",false),
                new TrelloList("4","name",false));

        List<TrelloBoard> boards = List.of(new TrelloBoard("1","name",list1),
                new TrelloBoard("2","name",list2));
        //when
        validator.validateTrelloBoards(boards);
    }
}