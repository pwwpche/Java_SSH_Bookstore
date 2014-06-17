package BookStore.Action.CustomerActions;

import BookStore.Entity.CustomerEntity;
import BookStore.Service.Interface.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pwwpche on 2014/6/8.
 *
 */
public class GetAllCustomerInfo extends ActionSupport implements ServletResponseAware, ServletRequestAware{
    @Resource
    private transient CustomerService customerService;

    private HttpServletRequest request;
    private HttpServletResponse response;

    public String getFormData(){
        int pageSize = Integer.parseInt(request.getParameter("rows") == null ? "0" : request.getParameter("rows"));
        int pageNum = Integer.parseInt(request.getParameter("page") == null ? "0" : request.getParameter("page"));

        List<CustomerEntity> results = customerService.getAllCustomers(pageNum, pageSize);
        dataMap.put("total", customerService.getCustomerTotal());
        dataMap.put("rows",results);
        return "success";
    }

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

    private Map<String, Object> dataMap;


    public GetAllCustomerInfo() {
        dataMap = new HashMap<String, Object>();
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }


}
