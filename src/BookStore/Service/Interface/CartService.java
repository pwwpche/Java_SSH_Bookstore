package BookStore.Service.Interface;

import BookStore.Dao.Wrapper.BookEntityWrapper;
import BookStore.Dao.Wrapper.CartBookWrapper;
import BookStore.Entity.BookEntity;
import BookStore.Entity.CartEntity;

import java.util.List;

/**
 * Created by pwwpche on 2014/6/7.
 */
public interface CartService {
    public String addToCart(String username, BookEntityWrapper book, int quantity);
    public String removeFromCart(String username, String isbn);
    public String removeAll(String username);
    public String saveCartToDb(String username, List<String> isbnList, List<Integer> quantity);
    public String modifyCart(String username,BookEntity book, int quantity);
    public CartEntity getCartByUsername(String username);
    public List<CartBookWrapper> getCartBooksByUsername(String username);
}
