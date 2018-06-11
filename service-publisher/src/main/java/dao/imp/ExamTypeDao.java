package dao.imp;

import dao.BaseDao;
import dao.IExamTypeDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import util.StringUtil;
import remote.vo.ExamType;
import remote.vo.Page;

import java.util.List;

@Repository("examTypeDao")
public class ExamTypeDao extends BaseDao implements IExamTypeDao {
    @Override
    public List<ExamType> getAllInfo(Page page, ExamType examType) {
        List<ExamType> list = (List<ExamType>) getHibernateTemplate().execute(
                new HibernateCallback<Object>() {
                    public Object doInHibernate(Session session) {
                        StringBuffer hql = new StringBuffer(" from ExamType et where 1=1 ");
                        if (examType.getExamType_id() != 0) {
                            hql.append(" and et.examType_id like :id");
                        }
                        if (StringUtil.isNotEmpty(examType.getType_examName())) {
                            hql.append(" and et.type_examName like :name");
                        }
                        Query query = session.createQuery(hql.toString());
                        if (examType.getExamType_id() != 0) {
                            query.setString("id", "%" + examType.getExamType_id() + "%");
                        }
                        if (StringUtil.isNotEmpty(examType.getType_examName())) {
                            query.setString("name", "%" + examType.getType_examName() + "%");
                        }
                        query.setFirstResult(page.getStart());
                        query.setMaxResults(page.getLimit());
                        return query.list();
                    }
                }
        );
        return list;
    }

    @Override
    public int countExamType(Page page, ExamType examType) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        StringBuffer hql = new StringBuffer("select count(*) from ExamType et where 1=1 ");
        if (examType.getExamType_id() != 0) {
            hql.append(" and et.examType_id like :id");
        }
        if (StringUtil.isNotEmpty(examType.getType_examName())) {
            hql.append(" and et.type_examName like :name");
        }
        Query query = session.createQuery(hql.toString());
        if (examType.getExamType_id() != 0) {
            query.setString("id", "%" + examType.getExamType_id() + "%");
        }
        if (StringUtil.isNotEmpty(examType.getType_examName())) {
            query.setString("name", "%" + examType.getType_examName() + "%");
        }
        List list = query.list();
        return Integer.parseInt(list.get(0).toString());
    }

    @Override
    public void saveExamType(ExamType examType) {
        if (examType.getExamType_id() != 0) {
            getHibernateTemplate().merge(examType);
        } else {
            getHibernateTemplate().save(examType);
        }
    }

    @Override
    public int[] delExamType(String ids) {
        int flagId = 0;
        int flag = 0;
        String[] strIds = ids.split(",");
        for (String id : strIds) {
            ExamType et = getHibernateTemplate().get(ExamType.class, Integer.parseInt(id));
            if (et.getExams().size() > 0) {
                flagId = Integer.parseInt(id);
                flag = 0;
                break;
            } else {
                getHibernateTemplate().delete(et);
                flag += 1;
            }
        }
        int[] flagArr = {flag, flagId};
        return flagArr;
    }

    @Override
    public String findExamNameById(int id) {
        String examTypeName = (String) getHibernateTemplate().find("select et.type_examName from ExamType et where et.examType_id =? ", id).get(0);
        return examTypeName;
    }
}
