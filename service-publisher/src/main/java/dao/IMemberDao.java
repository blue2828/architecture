package dao;

import remote.vo.Member;
import remote.vo.Page;
import remote.vo.Student;

import java.util.List;

public interface IMemberDao {
    public List<?> validateMember(Member member);

    public void memRegister(Member member);

    public void updateImg(Object obj);

    public String refreshImgHeader(Object obj);

    public List<Member> viewAllReg(Page page, String memName, String memberId);

    public int countReg(String memName, String memberId);

    public void updateRegAppro(Object memberId);

    public void editLoginRead(Student stu);

    public void updateMemberInfo(Object obj);
}
