package BookStore.Service.Implement;

import BookStore.Dao.Interface.OrderDao;
import BookStore.Dao.Wrapper.CountInfoWrapper;
import BookStore.Dao.Wrapper.StatisticItemWrapper;
import BookStore.Entity.OrdersEntity;
import BookStore.Service.Interface.OrderService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pwwpche on 2014/6/11.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderDao orderDao;

    @Override
    public String createOrder(String username, List<String> bookIsbn, List<Integer> quantity) {
        return orderDao.createOrder(username, bookIsbn, quantity);
    }

    @Override
    public List<StatisticItemWrapper> getAllOrders(int pageNum, int pageSize) {
        List<OrdersEntity> ordersEntities;
        ordersEntities = orderDao.getAllOrders(pageNum, pageSize);
        return orderDao.orderListToStatisticList(ordersEntities, "", "");
    }

    @Override
    public Long getOrderTotal() {
        return orderDao.getOrderTotal();
    }

    @Override
    public List<StatisticItemWrapper> getAllOrdersByCatagory(String catagory) {
        List<OrdersEntity> ordersEntities;
        ordersEntities = orderDao.getAllOrdersByQuery("catagory", catagory);
        return orderDao.orderListToStatisticList(ordersEntities, "catagory", catagory);
    }

    @Override
    public List<StatisticItemWrapper> getAllOrdersByQuery(String queryName, String queryString) {
        List<OrdersEntity> ordersEntities;
        ordersEntities = orderDao.getAllOrdersByQuery(queryName, queryString);
        return orderDao.orderListToStatisticList(ordersEntities, queryName, queryString);
    }

    @Override
    public CountInfoWrapper getCountInfo(String queryName, String queryString) {
        return orderDao.countInfoList(queryName, queryString);
    }
}
