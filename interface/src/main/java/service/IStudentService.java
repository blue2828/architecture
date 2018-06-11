package service;

import remote.vo.Student;

import java.util.List;

public interface IStudentService {
    public int saveSignUp(Student student, String examName, int saveVersion);

    public List<Object[]> mySignUPwithApp(int sId);
}
