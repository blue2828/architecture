package service.imp;

import dao.IExamDao;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import service.IExamService;
import remote.vo.Exam;
import remote.vo.Page;

import java.util.List;

@Service
public class ExamService implements IExamService {
    @Autowired
    @Qualifier("examDao")
    private IExamDao examDao;

    @Override
    public List<Exam> findAllInfo(Page page, Exam exam) {
        List<Exam> list = examDao.findAllInfo(page, exam);
        return list;
    }

    @Override
    public int countInfo(Page page, Exam exam) {
        int i = examDao.countInfo(page, exam);
        return i;
    }

    @Override
    public List getAllType() {
        List list = null;
        try {
            list = examDao.getAllType();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getThisType(int id) {
        return examDao.getThisType(id);
    }

    @Override
    public int saveExam(Exam exam, String examTypeName) {
        int flag = 0;
        try {
            examDao.saveExam(exam, examTypeName);
            flag = 1;
        } catch (HibernateException e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    public int[] deleteExam(String ids) {
        int succflag = 0;
        int failFlag = 0;
        try {
            int arr[] = examDao.delExam(ids);
            if (arr[0] > 0) {
                succflag = 1;
                failFlag = 1;
            } else {
                succflag = 0;
                failFlag = arr[1];
            }
        } catch (HibernateException e) {
            succflag = 0;
            failFlag = 0;
            e.printStackTrace();
        }
        int arr[] = {succflag, failFlag};
        return arr;
    }

    @Override
    public String findExamNameById(int id) {
        return examDao.findExamNameById(id);
    }

    @Override
    public int findExamByName(String examName) {
        int flag = examDao.findExamByName(examName);
        return flag;
    }

    @Override
    public List<Object[]> outPutExams(String ids) {
        return examDao.outPutExams(ids);
    }

}
