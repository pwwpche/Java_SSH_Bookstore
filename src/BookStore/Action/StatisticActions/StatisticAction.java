package BookStore.Action.StatisticActions;

import BookStore.Dao.Wrapper.CountInfoItem;
import BookStore.Dao.Wrapper.CountInfoWrapper;
import BookStore.Dao.Wrapper.StatisticItemWrapper;
import BookStore.Service.Interface.OrderService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pwwpche on 2014/6/9.
 *
 */
public class StatisticAction extends ActionSupport implements ServletRequestAware {
    @Resource
    OrderService orderService;
    private HttpServletRequest request;
    HashMap<String, Object> dataMap;
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public StatisticAction() {
        dataMap = new HashMap<String, Object>();
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public String getAllStatisticsItems(){
        dataMap.clear();
        String username = request.getParameter("type");
        if(username == null){
            return "error";
        }
        int pageSize = Integer.parseInt(request.getParameter("rows") == null ? "0" : request.getParameter("rows"));
        int pageNum = Integer.parseInt(request.getParameter("page") == null ? "0" : request.getParameter("page"));

        List<StatisticItemWrapper> wrapperList = orderService.getAllOrders(pageNum, pageSize);
        dataMap.put("total", wrapperList.size());
        dataMap.put("rows", wrapperList);
        return "success";
    }

    public String getStatisticsByItem(){
        dataMap.clear();
        String queryType = request.getParameter("type");
        String queryString = request.getParameter("dataString");
        List<StatisticItemWrapper> wrapperList = orderService.getAllOrdersByQuery(queryType, queryString);
        dataMap.put("total", wrapperList.size());
        dataMap.put("rows", wrapperList);
        return "success";
    }

    public String getCountInfo(){
        dataMap.clear();
        String queryType = request.getParameter("type");
        String queryString = request.getParameter("dataString");
        CountInfoWrapper wrappers = orderService.getCountInfo(queryType, queryString);
        List<CountInfoItem> countItems = wrappers.getItems();
        dataMap.put("total", wrappers.getItems().size());
        dataMap.put("rows", countItems);
        return "success";
    }
}
