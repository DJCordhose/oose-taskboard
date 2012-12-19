package de.oose.taskboard.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.oose.taskboard.shared.entity.Task;

@SuppressWarnings("serial")
public class TaskSpringRestEndpoint extends AbstractSpringServlet {

	private ObjectMapper mapper;
	
	private final static class ReturnCode {
		public String message;

		public ReturnCode(String message) {
			super();
			this.message = message;
		}
	}

	@Inject
	TaskService taskService;

	@Override
	public void init() throws ServletException {
		super.init();
		mapper = new ObjectMapper();
	}
	
	// delete
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

	// read
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Task> all = taskService.getAll();
		passResult(resp, all);
	}

	private void passResult(HttpServletResponse resp, Object result) {
		try {
			String json = mapper.writeValueAsString(result);
			resp.getWriter().write(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// update
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Task task = readParameterFromBody(req, Task.class);
		taskService.saveTask(task);
		returnCode(resp, "ok");
	}

	private void returnCode(HttpServletResponse resp, String message) {
		passResult(resp, new ReturnCode(message));
	}

	private <T> T readParameterFromBody(HttpServletRequest req,
			Class<T> classOfT) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					req.getInputStream(), "UTF-8"));
			T t = mapper.readValue(reader, classOfT);
			return t;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// create
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		taskService.createTask();
	}

}
