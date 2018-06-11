package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import service.IExamService;
import util.DateUtil;
import util.StringUtil;
import remote.vo.*;

import java.util.*;
import javax.annotation.Resource;

@Controller("examAction")
@Scope("prototype")
public class ExamAction extends ActionSupport {
    private static IExamService examService = null;
    private JSONObject jb;
    private int page;
    private int limit;
    private Exam exam;
    private String s_exam_id;
    private String s_examName;
    private String delId;

    public String getDelId() {
        return delId;
    }

    public void setDelId(String delId) {
        this.delId = delId;
    }

    private Date s_examBeginTime;
    private int getTypeId;

    public int getGetTypeId() {
        return getTypeId;
    }

    public void setGetTypeId(int getTypeId) {
        this.getTypeId = getTypeId;
    }

    public String getS_examName() {
        return s_examName;
    }

    public void setS_examName(String s_examName) {
        this.s_examName = s_examName;
    }

    public Date getS_examBeginTime() {
        return s_examBeginTime;
    }

    public void setS_examBeginTime(Date s_examBeginTime) {
        this.s_examBeginTime = s_examBeginTime;
    }

    public Date getS_examEndTime() {
        return s_examEndTime;
    }

    public void setS_examEndTime(Date s_examEndTime) {
        this.s_examEndTime = s_examEndTime;
    }

    private Date s_examEndTime;
    private String examTypeName;

    public String getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }

    public String getS_exam_id() {
        return s_exam_id;
    }

    public void setS_exam_id(String s_exam_id) {
        this.s_exam_id = s_exam_id;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public JSONObject getJb() {
        return jb;
    }

    public void setJb(JSONObject jb) {
        this.jb = jb;
    }
     
    public static IExamService getInstance(){
        if(null == examService){
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("subscriber-dubbo.xml");
            context.start();
            examService = context.getBean(IExamService.class);
        }
        return examService;
    }
    
    public String getAllInfo() {
        jb = new JSONObject();
        JSONArray a = new JSONArray();
        Page p = new Page(page, limit);
        Exam e = null;
        if (StringUtil.isNotEmpty(s_exam_id)) {
            s_exam_id = s_exam_id.trim();
            e = new Exam(Integer.parseInt(s_exam_id), s_examName, s_examBeginTime, s_examEndTime);
        } else e = new Exam(s_examName, s_examBeginTime, s_examEndTime);
        List<Exam> list = getInstance().findAllInfo(p, e);
        Map session = ActionContext.getContext().getSession();
        Member member = (Member) session.get("currentMember");
        Student student = member.getStudent();
        for (Exam ex : list) {
            JSONObject tempJb = new JSONObject();
            Set<SignUpStatus> sgs = ex.getSignUpStatuses();
            for (SignUpStatus sgss : sgs) {
                Student stu = sgss.getStudent();
                if (student != null) {
                    if (student.getStuId() == stu.getStuId()) {
                        tempJb.put("examId", 1);
                    }
                }
            }
            tempJb.put("exam_id", ex.getExam_id());
            tempJb.put("examName", ex.getExamName());
            tempJb.put("examBeginTime", DateUtil.formatDateToStr("yyyy-MM-dd hh:mm:ss", ex.getExamBeginTime()));
            tempJb.put("examEndTime", DateUtil.formatDateToStr("yyyy-MM-dd hh:mm:ss", ex.getExamEndTime()));
            Iterator<ExamType> it = ex.getExamTypes().iterator();
            StringBuffer sb = new StringBuffer("");
            while (it.hasNext()) {
                sb.append(it.next().getType_examName() + " ");
            }
            tempJb.put("examTypeName", sb.toString());
            a.add(tempJb);
        }
        jb.put("data", a);
        jb.put("count", getInstance().countInfo(p, e));
        jb.put("code", 0);
        jb.put("msg", "");
        return SUCCESS;
    }

    public String getAllType() {
        jb = new JSONObject();
        List<String> list = getInstance().getAllType();
        JSONArray ja = new JSONArray();
        for (String examTypeName : list) {
            JSONObject tempJb = new JSONObject();
            tempJb.put("type_examName", examTypeName);
            ja.add(tempJb);
        }
        jb.put("typeRows", ja);
        return SUCCESS;
    }

    public String getThisType() {
        jb = new JSONObject();
        String examType = (String) getInstance().getThisType(getTypeId).get(0);
        jb.put("type", examType);
        return SUCCESS;
    }

    public String saveExam() {
        jb = new JSONObject();
        int isHavedName = getInstance().findExamByName(exam.getExamName());
        if (isHavedName == 1) {
            jb.put("success", false);
            jb.put("errMsg", "考试名已存在");
            return SUCCESS;
        }
        int flag = getInstance().saveExam(exam, examTypeName);
        if (flag > 0) {
            jb.put("success", true);
        } else {
            jb.put("success", false);
            jb.put("errMsg", "保存失败");
        }
        return SUCCESS;
    }

    public String delExam() {
        jb = new JSONObject();
        int[] flag = getInstance().deleteExam(delId);
        if (flag[0] > 0) {
            jb.put("complete", true);
        } else {
            if (flag[1] == 0) {
                jb.put("complete", false);
                jb.put("errMsg", "服务器错误，请联系管理员");
            } else {
                jb.put("complete", false);
                jb.put("errMsg", "考试-" + getInstance().findExamNameById(flag[1]) + "下有考生选取该考试，不得删除");
            }
        }
        return SUCCESS;
    }
}
