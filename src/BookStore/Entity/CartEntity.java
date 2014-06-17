package BookStore.Entity;

import java.util.Collection;

/**
 * Created by pwwpche on 2014/6/10.
 */
public class CartEntity {
    private int cid;
    private String username;
    private Collection<CartbookEntity> cartbooksByCid;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartEntity that = (CartEntity) o;

        if (cid != that.cid) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cid;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    public Collection<CartbookEntity> getCartbooksByCid() {
        return cartbooksByCid;
    }

    public void setCartbooksByCid(Collection<CartbookEntity> cartbooksByCid) {
        this.cartbooksByCid = cartbooksByCid;
    }
}
