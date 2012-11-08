package de.oose.taskboard.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.oose.taskboard.shared.Task;
import de.oose.taskboard.shared.TaskService;

@SuppressWarnings("serial")
public class TaskServiceImpl extends RemoteServiceServlet implements
		TaskService {

	abstract class Template<T> {

		protected EntityManager em;

		public T execute() {
			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory("taskboardHsql");
			em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			try {
				try {
					T call = mach();
					tx.commit();
					return call;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} catch (Throwable t) {
				tx.rollback();
				throw new RuntimeException(t);
			} finally {
				em.close();
				emf.close();
			}
		}

		protected abstract T mach();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.oose.taskboard.server.TaskService#createTask()
	 */
	@Override
	public Task createTask() {
		return new Template<Task>() {
			@Override
			protected Task mach() {
				Task task = new Task();
				em.persist(task);
				return task;
			}

		}.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.oose.taskboard.server.TaskService#saveTask(de.oose.taskboard.shared
	 * .Task)
	 */
	@Override
	public Task saveTask(final Task task) {
		return new Template<Task>() {
			@Override
			protected Task mach() {
				if (task.getId() == 0) {
					em.persist(task);
					return task;
				} else {
					return em.merge(task);
				}
			}

		}.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.oose.taskboard.server.TaskService#getAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Task> getAll() {
		return new Template<List<Task>>() {
			@Override
			protected List<Task> mach() {
				Query query = em.createNamedQuery(Task.QUERY_ALL);
				return query.getResultList();
			}

		}.execute();

	}
}
