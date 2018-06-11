package dao.imp;

import dao.BaseDao;
import dao.IEchartsDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("echartsDao")
public class echartsDao extends BaseDao implements IEchartsDao {
    @Override
    public List<String> getAllExamName() {
        List<String> list = (List<String>) getHibernateTemplate().find("select e.examName from Exam e");
        return list;
    }

    @Override
    public int countExamByName(String examName) {
        int num = Integer.parseInt(getHibernateTemplate().find("select count(*) from SignUpStatus s,Exam e where e.examName=? and s.exam.exam_id=e.exam_id", examName).get(0).toString());
        return num;
    }
}
