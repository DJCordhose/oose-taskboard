package de.oose.taskboard.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class TaskListDto implements Serializable {

	public enum Type {
		SHOPPING, GENERAL;
	}

	public int id;
	public Type type = Type.GENERAL;

	public String name;

	public List<TaskDto> tasks = new ArrayList<TaskDto>();
}