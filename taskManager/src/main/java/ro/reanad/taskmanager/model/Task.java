package ro.reanad.taskmanager.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import ro.reanad.taskmanager.dao.exception.WrongSubtaskException;

@Entity
@Table(name = "task")
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idTask;

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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parentTaskId", insertable = true, updatable = true)
    private Task parentTask;

    @Transient
    private String parentTaskId;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "parentTask", fetch = FetchType.EAGER)
    private List<Task> task = new ArrayList<Task>();

    @Column
    private Date dueDate;

    @Column
    private int timeSpent;

    @Column
    private String url;


    @NotNull
    private String userEmail;
    // private List<String> comments;

    @Column
    private String status;

    public Task() {
        initializeFields(null, null);
    }

    private void initializeFields(String name, String userEmail) {
        generateId();
        this.name = name;
        this.status = "todo";
        this.userEmail = userEmail;
        this.task = new ArrayList<Task>();
        this.dueDate = new Date();
        this.parentTask = null;
    }

    public Task(String userEmail) {
        initializeFields(null, userEmail);
    }

    public Task(String name, String userEmail) {
        initializeFields(name, userEmail);
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

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> subTasks) {
        this.task = subTasks;
    }

    public void addSubTasks(Task subTask) throws WrongSubtaskException {
        if (subTask != null) {
            task.add(subTask);
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUser(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void generateId() {
        this.generatedId = (int) (Math.round(Math.random() * 1000)) + "Task";
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", generatedId='" + generatedId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", task=" + task +
                ", dueDate=" + dueDate +
                ", timeSpent=" + timeSpent +
                ", url='" + url + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
