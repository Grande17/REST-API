package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbServiceTest {
    @InjectMocks
    private DbService service;
    @Mock
    private TaskRepository repository;

    @Test
    void getAllTasks() {
        //given
        List<Task> tasks = Arrays.asList(new Task(1L,"task","content"),
                new Task(2L,"task1","content1"));
        when(repository.findAll()).thenReturn(tasks);
        //when
        List<Task> result = service.getAllTasks();
        //then
        assertEquals(2,result.size());
    }

    @Test
    void getTask() throws TaskNotFoundException {
        //given
        Task task = new Task(1L,"task","content");
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        //when
        Task result = service.getTask(1L);
        //then
        assertEquals(1L,result.getId());
    }

    @Test
    void saveTask() {
        //given
        Task task = new Task(1L,"task","content");
        when(repository.save(task)).thenReturn(task);
        //when
        Task savedTask = service.saveTask(task);
        //then
        assertEquals(task.getId(),savedTask.getId());
    }

    @Test
    void deleteTask() {
        //when
        service.deleteTask(88L);
        //then
        verify(repository,times(1)).deleteById(88L);
    }
}