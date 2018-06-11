package remote.vo;

public class Member implements java.io.Serializable{
    private int memberId;
    private String memName;
    private int level;
    private int isApproved;
    private String memPassword;
    private Admin admin;
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }

    public String getMemPassword() {
        return memPassword;
    }

    public void setMemPassword(String memPassword) {
        this.memPassword = memPassword;
    }

    public Member() {
    }

    public Member(int memberId, String memPassword) {
        this.memberId = memberId;
        this.memPassword = memPassword;
    }

    public Member(String memName, int memberId) {
        this.memName = memName;
        this.memberId = memberId;
    }
}
