package BookStore.Dao.Interface;

import BookStore.Dao.Wrapper.BookEntityWrapper;
import BookStore.Entity.BookEntity;

import java.util.List;

/**
 * Created by pwwpche on 2014/6/9.
 */
public interface BookDao {
    public String createBook(BookEntityWrapper bookEntityWrapper);
    public String updateBook(BookEntityWrapper bookEntityWrapper);
    public List<BookEntityWrapper> getAllBooks(int pageNum, int pageSize);
    public Long getBooksTotal();
    public List<BookEntityWrapper> getBooksByIsbnList(List<String> list);
    public BookEntityWrapper getBookByName(String bookName);
    public BookEntityWrapper createFromBookEntity(BookEntity bookEntity);
    public BookEntity getBookEntityByIsbn(String isbn);
    public String removeBookByISBN(String bookName);
    public String removeBookByName(String bookName);
    public BookEntity wrapperToBookEntity(BookEntityWrapper wrapper);
}
