package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService service;
    @MockBean
    private TaskMapper mapper;




    @Test
    void getAllTasks() throws Exception {
        //given
        List<TaskDto> tasks = List.of(new TaskDto(1L,"title","content"));
        when(mapper.mapToTaskDtoList(anyList())).thenReturn(tasks);
        //when & then
        mockMvc
                .perform(get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id",Matchers.is(1)))
                .andExpect(jsonPath("$[0].title",Matchers.is("title")))
                .andExpect(jsonPath("$[0].content",Matchers.is("content")));
    }
    @Test
    void fetchEmptyList() throws Exception {
        //given
        when(mapper.mapToTaskDtoList(anyList())).thenReturn(List.of());
        //when & then
        mockMvc
                .perform(get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",Matchers.hasSize(0)));
    }

    @Test
    void getTask() throws Exception {
        //given
        Task task = new Task(1L,"title","content");
        TaskDto dto = new TaskDto(1L,"title","content");
        when(mapper.mapToTaskDto(task)).thenReturn(dto);
        when(service.getTask(1L)).thenReturn(task);
        //when & then
        mockMvc
                .perform(get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",Matchers.is(1)))
                .andExpect(jsonPath("$.title",Matchers.is("title")))
                .andExpect(jsonPath("$.content",Matchers.is("content")));
    }

    @Test
    void createTask() throws Exception {
        //given
        Task task = new Task(1L,"title","content");
        TaskDto taskDto = new TaskDto(1L,"title","content");

        when(mapper.mapToTask(any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String content = gson.toJson(taskDto);
        //when
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content))
                .andExpect(status().isOk());

    }

    @Test
    void deleteTask() throws Exception {
        //given
        Task task = new Task(1L,"title","content");
        when(service.getTask(1L)).thenReturn(task);
        //when
        mockMvc
                .perform(delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateTask()throws Exception {
        //given
        Task task = new Task(1L,"title","content");
        TaskDto taskDto = new TaskDto(1L,"title","content");

        when(mapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(mapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String content = gson.toJson(taskDto);
        //when
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content))
                .andExpect(status().isOk());

    }
}