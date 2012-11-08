package de.oose.taskboard.client;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.oose.taskboard.shared.Task;
import de.oose.taskboard.shared.TaskService;
import de.oose.taskboard.shared.TaskServiceAsync;

public class TaskboardPresenter {

	TaskServiceAsync service = GWT.create(TaskService.class);

	TaskboardView view;

	
	public void createTask() {
		service.createTask(new AsyncCallback<Task>() {

			@Override
			public void onSuccess(Task task) {
				task.setDescription("Dritter Task");
				task.setOwner("Joheinz");
				task.setHide(true);
				service.saveTask(task, new AsyncCallback<Task>() {

					@Override
					public void onFailure(Throwable arg0) {
					}

					@Override
					public void onSuccess(Task task) {
						view.addTask(task);
						load();
					}
				});
			}

			@Override
			public void onFailure(Throwable arg0) {
			}
		});
	}

	public void saveTask(Task task) {
		service.saveTask(task, new AsyncCallback<Task>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(Task task) {
				load();
			}
		});
	}

	public void load() {
		service.getAll(new AsyncCallback<List<Task>>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(List<Task> tasks) {
				tasks.add(0, new Task());
				view.setTasks(tasks);
			}
		});
	}

	public void setView(TaskboardView view) {
		this.view = view;
	}
}
