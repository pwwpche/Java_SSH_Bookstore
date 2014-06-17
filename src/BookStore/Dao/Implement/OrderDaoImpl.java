package BookStore.Dao.Implement;

import BookStore.Dao.Interface.OrderDao;
import BookStore.Dao.Super.SuperDao;
import BookStore.Dao.Wrapper.CountInfoWrapper;
import BookStore.Dao.Wrapper.StatisticItemWrapper;
import BookStore.Entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public class OrderDaoImpl extends SuperDao implements OrderDao {
    @Override
    public String createOrder(String username, List<String> bookIsbn, List<Integer> quantity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from CartEntity as cart where cart.username =:username");
        query.setString("username", username);
        transaction.commit();
        List result = query.list();
        if(result.size() > 0){
            //Get cart-book relations
            OrdersEntity ordersEntity = new OrdersEntity();

            //Convert Cart-Book relation to Order-Book relation and save them
            Collection<OrderbookEntity> set;
            set = createOrderBookEntities(bookIsbn, quantity);
            List<OrderbookEntity> orderbookEntities = new ArrayList<OrderbookEntity>();
            orderbookEntities.addAll(set);
            for (OrderbookEntity orderbookEntity : orderbookEntities) {
                session.save(orderbookEntity);
            }

            //Set up OrderEntity and update CartEntity
            ordersEntity.setOrderbooksByOid(set);
            ordersEntity.setUsername(username);

            Date date = new Date();
            ordersEntity.setOrderYear(date.getYear());
            ordersEntity.setOrderMonth(date.getMonth());
            ordersEntity.setOrderDay(date.getDay());
            ordersEntity.setOrderTotalPrice(calculateTotalPrice(orderbookEntities));

            //Persist entities
            session.save(ordersEntity);
            session.persist(ordersEntity);
            session.close();
            return "success";
        }
        return "Error: Cart not exist.";
    }

    @Override
    public List<OrdersEntity> getAllOrders(int pageNum, int pageSize) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from OrdersEntity ");
        query.setFirstResult((pageNum - 1) * pageSize);
        query.setMaxResults(pageSize);
        transaction.commit();
        List result = query.list();
        List<OrdersEntity> ordersEntities = new ArrayList<OrdersEntity>();
        ordersEntities.addAll(result);
        return ordersEntities;
    }

    @Override
    public Long getOrderTotal() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select count(*) from OrdersEntity as orders ");
        return Long.parseLong(query.uniqueResult().toString());
    }

    @Override
    public List<OrdersEntity> getAllOrdersByUser(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from OrdersEntity as orders where orders.username = :username");
        query.setString("username",username);
        transaction.commit();
        List result = query.list();
        List<OrdersEntity> ordersEntities = new ArrayList<OrdersEntity>();
        ordersEntities.addAll(result);
        return ordersEntities;
    }

    @Override
    public List<OrdersEntity> getAllOrdersByQuery(String queryName, String queryString) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<OrdersEntity> ordersEntities = new ArrayList<OrdersEntity>();
        if(verifyQueryString(queryName) && !queryName.equals("catagory")) {
            Query query = session.createQuery("from OrdersEntity as orders where orders." + queryName + " =:queryString");
            query.setString("queryString", queryString);
            transaction.commit();
            List result = query.list();
            ordersEntities.addAll(result);
        }
        if(queryName.equals("catagory")){
            Query query = session.createQuery("from OrdersEntity as orderEntity join orderEntity.orderbooksByOid " +
                    " as orders join orders.bookByIsbn as book where book.bookCatagory = :queryString");
            query.setString("queryString",queryString);
            transaction.commit();
            List result = query.list();
            ordersEntities.addAll(result);
        }
        return ordersEntities;
    }

    @Override
    public List<StatisticItemWrapper> orderListToStatisticList(List ordersEntitiesList, String queryName, String queryString){
        int size = ordersEntitiesList.size();
        List<StatisticItemWrapper> statisticItemWrappers = new ArrayList<StatisticItemWrapper>();
        if (queryName.equals("catagory")) {
            for (int i = 0; i < size; i++) {
                OrdersEntity ordersEntity = (OrdersEntity) ((List<Object[]>) ordersEntitiesList).get(i)[0];
                OrderbookEntity orderbookEntity = (OrderbookEntity)  ((List<Object[]>) ordersEntitiesList).get(i)[1];
                StatisticItemWrapper wrapper = generateStaticItemWrapper(ordersEntity, orderbookEntity);
                statisticItemWrappers.add(wrapper);
            }
        }
        else {
            List<OrdersEntity> ordersEntities = (List<OrdersEntity>) ordersEntitiesList;
            for (int i = 0; i < size; i++) {
                OrdersEntity orderEntity = ordersEntities.get(i);
                List<OrderbookEntity> orderbookEntities = new ArrayList<OrderbookEntity>();
                orderbookEntities.addAll(orderEntity.getOrderbooksByOid());
                for (OrderbookEntity orderbookEntity : orderbookEntities) {
                    StatisticItemWrapper wrapper = generateStaticItemWrapper(orderEntity, orderbookEntity);
                    statisticItemWrappers.add(wrapper);
                }
            }
        }
        return statisticItemWrappers;
    }

    @Override
    public CountInfoWrapper countInfoList(String queryName, String queryString) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CountInfoWrapper countInfoWrapper = new CountInfoWrapper();
        countInfoWrapper.setItemName(queryName);
        countInfoWrapper.setItemString(queryString);

        String hql =  "";
        if(verifyQueryString(queryName) && !queryName.equals("catagory")) {
            hql = "select orders."+ queryName + ", sum(orders.orderTotalPrice) from OrdersEntity as orders " +
                    " group by orders." + queryName;
            Query query = session.createQuery(hql);
            transaction.commit();
            List<Object[]> result = (List<Object[]>)query.list();
            for (Object[]  aResult : result) {
                String str = "" + aResult[0];
                countInfoWrapper.insertPair(str, (Double)(aResult[1]));
            }
        } else if(queryName.equals("catagory"))
        {
            hql = "select book.bookCatagory , sum(book.bookPrice) from OrderbookEntity  as orders join orders.bookByIsbn as book group by book.bookCatagory";
            Query query = session.createQuery(hql);
            transaction.commit();
            List<Object[]> result = (List<Object[]>)query.list();
            for (Object[]  aResult : result) {
                countInfoWrapper.insertPair((String)(aResult[0]), (Double)(aResult[1]));
            }
        }



        return countInfoWrapper;
    }

    private HashSet<OrderbookEntity> createOrderBookEntities(List<String> isbnList, List<Integer> quantities){
        int size = isbnList.size();
        Session session  = sessionFactory.openSession();
        HashSet<OrderbookEntity> orderbookEntities = new HashSet<OrderbookEntity>();
        for(int i = 0 ; i < size ; i++){
            BookEntity bookEntity = (BookEntity)session.get(BookEntity.class, isbnList.get(i));
            OrderbookEntity orderbookEntity = new OrderbookEntity();
            orderbookEntity.setQuantity(quantities.get(i));
            orderbookEntity.setBookByIsbn(bookEntity);
            orderbookEntity.setIsbn(bookEntity.getIsbn());
            orderbookEntity.setPrice(bookEntity.getBookPrice());
            orderbookEntities.add(orderbookEntity);
            session.save(orderbookEntity);
        }
        return  orderbookEntities;
    }

    private Float calculateTotalPrice(List<OrderbookEntity> orderbookEntities){
        float result = 0;
        for (OrderbookEntity orderbookEntity : orderbookEntities) {
            result += (orderbookEntity.getPrice() * orderbookEntity.getQuantity());
        }
        return result;
    }

    private StatisticItemWrapper generateStaticItemWrapper(OrdersEntity ordersEntity, OrderbookEntity orderbookEntity){
        StatisticItemWrapper wrapper = new StatisticItemWrapper();
        wrapper.createWrapper(ordersEntity, orderbookEntity);
        Session session = sessionFactory.openSession();
        BookEntity bookEntity = (BookEntity)session.get(BookEntity.class, orderbookEntity.getIsbn());
        wrapper.setBookName(bookEntity.getBookName());
        wrapper.setCatagory(bookEntity.getBookCatagory());
        session.close();
        return wrapper;
    }

    private boolean verifyQueryString(String str) {
        return str.equals("username") || str.equals("orderDay") || str.equals("orderMonth") ||
                str.equals("orderYear") || str.equals("catagory");
    }

}
