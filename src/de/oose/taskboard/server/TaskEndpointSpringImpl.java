package de.oose.taskboard.server;

import java.util.List;

import javax.inject.Inject;

import de.oose.taskboard.shared.Task;
import de.oose.taskboard.shared.TaskEndpoint;

@SuppressWarnings("serial")
public class TaskEndpointSpringImpl extends AbstractSpringBoundary
		implements TaskEndpoint {

	@Inject
	TaskService taskService;

	@Override
	public Task createTask() {
		return taskService.createTask();
	}

	@Override
	public Task saveTask(final Task task) {
		return taskService.saveTask(task);
	}

	@Override
	public List<Task> getAll() {
		return taskService.getAll();
	}
}
