package BookStore.Entity;

import java.io.Serializable;

/**
 * Created by pwwpche on 2014/6/10.
 */
public class OrderbookEntityPK implements Serializable {
    private int oid;
    private String isbn;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderbookEntityPK that = (OrderbookEntityPK) o;

        if (oid != that.oid) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oid;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        return result;
    }
}
