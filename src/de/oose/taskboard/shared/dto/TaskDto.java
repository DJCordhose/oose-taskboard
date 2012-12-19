package de.oose.taskboard.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TaskDto implements Serializable {
	public enum State {
		TODO, IN_PROGRESS, DONE;
	}

	public int id;
	public State state = State.TODO;
	public String description = "";
	public String owner = "";
	public boolean hide = false;
	
	public TaskListDto list;
	
	public TaskDto() {
		
	}
	
	public TaskDto(int id, State state, String description, String owner,
			boolean hide) {
		super();
		this.id = id;
		this.state = state;
		this.description = description;
		this.owner = owner;
		this.hide = hide;
	}
}
