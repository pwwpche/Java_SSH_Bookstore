package BookStore.Entity;

import java.util.Collection;

/**
 * Created by pwwpche on 2014/6/10.
 */
public class OrdersEntity {
    private int oid;
    private String username;
    private Integer orderYear;
    private Integer orderMonth;
    private Integer orderDay;
    private Float orderTotalPrice;
    private Collection<OrderbookEntity> orderbooksByOid;

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getOrderYear() {
        return orderYear;
    }

    public void setOrderYear(Integer orderYear) {
        this.orderYear = orderYear;
    }

    public Integer getOrderMonth() {
        return orderMonth;
    }

    public void setOrderMonth(Integer orderMonth) {
        this.orderMonth = orderMonth;
    }

    public Integer getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(Integer orderDay) {
        this.orderDay = orderDay;
    }

    public Float getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Float orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersEntity that = (OrdersEntity) o;

        if (oid != that.oid) return false;
        if (orderDay != null ? !orderDay.equals(that.orderDay) : that.orderDay != null) return false;
        if (orderMonth != null ? !orderMonth.equals(that.orderMonth) : that.orderMonth != null) return false;
        if (orderTotalPrice != null ? !orderTotalPrice.equals(that.orderTotalPrice) : that.orderTotalPrice != null)
            return false;
        if (orderYear != null ? !orderYear.equals(that.orderYear) : that.orderYear != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oid;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (orderYear != null ? orderYear.hashCode() : 0);
        result = 31 * result + (orderMonth != null ? orderMonth.hashCode() : 0);
        result = 31 * result + (orderDay != null ? orderDay.hashCode() : 0);
        result = 31 * result + (orderTotalPrice != null ? orderTotalPrice.hashCode() : 0);
        return result;
    }

    public Collection<OrderbookEntity> getOrderbooksByOid() {
        return orderbooksByOid;
    }

    public void setOrderbooksByOid(Collection<OrderbookEntity> orderbooksByOid) {
        this.orderbooksByOid = orderbooksByOid;
    }
}
