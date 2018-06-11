package service.imp;

import dao.IAdminOpDao;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import service.IAdminOpService;
import remote.vo.Exam;
import remote.vo.Notice;
import remote.vo.Page;

import java.util.Date;
import java.util.List;

@Service("adminOpService")
public class AdminOpService implements IAdminOpService {
    @Autowired
    @Qualifier("adminOpDao")
    private IAdminOpDao adminOpDao;

    @Override
    public List<Notice> viewAllNotice(Page page, Notice no, String noticeId, Date s_pbBeginIime, Date s_pbEndTime) {
        List<Notice> list = adminOpDao.viewAllNotice(page, no, noticeId, s_pbBeginIime, s_pbEndTime);
        return list;
    }

    @Override
    public int countNotice(Notice no, String noticeId, Date s_pbBeginIime, Date s_pbEndTime) {
        int num = adminOpDao.countNotice(no, noticeId, s_pbBeginIime, s_pbEndTime);
        return num;
    }

    @Override
    public int deleteNoticeById(String ids) {
        int flag = -1;
        try {
            adminOpDao.delNoticeById(ids);
            flag = 1;
        } catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public int saveNotice(Notice notice) {
        int flag = -1;
        try {
            adminOpDao.saveNotice(notice);
            flag = 1;
        } catch (HibernateException e) {
            flag = 0;
            ;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Object[]> allSignUp(Page page, Exam exam, String s_stuName, String s_sId, String isPrint) {
        return adminOpDao.allSignUp(page, exam, s_stuName, s_sId, isPrint);
    }

    @Override
    public int countSignUp(Page page, Exam exam, String s_stuName, String s_sId, String isPrint) {
        return adminOpDao.countSignUp(page, exam, s_stuName, s_sId, isPrint);
    }

    @Override
    public int updateSignUp(String signUpId, int approVersion) {
        int flag = -1;
        try {
            int flag2 = adminOpDao.approSignUp(signUpId, approVersion);
            if (flag2 == 0) flag = 1;
            else if (flag2 == 1) flag = 2;
        } catch (HibernateException e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public void saveCerti(int saveSId, String certificateId) {
        adminOpDao.saveCerti(saveSId, certificateId);
    }

}
