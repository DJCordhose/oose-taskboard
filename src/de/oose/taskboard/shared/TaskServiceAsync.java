package de.oose.taskboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface TaskServiceAsync {

	void createTask(AsyncCallback<Task> callback);

	void getAll(AsyncCallback<List<Task>> callback);

	void saveTask(Task task, AsyncCallback<Task> callback);

}
