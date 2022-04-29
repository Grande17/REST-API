package com.crud.tasks;

import com.crud.tasks.controller.TaskController;
import com.crud.tasks.controller.TrelloController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class TasksApplicationTests {
	@Autowired
	private TaskController taskController;
	@Autowired
	private TrelloController trelloController;

	@Test
	void contextLoads() {
		assertThat(taskController).isNotNull();
		assertThat(trelloController).isNotNull();
	}

}
