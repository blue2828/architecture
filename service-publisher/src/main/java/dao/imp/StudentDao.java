package dao.imp;

import dao.BaseDao;
import dao.IStudentDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import remote.vo.Exam;
import remote.vo.SignUpStatus;
import remote.vo.Student;

import java.util.List;

@Repository("studentDao")
public class StudentDao extends BaseDao implements IStudentDao {
    @Override
    public int saveSignUp(Student student, String examName, int saveVersion) {
        int flag = -1;
        List<Exam> list = (List<Exam>) getHibernateTemplate().find("from Exam e where e.examName =?", examName.trim());
        Exam exam = list.get(0);
        if (saveVersion == 0) {
            SignUpStatus signUpStatus = new SignUpStatus();
            signUpStatus.setExam(exam);
            signUpStatus.setStudent(student);
            signUpStatus.setAppro_stat(0);
            getHibernateTemplate().merge(signUpStatus);
            flag = 1;
        } else {
            List<Object[]> lists = (List<Object[]>) getHibernateTemplate().find("from SignUpStatus s,Exam e,Student st where s.exam.exam_id =e.exam_id and s.student.stuId=st.stuId and s.student.stuId=? and s.exam.examName=?", student.getStuId(), examName.trim());
            for (Object[] objects : lists) {
                for (Object o : objects) {
                    if (o instanceof SignUpStatus) {
                        ((SignUpStatus) o).getStudent().getSignUpStatus().remove(((SignUpStatus) o));
                        ((SignUpStatus) o).getExam().getSignUpStatuses().remove(((SignUpStatus) o));
                        getHibernateTemplate().delete(((SignUpStatus) o));
                        flag = 2;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public List<Object[]> mySignUPwithApp(int sId) {
        List list = (List) getHibernateTemplate().execute(
                new HibernateCallback<Object>() {
                    public Object doInHibernate(Session session) {
                        String hql = "from SignUpStatus s,Exam e,Student st where s.exam.exam_id =e.exam_id and s.student.stuId=st.stuId and s.sId=?";
                        Query query = session.createQuery(hql);
                        query.setInteger(0, sId);
                        return query.list();
                    }
                }
        );
        return list;
    }
}
