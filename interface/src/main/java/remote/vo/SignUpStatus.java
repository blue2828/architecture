package remote.vo;

public class SignUpStatus implements java.io.Serializable{
    private int sId;
    private Student student;
    private Exam exam;
    private int appro_stat;

    public int getAppro_stat() {
        return appro_stat;
    }

    public void setAppro_stat(int appro_stat) {
        this.appro_stat = appro_stat;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }


}
