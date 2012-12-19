package de.oose.taskboard.server;

import java.util.List;

import javax.inject.Inject;

import de.oose.taskboard.shared.TaskBoundary;
import de.oose.taskboard.shared.entity.Task;

@SuppressWarnings("serial")
public class TaskBoundarySpringImpl extends AbstractSpringBoundary
		implements TaskBoundary {

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
