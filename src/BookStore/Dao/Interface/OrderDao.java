package BookStore.Dao.Interface;

import BookStore.Dao.Wrapper.CountInfoWrapper;
import BookStore.Dao.Wrapper.StatisticItemWrapper;
import BookStore.Entity.BookEntity;
import BookStore.Entity.CartEntity;
import BookStore.Entity.OrdersEntity;
import org.hibernate.internal.CriteriaImpl;

import java.util.List;

/**
 * Created by pwwpche on 2014/6/9.
 */
public interface OrderDao {
    public String createOrder(String username, List<String> bookIsbn, List<Integer> quantity) ;
    public List<OrdersEntity> getAllOrders(int pageNum, int pageSize);
    public Long getOrderTotal();
    public List<OrdersEntity> getAllOrdersByUser(String username);
    public List<OrdersEntity> getAllOrdersByQuery(String queryName, String queryString);
    public List<StatisticItemWrapper> orderListToStatisticList(List ordersEntities, String queryName, String queryString);
    public CountInfoWrapper countInfoList(String queryName, String queryString);
}
