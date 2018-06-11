package dao;

import remote.vo.ExamType;
import remote.vo.Page;

import java.util.List;

public interface IExamTypeDao {
    public List<ExamType> getAllInfo(Page page, ExamType examType);

    public int countExamType(Page page, ExamType examType);

    public void saveExamType(ExamType examType);

    public int[] delExamType(String ids);

    public String findExamNameById(int id);
}
