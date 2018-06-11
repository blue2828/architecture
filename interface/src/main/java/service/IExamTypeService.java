package service;

import remote.vo.ExamType;
import remote.vo.Page;

import java.util.List;

public interface IExamTypeService {
    public List<ExamType> getAllInfo(Page page, ExamType examType);

    public int countExamType(Page page, ExamType examType);

    public int saveExamType(ExamType examType);

    public int[] deleteExamType(String ids);

    public String findExamNameById(int id);
}
