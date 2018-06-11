package interceptor;

import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import remote.vo.Member;

public class loginInterceptor extends MethodFilterInterceptor {
    private static final String AJAX_TIME_OUT = "ajaxSessionTimeOut";

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest req = ServletActionContext.getRequest();
        String url = req.getServletPath();
        url = url.substring(url.indexOf("_") + 1);
        if (url.equals("logout")) {
            ServletActionContext.getContext().getSession().remove("currentMember");
            return invocation.invoke();
        }
        String type = req.getHeader("x-requested-with");
        System.out.print(type);
        Member userinfo = (Member) ServletActionContext.getContext().getSession().get("currentMember");
        if (userinfo == null) {
            if ("XMLHttpRequest".equalsIgnoreCase(type)) {
                PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
                printWriter.print(AJAX_TIME_OUT);
                printWriter.flush();
                printWriter.close();
                return null;
            } else {
                return "sessionTimeOut";
            }
        }
        return invocation.invoke();
    }

    @Override
    public void setExcludeMethods(String excludeMethods) {
        super.setExcludeMethods(excludeMethods);
    }

    @Override
    public Set<String> getExcludeMethodsSet() {
        return super.getExcludeMethodsSet();
    }
}