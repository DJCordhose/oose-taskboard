package de.oose.taskboard.shared.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@NamedQueries({
	@NamedQuery(name = Task.QUERY_ALL, query="SELECT t FROM Task t")})
@JsonIgnoreProperties(ignoreUnknown=true, value={"version"})
public class Task implements Serializable {
	public final static String QUERY_ALL = "Task.all";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	@Enumerated(EnumType.STRING)
	State state = State.TODO;
	String description = "";
	String owner = "";
	boolean hide = false;
	
	@ManyToOne
	private TaskList list;
	
	@Version
	int version;

	public Task() {
		
	}
	
	public Task(int id, State state, String description, String owner,
			boolean hide) {
		super();
		this.id = id;
		this.state = state;
		this.description = description;
		this.owner = owner;
		this.hide = hide;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public TaskList getList() {
		return list;
	}

	public void setList(TaskList list) {
		this.list = list;
	}

}
