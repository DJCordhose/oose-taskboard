package de.oose.taskboard.shared.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@SuppressWarnings("serial")
@Entity
public class TaskList implements Serializable {

	public enum Type {
		SHOPPING, GENERAL;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Enumerated(EnumType.STRING)
	Type type = Type.GENERAL;

	private String name;

	@OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
	List<Task> tasks = new ArrayList<Task>();

	@Version
	int version;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void addTask(Task task) {
		tasks.add(task);
		task.setList(this);
	}

	public void setTasks(List<Task> tasks) {
		tasks.clear();
		for (Task task : tasks) {
			addTask(task);
		}
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
