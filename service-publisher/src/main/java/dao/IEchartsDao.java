package dao;

import java.util.List;

public interface IEchartsDao {
    public List<String> getAllExamName();

    public int countExamByName(String examName);
}
