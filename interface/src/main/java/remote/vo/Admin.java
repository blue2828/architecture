package remote.vo;

import java.util.Set;
public class Admin implements java.io.Serializable{
    private int adminId;
    private int adminSex;
    private String adminPhone;
    private String adminIDCard;
    private int adminNo;
    private String adminName;
    private String adminAddress;
    private String adminImg;
    private Member member;
    private Set notices;

    public Set getNotices() {
        return notices;
    }

    public void setNotices(Set notices) {
        this.notices = notices;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getAdminSex() {
        return adminSex;
    }

    public void setAdminSex(int adminSex) {
        this.adminSex = adminSex;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminIDCard() {
        return adminIDCard;
    }

    public void setAdminIDCard(String adminIDCard) {
        this.adminIDCard = adminIDCard;
    }

    /*public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }*/

    public int getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(int adminNo) {
        this.adminNo = adminNo;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminAddress() {
        return adminAddress;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public String getAdminImg() {
        return adminImg;
    }

    public void setAdminImg(String adminImg) {
        this.adminImg = adminImg;
    }

    public Admin() {
    }
}
