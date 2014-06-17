package BookStore.Action.CustomerActions;

import BookStore.Entity.CustomerEntity;
import BookStore.Service.Interface.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pwwpche on 2014/6/8.
 */
public class ModifyCustomer extends ActionSupport implements ServletRequestAware, ServletResponseAware
{
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
        String birth = request.getParameter("birthday").replace("T00:00:00","");
        System.out.println(birth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        try{
            sdf.parse(birth);
            customerEntity.setBirthday(Date.valueOf(birth));
            customerService.modifyCustomer(customerEntity);
            dataMap.put("succeed", true);

        }catch(Exception e){
            System.out.println(e.getCause());
        }
        return SUCCESS;
    }

    private Map<String, Object> dataMap;
    public ModifyCustomer() {
        dataMap = new HashMap<String, Object>();
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }
}
