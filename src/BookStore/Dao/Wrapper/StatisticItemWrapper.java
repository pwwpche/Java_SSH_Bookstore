package BookStore.Dao.Wrapper;

import BookStore.Entity.OrderbookEntity;
import BookStore.Entity.OrdersEntity;

/**
 * Created by pwwpche on 2014/6/11.
 *
 */
public class StatisticItemWrapper {
    private int oid;
    private String username;
    private int orderYear;
    private int orderMonth;
    private int orderDay;
    private String isbn;
    private String bookName;
    private String catagory;
    private int quantity;
    private Float totalPrice;

    public int getOid(){
        return oid;
    }
    public void setOid(int n){
        oid = n;
    }
    public int getOrderDay(){
        return orderDay;
    }
    public void setOrderDay(int n){
        orderDay = n;
    }

    public int getOrderMonth(){
        return orderMonth;
    }
    public void setOrderMonth(int n){
        orderMonth = n;
    }
    public int getOrderYear(){
        return orderYear;
    }
    public void setOrderYear(int n){
        orderYear = n;
    }

    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int n){
        quantity = n;
    }

    public String getBookName(){
        return bookName;
    }
    public void setBookName(String str){
        bookName = str;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String str){
        username = str;
    }

    public String getIsbn(){
        return isbn;
    }
    public void setIsbn(String str){
        isbn = str;
    }
    public String getCatagory(){
        return catagory;
    }

    public void setCatagory(String str){
        catagory = str;
    }

    public Float getTotalPrice(){
        return totalPrice;
    }

    public void setTotalPrice(float n){
        totalPrice = n;
    }

    //Book Catagory and book name need to be set up by DAO
    public String createWrapper(OrdersEntity ordersEntity, OrderbookEntity orderbookEntity){
        oid = ordersEntity.getOid();
        orderDay = ordersEntity.getOrderDay();
        orderMonth = ordersEntity.getOrderMonth();
        orderYear = ordersEntity.getOrderYear();
        totalPrice = orderbookEntity.getPrice();
        isbn = orderbookEntity.getIsbn();
        username = ordersEntity.getUsername();
        quantity = orderbookEntity.getQuantity();
        totalPrice = orderbookEntity.getPrice();
        return "success";
    }

}
