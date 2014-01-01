package ro.reanad.taskmanager.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration
@Transactional
public abstract class AbstractDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}
