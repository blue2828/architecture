package service;

import remote.vo.Member;
import remote.vo.Page;
import remote.vo.Student;

import java.util.List;

public interface IMemberService {
    public List<?> validateMember(Member member);

    public int save_memRegister(Member member);

    public int updateImg(Object obj);

    public String refreshImgHeader(Object obj);

    public List<Member> viewAllReg(Page page, String memName, String memberId);

    public int countReg(String memName, String memberId);

    public int updateRegAppro(Object memberId);

    public int updateLoginRead(Student stu);

    public int updateMemberInfo(Object obj);
}
