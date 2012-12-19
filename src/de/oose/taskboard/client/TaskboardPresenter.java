package de.oose.taskboard.client;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;

import de.oose.taskboard.shared.TaskBoundary;
import de.oose.taskboard.shared.TaskBoundaryAsync;
import de.oose.taskboard.shared.entity.Task;

public class TaskboardPresenter {

	TaskBoundaryAsync service = GWT.create(TaskBoundary.class);

	TaskboardView view;

	private ListDataProvider<Task> dataProvider;

	public void createTask() {
		service.createTask(new AsyncCallback<Task>() {

			@Override
			public void onSuccess(Task task) {
				service.saveTask(task, new AsyncCallback<Task>() {

					@Override
					public void onFailure(Throwable arg0) {
					}

					@Override
					public void onSuccess(Task task) {
						addTask(task);
						load();
					}
				});
			}

			@Override
			public void onFailure(Throwable arg0) {
			}
		});
	}

	void addTask(Task task) {
		if (getDataProvider() != null) {
			getDataProvider().getList().add(task);
		}
	}

	void setTasks(List<Task> tasks) {
		if (getDataProvider() != null) {
			getDataProvider().setList(tasks);
			// FIXME: needed?
			getDataProvider().refresh();
		}
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
				setTasks(tasks);
			}
		});
	}

	public void setView(TaskboardView view) {
		this.view = view;
	}

	public ListDataProvider<Task> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ListDataProvider<Task> dataProvider) {
		this.dataProvider = dataProvider;
	}
}
