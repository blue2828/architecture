package dao;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("baseDao")
public class BaseDao extends HibernateDaoSupport {
    @Resource(name = "sessionFactory")
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
    /*@Resource
    private SessionFactory sessionFactory;*/

   /* public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/

    /*public Session getSession() {
        return this.sessionFactory.openSession();
    }*/

    /*public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BaseDao b = (BaseDao) context.getBean("baseDao");
        Session session = null;
        Transaction tx = null;
        try {
            session = b.getSession();
            tx = session.beginTransaction();
            Member mem=(Member)session.get(Member.class,4);
            Student stu=mem.getStudent();
            //session.save(member);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }*/
}
