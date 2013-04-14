package ro.reanad.taskmanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ro.reanad.taskmanager.dao.exception.WrongSubtaskException;

@Entity
@Table(name = "task")
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int idTask;

	private static int id = 0;

	@Column(unique = true)
	@NotNull
	private String generatedId;

	@Column
	@NotNull
	private String name;
	@Column
	private String description;

	@Column
	private String category;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "parentTaskId", insertable = true, updatable = true)
	private Task parentTask;

	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "parentTask", fetch = FetchType.EAGER)
	private List<Task> subTasks;
	@Column
	private Date dueDate;

	@Column
	private int timeSpent;
	@Column
	private String url;

	@ManyToOne(cascade = {})
	@JoinColumn(name = "idUser")
	@NotNull
	private User user;
	// private List<String> comments;

	@Column
	private String status;

	public Task() {
		id = (int) (Math.round(Math.random() * 1000));
		this.name = "";
		this.generatedId = "Task" + id;
		this.status = "todo";
		subTasks = new ArrayList<Task>();
		this.dueDate = new Date();
		this.parentTask=null;
	}

	public Task(User user) {
		this();
		this.user = user;
	}

	public Task(String name, User user) {
		this();
		this.name = name;
		this.user = user;
	}

	protected int getIdTask() {
		return idTask;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}

	public List<Task> getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(List<Task> subTasks) {
		this.subTasks = subTasks;
	}

	public void addSubTasks(Task subTask) throws WrongSubtaskException {
		if (subTask != null) {
				subTasks.add(subTask);
		} else
			throw new WrongSubtaskException("Subtask cannot be null");
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(int timeSpent) {
		this.timeSpent = timeSpent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
