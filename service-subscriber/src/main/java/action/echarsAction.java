package action;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import service.IEchartsService;
import java.util.List;

@Controller("echartsAction")
@Scope("prototype")
public class echarsAction extends ActionSupport {
    private JSONObject jb;
    private static IEchartsService echartsService = null;

    public JSONObject getJb() {
        return jb;
    }

    public void setJb(JSONObject jb) {
        this.jb = jb;
    }
    public static IEchartsService getInstance(){
        if(null == echartsService){
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("subscriber-dubbo.xml");
            context.start();
            echartsService = context.getBean(IEchartsService.class);
        }
        return echartsService;
    }
    public String echartsData() {
        jb = new JSONObject();
        JSONArray array = new JSONArray();
        List<String> list = getInstance().getAllExamName();
        for (String examName : list) {
            int count = getInstance().countExamByName(examName);
            JSONObject tempJb = new JSONObject();
            tempJb.put("examName", examName);
            tempJb.put("count", count);
            array.add(tempJb);
        }
        jb.put("data", array);
        return SUCCESS;
    }
}
