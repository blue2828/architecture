package dao.imp;

import dao.BaseDao;
import dao.IExamDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import util.StringUtil;
import remote.vo.Exam;
import remote.vo.ExamType;
import remote.vo.Page;
import remote.vo.SignUpStatus;

import java.util.*;

@Repository("examDao")
public class ExamDao extends BaseDao implements IExamDao {
    @Override
    public List<Exam> findAllInfo(Page page, Exam exam) {
        List<Exam> list = (List<Exam>) getHibernateTemplate().execute(
                new HibernateCallback<Object>() {
                    public Object doInHibernate(Session session) {
                        StringBuffer hql = new StringBuffer("from Exam e where 1=1 ");
                        if (exam.getExam_id() != 0) {
                            hql.append(" and e.exam_id like '%" + exam.getExam_id() + "%'");
                        }
                        if (StringUtil.isNotEmpty(exam.getExamName())) {
                            hql.append(" and e.examName like :examName");
                        }
                        if (null != exam.getExamBeginTime()) {
                            hql.append(" and to_days(e.examBeginTime) >= to_days(:examBeginTime) ");
                        }
                        if (null != exam.getExamEndTime()) {
                            hql.append(" and to_days(e.examEndTime) <= to_days(:examEndTime)");
                        }
                        Query query = session.createQuery(hql.toString());
                        if (StringUtil.isNotEmpty(exam.getExamName())) {
                            query.setString("examName", "%" + exam.getExamName() + "%");
                        }
                        if (null != exam.getExamBeginTime()) {
                            query.setDate("examBeginTime", exam.getExamBeginTime());
                        }
                        if (null != exam.getExamEndTime()) {
                            query.setDate("examEndTime", exam.getExamEndTime());
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
    public int countInfo(Page page, Exam exam) {
        StringBuffer hql = new StringBuffer("select count(*) from Exam e where 1=1 ");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        if (exam.getExam_id() != 0) {
            hql.append(" and e.exam_id like '%" + exam.getExam_id() + "%'");
        }
        if (StringUtil.isNotEmpty(exam.getExamName())) {
            hql.append(" and e.examName like :examName");
        }
        if (null != exam.getExamBeginTime()) {
            hql.append(" and to_days(e.examBeginTime) >= to_days(:examBeginTime) ");
        }
        if (null != exam.getExamEndTime()) {
            hql.append(" and to_days(e.examEndTime) <= to_days(:examEndTime)");
        }
        Query query = session.createQuery(hql.toString());
        if (StringUtil.isNotEmpty(exam.getExamName())) {
            query.setString("examName", "%" + exam.getExamName() + "%");
        }
        if (null != exam.getExamBeginTime()) {
            query.setDate("examBeginTime", exam.getExamBeginTime());
        }
        if (null != exam.getExamEndTime()) {
            query.setDate("examEndTime", exam.getExamEndTime());
        }
        return Integer.parseInt(query.list().get(0).toString());
    }

    @Override
    public List getAllType() {
        return getHibernateTemplate().find("select e.type_examName from ExamType e");
    }

    @Override
    public List getThisType(int id) {
        Exam e = getHibernateTemplate().get(Exam.class, id);
        Set<ExamType> h = e.getExamTypes();
        Iterator<ExamType> it = h.iterator();
        ExamType et = null;
        while (it.hasNext()) {
            et = it.next();
        }
        int thisId = et.getExamType_id();
        return getHibernateTemplate().find("select e.type_examName from ExamType e where e.examType_id=?", thisId);
    }

    @Override
    public void saveExam(Exam exam, String examTypeName) {
        exam.setExamTypes(new HashSet<ExamType>());
        exam.getExamTypes().add((ExamType) getHibernateTemplate().find("from ExamType e where e.type_examName=?", examTypeName).get(0));
        if (exam.getExam_id() != 0) {
            getHibernateTemplate().merge(exam);
        } else getHibernateTemplate().save(exam);
    }

    @Override
    public int[] delExam(String ids) {
        String[] id = ids.split(",");
        int flag1 = 0;
        int flag2 = 0;
        for (String strId : id) {
            Exam e = getHibernateTemplate().get(Exam.class, Integer.parseInt(strId));
            Set<SignUpStatus> sgs = e.getSignUpStatuses();
            if (sgs.size() > 0) {
                flag1 = 0;
                flag2 = Integer.parseInt(strId);
                break;
            } else {
                getHibernateTemplate().delete(e);
                flag1 = 1;
                flag2 += 1;
            }
        }
        int arr[] = {flag1, flag2};
        return arr;
    }

    @Override
    public String findExamNameById(int id) {
        Exam e = getHibernateTemplate().get(Exam.class, id);
        return e.getExamName();
    }

    public int findExamByName(String examName) {
        List exam = getHibernateTemplate().find("from Exam where examName =?", examName);
        int flag = -1;
        if (exam.size() > 0) flag = 1;
        else flag = 0;
        return flag;
    }

    public List<Object[]> outPutExams(String ids) {
        List<Object[]> list = (List<Object[]>) getHibernateTemplate().find("from SignUpStatus s,Student st,Exam e where s.exam.exam_id=e.exam_id and st.stuId=s.student.stuId and s.sId in (" + ids + ")");
        return list;
    }
}
