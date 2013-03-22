package ro.reanad.taskmanager.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ro.reanad.taskmanager.dao.exception.DuplicateGeneratedIdException;
import ro.reanad.taskmanager.model.Task;

@Repository
public class TaskDaoImpl extends AbstractDao implements TaskDao {
	private Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);
	@Override
	public void modifyTask(Task task) {
		logger.debug("Modified task ",task.toString());
		getSessionFactory().getCurrentSession().saveOrUpdate(task);
	}

	@Override
	public void createTask(Task task) throws DuplicateGeneratedIdException {
		try {
			logger.debug("Created task ",task.toString());
			getSessionFactory().getCurrentSession().saveOrUpdate(task);
		} catch (ConstraintViolationException ex) {
			throw new DuplicateGeneratedIdException();
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
		Task task = (Task) query.uniqueResult();
		return task;
	}

	@Override
	public List<Task> getTasksWithCategoryForUsername(String username,
			String category) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session
				.createQuery("from ro.reanad.taskmanager.model.Task as task where task.user.username=:username and task.category=:category and task.parentTask=null");
		query.setParameter("username", username);
		query.setParameter("category", category);
		@SuppressWarnings("unchecked")
		List<Task> tasks = query.list();
		return tasks;
	}

	@Override
	public List<Task> getTasksWithStatusForUsername(String username,
			String status) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session
				.createQuery("from ro.reanad.taskmanager.model.Task as task where task.user.username=:username and task.status=:status and task.parentTask=null");
		query.setParameter("username", username);
		query.setParameter("status", status);
		@SuppressWarnings("unchecked")
		List<Task> tasks = query.list();
		return tasks;
	}

	@Override
	public List<Task> getTasksForUsername(String username) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session
				.createQuery("from ro.reanad.taskmanager.model.Task as task where task.user.username=:username and task.parentTask=null");
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<Task> tasks = query.list();
		return tasks;
	}
}

