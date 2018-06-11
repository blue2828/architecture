package action;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import service.IExamTypeService;
import util.StringUtil;
import remote.vo.ExamType;
import remote.vo.Page;

import java.util.List;
import javax.annotation.Resource;

@Controller("examTypeAction")
@Scope("prototype")
public class examTypeAction extends ActionSupport {
    private int page, limit;
    private ExamType examType;
    private String editId;
    private String examType_id, type_examName;

    public String getExamType_id() {
        return examType_id;
    }

    public void setExamType_id(String examType_id) {
        this.examType_id = examType_id;
    }

    public String getType_examName() {
        return type_examName;
    }

    public void setType_examName(String type_examName) {
        this.type_examName = type_examName;
    }

    public String getDelId() {
        return delId;
    }

    public void setDelId(String delId) {
        this.delId = delId;
    }

    private String delId;

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }

    private String editName;

    public String getEditId() {
        return editId;
    }

    public void setEditId(String editId) {
        this.editId = editId;
    }

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
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

    private JSONObject jb;
    private static IExamTypeService examTypeService = null;

    public JSONObject getJb() {
        return jb;
    }

    public void setJb(JSONObject jb) {
        this.jb = jb;
    }
    public static IExamTypeService getInstance(){
        if(null == examTypeService){
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("subscriber-dubbo.xml");
            context.start();
            examTypeService = context.getBean(IExamTypeService.class);
        }
        return examTypeService;
    }
    public String getAllInfo() {
        Page p = new Page(page, limit);
        jb = new JSONObject();
        if (StringUtil.isNotEmpty(examType_id)) {
            examType_id = examType_id.trim();
            examType = new ExamType(Integer.parseInt(examType_id), type_examName);
        } else examType = new ExamType(type_examName);
        List<ExamType> list = getInstance().getAllInfo(p, examType);
        JSONArray jr = new JSONArray();
        for (ExamType et : list) {
            JSONObject temp = new JSONObject();
            temp.put("examType_id", et.getExamType_id());
            temp.put("type_examName", et.getType_examName());
            jr.add(temp);
        }
        jb.put("code", 0);
        jb.put("msg", "");
        jb.put("count", getInstance().countExamType(p, examType));
        jb.put("data", jr);
        return SUCCESS;
    }

    public String saveExamType() {
        jb = new JSONObject();
        ExamType et = null;
        if (StringUtil.isNotEmpty(editId)) {
            et = new ExamType(Integer.parseInt(editId), editName);
        } else {
            et = new ExamType();
            et.setType_examName(examType.getType_examName());
        }
        int flag = getInstance().saveExamType(et);
        if (flag > 0) {
            jb.put("complete", true);
        } else jb.put("complete", false);
        return SUCCESS;
    }

    public String delExamType() {
        jb = new JSONObject();
        int flag[] = getInstance().deleteExamType(delId);
        if (flag[0] > 0) jb.put("complete", true);
        else {
            if (flag[1] == 0) {
                jb.put("complete", "服务器错误，请联系管理员");
            } else {
                String type_examName = getInstance().findExamNameById(flag[1]);
                jb.put("complete", false);
                jb.put("errMsg", "考试类别-" + type_examName + " 下有考试,请先删除该类别下考试或者将这些考试的类别改成其他类别再进行操作");
            }
        }
        return SUCCESS;
    }
}
