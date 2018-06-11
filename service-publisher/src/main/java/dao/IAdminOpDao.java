package dao;

import remote.vo.Exam;
import remote.vo.Notice;
import remote.vo.Page;

import java.util.Date;
import java.util.List;

public interface IAdminOpDao {
    public List viewAllNotice(Page page, Notice no, String noticeId, Date s_pbBeginIime, Date s_pbEndTime);

    public int countNotice(Notice no, String noticeId, Date s_pbBeginIime, Date s_pbEndTime);

    public void delNoticeById(String ids);

    public void saveNotice(Notice notice);

    public List<Object[]> allSignUp(Page page, Exam exam, String s_stuName, String s_sId, String isPrint);

    public int countSignUp(Page page, Exam exam, String s_stuName, String s_sId, String isPrint);

    public int approSignUp(String signUpId, int approVersion);

    public void saveCerti(int saveSId, String certificateId);
}
