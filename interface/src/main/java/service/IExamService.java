package service;

import remote.vo.Exam;
import remote.vo.Page;

import java.util.List;

public interface IExamService {
    public List<Exam> findAllInfo(Page page, Exam exam);

    public int countInfo(Page page, Exam exam);

    public List getAllType();

    public List getThisType(int id);

    public int saveExam(Exam exam, String examTypeName);

    public int[] deleteExam(String ids);

    public String findExamNameById(int id);

    public int findExamByName(String examName);

    public List<Object[]> outPutExams(String ids);
}
