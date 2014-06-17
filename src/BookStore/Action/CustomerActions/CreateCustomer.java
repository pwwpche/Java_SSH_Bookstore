package BookStore.Action.CustomerActions;

import BookStore.Entity.CustomerEntity;
import BookStore.Service.Interface.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pwwpche on 2014/6/8.
 */
public class CreateCustomer extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    @Resource
    CustomerService customerService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletResponse getResponse(){
        return  this.response;
    }

    public HttpServletRequest getRequest(){
        return  this.request;
    }

    public String execute(){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUsername(request.getParameter("username"));
        customerEntity.setPassword(request.getParameter("password"));
        customerEntity.setEmail(request.getParameter("email"));
        customerEntity.setBirthday(Date.valueOf(request.getParameter("birthday").replace("T00:00:00","")));
        customerService.addCustomer(customerEntity);
        dataMap.put("success", true);
        return SUCCESS;
    }
    private Map<String, Object> dataMap;


    public CreateCustomer() {
        dataMap = new HashMap<String, Object>();
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }
}
