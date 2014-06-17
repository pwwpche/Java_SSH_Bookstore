package BookStore.Service.Interface;

import BookStore.Dao.Wrapper.BookEntityWrapper;

import java.util.List;

/**
 * Created by pwwpche on 2014/6/9.
 */
public interface BookService {
    public String addBook(BookEntityWrapper bookEntityWrapper);
    public BookEntityWrapper getBookByName(String bookName);
    public List<BookEntityWrapper> getBooksByIsbnList(List<String> list);
    public List<BookEntityWrapper> getAllBooks(int pageNum, int pageSize);
    public Long getTotalSize();
    public String updateBook(BookEntityWrapper bookEntityWrapper);
    public String removeBookByIsbn(String isbn);
}
