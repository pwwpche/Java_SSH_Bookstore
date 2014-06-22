package BookStore.Service.Implement;

import BookStore.Dao.Interface.BookDao;
import BookStore.Dao.Interface.CartDao;
import BookStore.Dao.Wrapper.BookEntityWrapper;
import BookStore.Dao.Wrapper.CartBookWrapper;
import BookStore.Entity.BookEntity;
import BookStore.Entity.CartEntity;
import BookStore.Entity.CartbookEntity;
import BookStore.Service.Interface.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pwwpche on 2014/6/10.
 *
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    CartDao cartDao;

    @Resource
    BookDao bookDao;
    @Override
    public String addToCart(String username, BookEntityWrapper book, int quantity) {
        BookEntity bookEntity = bookDao.wrapperToBookEntity(book);
        return cartDao.addToCart(username, bookEntity, quantity);
    }

    @Override
    public String removeFromCart(String username, String isbn ) {
        return cartDao.removeFromCart(username, isbn);
    }

    @Override
    public String removeAll(String username) {
        return cartDao.removeAll(username);
    }

    @Override
    public String saveCartToDb(String username, List<String> bookIsbn, List<Integer> quantity) {
        cartDao.removeAll(username);
        return cartDao.saveCartToDb(username, bookIsbn, quantity);
    }


    @Override
    public String modifyCart(String username, BookEntity book, int quantity) {
        return cartDao.updateInCart(username, book, quantity);
    }

    @Override
    public CartEntity getCartByUsername(String username) {
        return cartDao.getCartByUsername(username);
    }

    @Override
    public List<CartBookWrapper> getCartBooksByUsername(String username) {
        CartEntity cartEntity = cartDao.getCartByUsername(username);
        List<CartbookEntity> cartbookEntities = new ArrayList<CartbookEntity>();
        cartbookEntities.addAll(cartEntity.getCartbooksByCid());
        List<CartBookWrapper> wrappers = new ArrayList<CartBookWrapper>();
        for (CartbookEntity cartbookEntity : cartbookEntities) {
            CartBookWrapper wrapper = new CartBookWrapper();
            BookEntity bookEntity = bookDao.getBookEntityByIsbn(cartbookEntity.getIsbn());
            wrapper.generateFromBookWrapper(bookEntity, cartbookEntity);
            wrappers.add(wrapper);
        }
        return  wrappers;
    }


}
