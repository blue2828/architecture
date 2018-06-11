package dao;

import remote.vo.Student;

import java.util.List;

public interface IStudentDao {
    public int saveSignUp(Student student, String examId, int saveVersion);

    public List<Object[]> mySignUPwithApp(int sId);
}
