package remote.vo;

import java.util.HashSet;
import java.util.Set;

public class ExamType implements java.io.Serializable{
    private int examType_id;

    public int getExamType_id() {
        return examType_id;
    }

    public void setExamType_id(int examType_id) {
        this.examType_id = examType_id;
    }

    private String type_examName;

    public Set<Exam> getExams() {
        return exams;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }

    private Set<Exam> exams = new HashSet<Exam>();

    public ExamType() {
    }

    public ExamType(String type_examName) {
        this.type_examName = type_examName;
    }

    public String getType_examName() {
        return type_examName;
    }

    public ExamType(int examType_id, String type_examName) {
        this.examType_id = examType_id;
        this.type_examName = type_examName;
    }

    public void setType_examName(String type_examName) {
        this.type_examName = type_examName;
    }
}
