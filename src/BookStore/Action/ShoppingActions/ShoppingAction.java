package BookStore.Action.ShoppingActions;

import BookStore.Dao.Wrapper.BookEntityWrapper;
import BookStore.Service.Interface.CartService;
import BookStore.Service.Interface.OrderService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pwwpche on 2014/6/9.
 */

public class ShoppingAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    @Resource
    OrderService orderService;

    @Resource
    CartService cartService;

    private HttpServletRequest request;
    private HttpServletResponse response;

    private Map<String, Object> dataMap;

    public ShoppingAction() {
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

    public String getCartContent(){
        dataMap.clear();
        /*
        String username = (String)request.getSession().getAttribute("username");
        if(username != null)
        {
            List<BookEntityWrapper> wrapperList = cartService.getCartBooksByUsername(username);
            dataMap.put("total", wrapperList.size());
            dataMap.put("rows", wrapperList);
        }
        */
        HttpSession session = request.getSession();
        String content = (String)session.getAttribute("cartContent");
        try {
            dataMap.put("data", content);
        }catch (Exception e){
            System.out.println(e.getCause());
        }
        return "success";
    }

    public String buy(){
        dataMap.clear();
        //String username = request.getParameter("username");
        String username = "aaa";
        String rowData = request.getParameter("rowData");
        try {
            JSONObject row = new JSONObject(rowData);
            JSONArray array = row.getJSONArray("rows");
            int size = row.getInt("total");
            List<String> isbnList = new ArrayList<String>();
            List<Integer> quantityList = new ArrayList<Integer>();
            for(int i = 0 ; i < size ; i++)
            {
                String isbn = ((JSONObject)array.get(i)).getString("isbn");
                Integer quantity = ((JSONObject)array.get(i)).getInt("quantity");
                isbnList.add(isbn);
                quantityList.add(quantity);
            }
            cartService.buy(username, isbnList, quantityList);
            orderService.createOrder(username, isbnList, quantityList);
            cartService.removeAll(username);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "success";
    }

    public String saveCartToSession(){
        dataMap.clear();
        String cartContent = request.getParameter("cartContent");
        HttpSession session = request.getSession();
        session.setAttribute("cartContent", cartContent);
        /*
        try {
            JSONObject obj = new JSONObject(cartContent);
            String rowData = obj.getString("rows");
            HttpSession session = request.getSession();
            session.setAttribute("cartContent", rowData);
            System.out.println(rowData);
        }catch (Exception e){
            System.out.println(e.getCause());
        }
        */
        return "success";
    }
}
