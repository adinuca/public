package ro.reanad.taskmanager.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ro.reanad.taskmanager.model.Task;

@Repository
public class TaskDaoImpl extends AbstractDao implements TaskDao {
	private final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);
	@Override
	public void modifyTask(Task task) {
		logger.debug("Modified task ",task.toString());
		getSessionFactory().getCurrentSession().saveOrUpdate(task);
	}

	@Override
	public void createTask(Task task) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(task);
			logger.info("Created task ", task.toString());
		} catch (ConstraintViolationException ex) {
			if (ex.getLocalizedMessage().contains("generatedId_UNIQUE")) {
				task.generateId();
				createTask(task);
			}
		}
	}

	@Override
	public void removeTask(String generatedId) {
		Task task = getTask(generatedId);
		Session session = getSessionFactory().getCurrentSession();
		if (task != null) {
			logger.debug("Removed task ",task.toString());
			session.delete(task);
		}
	}

	@Override
	public Task getTask(String generatedId) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session
				.createQuery("from Task as task where task.generatedId=:generatedId");
		query.setParameter("generatedId", generatedId);
        return (Task) query.uniqueResult();
	}

	@Override
	public List<Task> getTasksWithCategoryForUsername(String userEmail,
			String category) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session
				.createQuery("from ro.reanad.taskmanager.model.Task as task where task.userEmail=:userEmail and task.category=:category and task.parentTask=null");
		query.setParameter("userEmail", userEmail);
		query.setParameter("category", category);
        return query.list();
	}

	@Override
	public List<Task> getTasksWithStatusForUsername(String username,
			String status) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session
				.createQuery("from ro.reanad.taskmanager.model.Task as task where task.userEmail=:userEmail and task.status=:status and task.parentTask=null");
		query.setParameter("userEmail", username);
		query.setParameter("status", status);
        return query.list();
	}

	@Override
	public List<Task> getTasksForUsername(String username) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session
				.createQuery("from ro.reanad.taskmanager.model.Task as task where task.userEmail=:userEmail and task.parentTask=null");
		query.setParameter("userEmail", username);
        return query.list();
	}
}

