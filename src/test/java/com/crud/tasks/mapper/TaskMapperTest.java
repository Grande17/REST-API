package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    private TaskMapper mapper;

    @Test
    void mapToTask() {
        //given
        TaskDto taskDto = new TaskDto(1L,"title","content");
        //when
        Task mappedTask = mapper.mapToTask(taskDto);
        //then
        assertEquals(taskDto.getId(),mappedTask.getId());
        assertEquals(taskDto.getTitle(),mappedTask.getTitle());
        assertEquals(taskDto.getContent(),mappedTask.getContent());

    }

    @Test
    void mapToTaskDto() {
        //given
        Task task = new Task(1L,"title","content");
        //when
        TaskDto mappedTaskDto = mapper.mapToTaskDto(task);
        //then
        assertEquals(task.getId(),mappedTaskDto.getId());
        assertEquals(task.getTitle(),mappedTaskDto.getTitle());
        assertEquals(task.getContent(),mappedTaskDto.getContent());
    }

    @Test
    void mapToTaskDtoList() {
        //given
        List<Task> toProceed = List.of(new Task(1L,"title","content"));
        //when
        List<TaskDto> dtos = mapper.mapToTaskDtoList(toProceed);
        //then
        assertThat(dtos).isNotNull();
        assertThat(dtos.size()).isEqualTo(1);

        dtos.forEach(taskDto -> {
            assertThat(taskDto.getId()).isEqualTo(1L);
            assertThat(taskDto.getTitle()).isEqualTo("title");
            assertThat(taskDto.getContent()).isEqualTo("content");
        });
    }
}