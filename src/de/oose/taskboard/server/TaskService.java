package de.oose.taskboard.server;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import de.oose.taskboard.shared.Task;
import de.oose.taskboard.shared.TaskList;

@Named
@Transactional
public class TaskService {

	@PersistenceContext
	EntityManager em;

	public Task createTask() {
		Task task = new Task();
		em.persist(task);
		return task;
	}

	public Task saveTask(final Task task) {
		if (task.getId() == 0) {
			em.persist(task);
			return task;
		} else {
			return em.merge(task);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAll() {
		Query query = em.createNamedQuery(Task.QUERY_ALL);
		return query.getResultList();
	}
	
	public TaskList detach(TaskList taskList) {
		if (taskList.getTasks() != null) {
			taskList.setTasks(new ArrayList<Task>(taskList.getTasks()));
		}
		return taskList;
	}
	
	public Task detach(Task task) {
		return task;
	}


}
