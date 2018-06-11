package remote.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Exam implements java.io.Serializable{
    private int exam_id;
    private String examName;
    private Set<ExamType> examTypes = new HashSet<ExamType>();
    private Date examBeginTime;
    private Date examEndTime;
    private Set<SignUpStatus> signUpStatuses = new HashSet<SignUpStatus>();

    public Set<SignUpStatus> getSignUpStatuses() {
        return signUpStatuses;
    }

    public void setSignUpStatuses(Set<SignUpStatus> signUpStatuses) {
        this.signUpStatuses = signUpStatuses;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public Date getExamBeginTime() {
        return examBeginTime;
    }

    public void setExamBeginTime(Date examBeginTime) {
        this.examBeginTime = examBeginTime;
    }

    public Date getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(Date examEndTime) {
        this.examEndTime = examEndTime;
    }

    public Set<ExamType> getExamTypes() {
        return examTypes;
    }

    public void setExamTypes(Set<ExamType> examTypes) {
        this.examTypes = examTypes;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Exam(String examName, Date examBeginTime, Date examEndTime) {
        this.examName = examName;
        this.examBeginTime = examBeginTime;
        this.examEndTime = examEndTime;
    }

    public Exam(int exam_id, String examName, Date examBeginTime, Date examEndTime) {
        this.exam_id = exam_id;
        this.examName = examName;
        this.examBeginTime = examBeginTime;
        this.examEndTime = examEndTime;
    }

    public Exam(int exam_id) {
        this.exam_id = exam_id;
    }

    public Exam() {
    }
}
