package service.imp;

import dao.IStudentDao;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import service.IStudentService;
import remote.vo.Student;

import java.util.List;

@Service("studentService")
public class StudentService implements IStudentService {
    @Autowired
    @Qualifier("studentDao")
    private IStudentDao studentDao;

    @Override
    public int saveSignUp(Student student, String examName, int saveVersion) {
        int flag = 0;
        try {
            int flag2 = studentDao.saveSignUp(student, examName, saveVersion);
            if (flag2 == 1) flag = 1;
            else if (flag2 == 2) flag = 2;
            else flag = -1;
        } catch (HibernateException e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Object[]> mySignUPwithApp(int sId) {
        return studentDao.mySignUPwithApp(sId);
    }
}
