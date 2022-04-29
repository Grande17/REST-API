package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService service;
    @MockBean
    private TaskMapper mapper;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void getTasks() throws Exception {
        //given
        Task task = new Task(1L,"title","content");
        List<Task> tasks = List.of(task);
        when(service.getAllTasks()).thenReturn(tasks);
        //when
        MockHttpServletResponse response = mockMvc.perform(get("/v1/tasks")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void getTask() throws Exception {
        //given
        Task task = new Task(1L,"title","content");
        when(service.getTask(1L)).thenReturn(task);
        //when
        MockHttpServletResponse response = mockMvc.perform(get("/v1/tasks/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void createTask() throws Exception {
        //given
        Task task = new Task(1L,"title","content");
        when(service.saveTask(task)).thenReturn(task);
        //when
        String content = mapToJson(task);
        MockHttpServletRequestBuilder request = post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        MockHttpServletResponse response = mockMvc.perform(request)
                .andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void deleteTask() throws Exception {
        //given
        //when
        MockHttpServletResponse response = mockMvc.perform(delete("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void updateTask()throws Exception {
        //given
        Task task = new Task(1L,"title","content");
        //when
        String content = mapToJson(task);

        MockHttpServletRequestBuilder request = put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }
}