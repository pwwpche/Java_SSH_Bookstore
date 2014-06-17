package BookStore.Service.Implement;

import BookStore.Dao.Interface.BookDao;
import BookStore.Dao.Wrapper.BookEntityWrapper;
import BookStore.Service.Interface.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pwwpche on 2014/6/9.
 */
@Service
public class BookServiceImpl implements BookService {
    @Resource
    BookDao bookDao;

    @Override
    public String addBook(BookEntityWrapper bookEntityWrapper) {
        return bookDao.createBook(bookEntityWrapper);
    }

    @Override
    public BookEntityWrapper getBookByName(String bookName) {
        return bookDao.getBookByName(bookName);
    }

    @Override
    public List<BookEntityWrapper> getBooksByIsbnList(List<String> list) {
        return bookDao.getBooksByIsbnList(list);
    }

    @Override
    public List<BookEntityWrapper> getAllBooks(int pageNum, int pageSize) {
        return bookDao.getAllBooks(pageNum, pageSize);
    }

    @Override
    public Long getTotalSize() {
        return bookDao.getBooksTotal();
    }

    @Override
    public String updateBook(BookEntityWrapper bookEntityWrapper) {
        return bookDao.updateBook(bookEntityWrapper);
    }

    @Override
    public String removeBookByIsbn(String isbn) {
        return bookDao.removeBookByISBN(isbn);
    }
}
