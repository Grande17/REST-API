package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @GetMapping("get-Tasks")
    public List<TaskDto> getTasks(){
        return new ArrayList<>();
    }
    @GetMapping("/get-task")
    public TaskDto getTask(Long taskId){
        return new TaskDto(1l,"test title","test content");
    }
    @DeleteMapping("/remove")
    public void deleteTask(Long taskId){

    }
    @PutMapping("/update")
    public TaskDto updateTask(TaskDto task){
        return new TaskDto(1l,"edited task","edited content");
    }
    @PostMapping("/create")
    public void createTask(TaskDto task){

    }

}
