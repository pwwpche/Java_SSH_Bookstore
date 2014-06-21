package BookStore.Entity;

/**
 * Created by pwwpche on 2014/6/10.
 */
public class OrderbookEntity {
    private int oid;
    private String isbn;
    private Integer quantity;
    private Float price;


    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderbookEntity that = (OrderbookEntity) o;

        if (oid != that.oid) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oid;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
/*
    public BookEntity getBookByIsbn() {
        return bookByIsbn;
    }

    public void setBookByIsbn(BookEntity bookByIsbn) {
        this.bookByIsbn = bookByIsbn;
    }
*/
}
