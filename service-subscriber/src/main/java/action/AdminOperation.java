package action;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import service.IAdminOpService;
import util.DateUtil;
import util.EnumTypeFormat;
import util.StringUtil;
import remote.vo.*;
import java.util.List;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

@Controller("adminOperation")
@Scope("prototype")
public class AdminOperation extends ActionSupport {
    private int page, limit;
    private String s_noticeId, s_noticeTitle, s_adminName;
    private Date s_pbBeginTime;
    private Notice notice;
    private String updateId;
    private String isPrint;

    public String getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(String isPrint) {
        this.isPrint = isPrint;
    }

    public String getS_sId() {
        return s_sId;
    }

    public void setS_sId(String s_sId) {
        this.s_sId = s_sId;
    }

    private String s_sId;
    private String s_examName;

    public String getS_stuName() {
        return s_stuName;
    }

    public void setS_stuName(String s_stuName) {
        this.s_stuName = s_stuName;
    }

    private String s_stuName;
    private Date s_examBeginTime, s_examEndTime;
    private String signUpId;
    private int approVersion;
    private int saveSId;
    private String certificateId;

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public int getSaveSId() {
        return saveSId;
    }

    public void setSaveSId(int saveSId) {
        this.saveSId = saveSId;
    }

    public String getSignUpId() {
        return signUpId;
    }

    public void setSignUpId(String signUpId) {
        this.signUpId = signUpId;
    }

    public int getApproVersion() {
        return approVersion;
    }

    public void setApproVersion(int approVersion) {
        this.approVersion = approVersion;
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

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public Date getS_pbEndTime() {
        return s_pbEndTime;
    }

    public void setS_pbEndTime(Date s_pbEndTime) {
        this.s_pbEndTime = s_pbEndTime;
    }

    private Date s_pbEndTime;
    private static IAdminOpService adminOpService = null;
    private JSONObject jb;
    private String del_noticeId;

    public String getDel_noticeId() {
        return del_noticeId;
    }

    public void setDel_noticeId(String del_noticeId) {
        this.del_noticeId = del_noticeId;
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

    public String getS_noticeId() {
        return s_noticeId;
    }

    public void setS_noticeId(String s_noticeId) {
        this.s_noticeId = s_noticeId;
    }

    public String getS_noticeTitle() {
        return s_noticeTitle;
    }

    public void setS_noticeTitle(String s_noticeTitle) {
        this.s_noticeTitle = s_noticeTitle;
    }

    public String getS_adminName() {
        return s_adminName;
    }

    public void setS_adminName(String s_adminName) {
        this.s_adminName = s_adminName;
    }

    public Date getS_pbBeginTime() {
        return s_pbBeginTime;
    }

    public void setS_pbBeginTime(Date s_pbBeginTime) {
        this.s_pbBeginTime = s_pbBeginTime;
    }

    public JSONObject getJb() {
        return jb;
    }

    public void setJb(JSONObject jb) {
        this.jb = jb;
    }
    public static IAdminOpService getInstance(){
        if(null == adminOpService){
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("subscriber-dubbo.xml");
            context.start();
            adminOpService = context.getBean(IAdminOpService.class); 
        }
        return adminOpService;
    }
    public String viewAllNotice() {
        Notice no = new Notice();
        Admin ad = new Admin();
        ad.setAdminName(s_adminName);
        no.setAdmin(ad);
        no.setNoticeTitle(s_noticeTitle);
        Page p = new Page(page, limit);
        if (StringUtil.isNotEmpty(s_noticeId)) {
            s_noticeId = s_noticeId.trim();
        }
        List<Notice> list = getInstance().viewAllNotice(p, no, s_noticeId, s_pbBeginTime, s_pbEndTime);
        JSONArray array = new JSONArray();
        for (Notice notice : list) {
            JSONObject temp = new JSONObject();
            temp.put("noticeId", notice.getNoticeId());
            temp.put("noticeTitle", notice.getNoticeTitle());
            temp.put("noticeTime", DateUtil.formatDateToStr("yyyy-MM-dd hh:mm:ss", notice.getNoticeTime()));
            temp.put("noticeContent", notice.getNoticeContent());
            if (null != notice.getAdmin())
                temp.put("adminName", notice.getAdmin().getAdminName());
            array.add(temp);
        }
        jb = new JSONObject();
        jb.put("data", array);
        jb.put("count", getInstance().countNotice(no, s_noticeId, s_pbBeginTime, s_pbEndTime));
        jb.put("code", 0);
        jb.put("msg", "");
        return SUCCESS;
    }

    public String delNoticeById() {
        jb = new JSONObject();
        int flag = getInstance().deleteNoticeById(del_noticeId);
        if (flag > 0) {
            jb.put("success", true);
        } else {
            jb.put("success", false);
            jb.put("errMsg", "删除失败");
        }
        return SUCCESS;
    }

    public String saveNotce() {
        jb = new JSONObject();
        Member mb = (Member) ServletActionContext.getContext().getSession().get("currentMember");
        Admin ad = mb.getAdmin();
        if (StringUtil.isNotEmpty(updateId)) {
            notice.setNoticeId(Integer.parseInt(updateId));
        } else {
            notice.setAdmin(ad);
        }
        int flag = getInstance().saveNotice(notice);
        if (flag > 0) {
            jb.put("success", true);
        } else {
            jb.put("success", false);
            jb.put("errMsg", "保存失败");
        }
        return SUCCESS;
    }

    public String allSignUp() {
        jb = new JSONObject();
        Exam exam = null;
        exam = new Exam(s_examName, s_examBeginTime, s_examEndTime);
        List<Object[]> list = getInstance().allSignUp(new Page(page, limit), exam, s_stuName, s_sId, isPrint);
        JSONArray jsonArray = new JSONArray();
        for (Object[] o : list) {
            for (Object o2 : o) {
                if (o2 instanceof SignUpStatus) {
                    JSONObject temp = new JSONObject();
                    Student stu = ((SignUpStatus) o2).getStudent();
                    Exam e = ((SignUpStatus) o2).getExam();
                    temp.put("stuName", stu.getStuName());
                    temp.put("examName", e.getExamName());
                    temp.put("examBeginTime", DateUtil.formatDateToStr("yyyy-MM-dd hh:mm:ss", e.getExamBeginTime()));
                    temp.put("examEndTime", DateUtil.formatDateToStr("yyyy-MM-dd hh:mm:ss", e.getExamEndTime()));
                    Set<ExamType> et = e.getExamTypes();
                    for (ExamType ett : et) {
                        temp.put("examTypeName", ett.getType_examName());
                    }
                    temp.put("appro_stat", EnumTypeFormat.formatApproToStr(((SignUpStatus) o2).getAppro_stat()));
                    temp.put("sId", ((SignUpStatus) o2).getsId());
                    jsonArray.add(temp);
                }

            }
        }
        jb.put("data", jsonArray);
        jb.put("code", 0);
        jb.put("msg", "");
        jb.put("count", getInstance().countSignUp(new Page(page, limit), exam, s_stuName, s_sId, isPrint));
        return SUCCESS;
    }

    public String approSignUp() {
        jb = new JSONObject();
        int flag = getInstance().updateSignUp(signUpId, approVersion);
        if (flag > 0) {
            if (flag == 1) {
                jb.put("complete", true);
                jb.put("msg", "审批成功");
            } else {
                jb.put("complete", true);
                jb.put("msg", "取消审批成功");
            }
        } else {
            jb.put("complete", false);
        }
        return SUCCESS;
    }

    public String saveCerti() {
        getInstance().saveCerti(saveSId, certificateId);
        return SUCCESS;
    }
}
