package BookStore.Service.Interface;

import BookStore.Dao.Wrapper.CountInfoWrapper;
import BookStore.Dao.Wrapper.StatisticItemWrapper;
import BookStore.Entity.CartEntity;
import BookStore.Entity.OrdersEntity;

import java.util.List;

/**
 * Created by pwwpche on 2014/6/10.
 */
public interface OrderService {
    public String createOrder(String username, List<String> bookIsbn, List<Integer> quantity) ;
    public List<StatisticItemWrapper> getAllOrders(int pageNum, int pageSize);
    public Long getOrderTotal();
    public List<StatisticItemWrapper> getAllOrdersByCatagory(String catagory);
    public List<StatisticItemWrapper> getAllOrdersByQuery(String queryName, String queryString);
    public CountInfoWrapper getCountInfo(String queryName, String queryString);
}
