package dao;

import remote.vo.Exam;
import remote.vo.Page;

import java.util.List;

public interface IExamDao {
    public List<Exam> findAllInfo(Page page, Exam exam);

    public int countInfo(Page page, Exam exam);

    public List getAllType();

    public List getThisType(int id);

    public void saveExam(Exam exam, String examTypeName);

    public int[] delExam(String ids);

    public String findExamNameById(int id);

    public int findExamByName(String examName);

    public List<Object[]> outPutExams(String ids);
}
