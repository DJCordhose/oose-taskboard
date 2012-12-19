package de.oose.taskboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.oose.taskboard.shared.entity.Task;


@RemoteServiceRelativePath("task")
public interface TaskBoundary extends RemoteService {

	public abstract Task createTask();

	public abstract Task saveTask(Task task);

	public abstract List<Task> getAll();

}