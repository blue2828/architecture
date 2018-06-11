package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import service.IStudentService;
import util.DateUtil;
import remote.vo.*;

import java.util.*;
import javax.annotation.Resource;

@Controller("studentAction")
@Scope("prototype")
public class StudentAction extends ActionSupport {
    private static IStudentService studentService = null;
    private int saveVersion;
    private int sId;
    private String save_examName;

    public String getSave_examName() {
        return save_examName;
    }

    public void setSave_examName(String save_examName) {
        this.save_examName = save_examName;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public JSONObject getJb() {
        return jb;
    }

    public void setJb(JSONObject jb) {
        this.jb = jb;
    }

    private JSONObject jb;

    public int getSaveVersion() {
        return saveVersion;
    }

    public void setSaveVersion(int saveVersion) {
        this.saveVersion = saveVersion;
    }

    public static IStudentService getInstance(){
        if(null == studentService){
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("subscriber-dubbo.xml");
            context.start();
            studentService = context.getBean(IStudentService.class);
        }
        return studentService;
    }
    public String saveSignUp() {
        Map session = ActionContext.getContext().getSession();
        Member member = (Member) session.get("currentMember");
        Student student = member.getStudent();
        int flag = getInstance().saveSignUp(student, save_examName, saveVersion);
        jb = new JSONObject();
        if (flag > 0) {
            jb.put("complete", true);
            if (flag == 1) {
                jb.put("msg", "申请报名成功,请等待管理员审批");
            } else {
                jb.put("msg", "取消报名成功");
            }
        } else {
            jb.put("complete", false);
        }
        return SUCCESS;
    }

    public String mySignUPwithApp() {
        jb = new JSONObject();
        List<Object[]> list = getInstance().mySignUPwithApp(sId);
        Object[] o = list.get(0);
        SignUpStatus s = (SignUpStatus) o[0];
        Student st = s.getStudent();
        Exam e = s.getExam();
        Set<ExamType> ep = e.getExamTypes();
        Iterator<ExamType> it = ep.iterator();
        String examTypeName = "";
        while (it.hasNext()) {
            examTypeName = it.next().getType_examName();
        }
        String examTime = DateUtil.formatDateToStr("yyyy-MM-dd hh:mm:ss", e.getExamBeginTime()) + "至" + DateUtil.formatDateToStr("yyyy-MM-dd hh:mm:ss", e.getExamEndTime());
        jb.put("certificateId", st.getCertificateId());
        jb.put("examTypeName", examTypeName);
        jb.put("stuName", st.getStuName());
        jb.put("stuIDCard", st.getStuIDCard());
        jb.put("examTime", examTime);
        jb.put("examName", e.getExamName());
        return SUCCESS;
    }
}
