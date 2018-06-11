package remote.vo;

import java.util.HashSet;
import java.util.Set;

public class Student implements java.io.Serializable{
    private int stuId;
    private int stuSex;
    private String stuPhone;
    private String stuIDCard;
    //private int memberId;
    private int stuNo;
    private String stuName;
    private String stuAddress;
    private String stuImg;
    private Member member;
    private int loginRead;
    private int editInfoRead;
    private String certificateId;

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    private Set<SignUpStatus> signUpStatus = new HashSet<SignUpStatus>();

    public Set<SignUpStatus> getSignUpStatus() {
        return signUpStatus;
    }

    public void setSignUpStatus(Set<SignUpStatus> signUpStatus) {
        this.signUpStatus = signUpStatus;
    }

    public int getLoginRead() {
        return loginRead;
    }

    public void setLoginRead(int loginRead) {
        this.loginRead = loginRead;
    }

    public int getEditInfoRead() {
        return editInfoRead;
    }

    public void setEditInfoRead(int editInfoRead) {
        this.editInfoRead = editInfoRead;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public int getStuSex() {
        return stuSex;
    }

    public void setStuSex(int stuSex) {
        this.stuSex = stuSex;
    }

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public String getStuIDCard() {
        return stuIDCard;
    }

    public void setStuIDCard(String stuIDCard) {
        this.stuIDCard = stuIDCard;
    }

    public int getStuNo() {
        return stuNo;
    }

    public void setStuNo(int stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuAddress() {
        return stuAddress;
    }

    public void setStuAddress(String stuAddress) {
        this.stuAddress = stuAddress;
    }

    public String getStuImg() {
        return stuImg;
    }

    public void setStuImg(String stuImg) {
        this.stuImg = stuImg;
    }

    public Student() {
    }
}
