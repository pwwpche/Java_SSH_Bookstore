package BookStore.Interceptor;

import java.util.Map;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class LoginInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String name = invocation.getInvocationContext().getName();

        if (name.contains("loginAction") || name.contains("register") || name.contains("Cart") || name.contains("AllBooks")) {
            return invocation.invoke();
        } else {
            //Get User Session
            ActionContext ac = invocation.getInvocationContext();
            Map session =    (Map)ac.get(ServletActionContext.SESSION);
            if (session == null) {
                return "login";
            } else {
                String username = (String)session.get("username");
                if (username == null) {
                    return "login";
                } else {
                    return invocation.invoke();
                }
            }
        }
    }
}
 