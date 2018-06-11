package service;

import java.util.List;

public interface IEchartsService {
    public List<String> getAllExamName();

    public int countExamByName(String examName);
}
