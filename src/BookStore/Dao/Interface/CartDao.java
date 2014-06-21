package BookStore.Dao.Interface;

import BookStore.Dao.Super.SuperDao;
import BookStore.Entity.BookEntity;
import BookStore.Entity.CartEntity;
import BookStore.Dao.Wrapper.BookEntityWrapper;

import java.util.List;

/**
 * Created by pwwpche on 2014/6/9.
 */
public interface CartDao {
    public String addToCart(String username, BookEntity bookEntity, int quantity);
    public String updateInCart(String username, BookEntity bookEntity, int quantity);
    public String removeFromCart(String username, String ISBN);
    public String removeAll(String username);
    public String saveCartToDb(String username, List<String> isbnList, List<Integer> quantity);
    public CartEntity getCartByUsername(String username);
}
