package BookStore.Action.LoginActions;

import BookStore.Service.Interface.LoginService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by pwwpche on 2014/6/7.
 *
 */
@Controller
public class LoginAction extends ActionSupport implements ServletRequestAware{
    @Resource
    LoginService loginService;

    HttpServletRequest request;
    String username;
    String password;

    public void setUsername(String name){
        this.username = name;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String pwd){
        this.password = pwd;
    }

    public String getPassword(){
        return this.password;
    }

    public static int count = 0;

    @Override
    public String execute(){
        Map<String, Object> userSession = ActionContext.getContext().getSession();
        HttpSession session = request.getSession();
        if(userSession.containsKey("done"))
        {
            System.out.println("done logged");
            userSession.put("done", count++);
        }
        else
        {
            userSession.put("done", "null");
        }


        String res = loginService.verify(username, password);
        if(res.equals("success"))
        {
            System.out.print("success");
            //userSession.put("username", username);
            session.setAttribute("username", username);
            if(!username.equals("admin")) {
                //userSession.put("role", "user");
                session.setAttribute("role", "user");
                return "user";
            }else{
                //userSession.put("role", "admin");
                session.setAttribute("role", "admin");
                return "admin";
            }

        }
        else
        {
            System.out.print("input");
            return "input";
        }
    }


    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
