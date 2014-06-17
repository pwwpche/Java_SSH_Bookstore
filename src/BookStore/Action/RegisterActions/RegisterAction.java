package BookStore.Action.RegisterActions;

import BookStore.Entity.CustomerEntity;
import BookStore.Service.Interface.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.DateFormatter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pwwpche on 2014/6/12.
 */

public class RegisterAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    @Resource
    CustomerService customerService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> dataMap;

    public RegisterAction() {
        dataMap = new HashMap<String, Object>();
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public String checkUserExistance(){
        dataMap.clear();
        String username = request.getParameter("username");
        if(customerService.getCustomer(username) != null){
            dataMap.put("status", false);
            dataMap.put("message", "User Exists");
        }
        else{
            dataMap.put("success", true);
        }
        return "success";
    }

    public String registerUser(){
        dataMap.clear();
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUsername(request.getParameter("username"));
        customerEntity.setPassword(request.getParameter("password"));
        customerEntity.setEmail(request.getParameter("email"));
        String birthday = request.getParameter("birthday");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date parsed = format.parse(birthday);
            java.sql.Date sql = new java.sql.Date(parsed.getTime());
            customerEntity.setBirthday(sql);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        String result = customerService.addCustomer(customerEntity);
        if(result.equals("success")) {
            dataMap.put("success",true );
        }else{
            dataMap.put("success", false);
            dataMap.put("message", result);
        }
        return "success";
    }

}
