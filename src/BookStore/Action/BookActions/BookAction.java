package BookStore.Action.BookActions;

import BookStore.Dao.Wrapper.BookEntityWrapper;
import BookStore.Service.Interface.BookService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pwwpche on 2014/6/9.
 */

public class BookAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    @Resource
    BookService bookService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> dataMap;

    public BookAction() {
        dataMap = new HashMap<String, Object>();
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }


    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletResponse getResponse(){
        return  this.response;
    }

    public HttpServletRequest getRequest(){
        return  this.request;
    }

    public String createBook(){
        dataMap.clear();
        String bookName = request.getParameter("bookName");
        String isbn = request.getParameter("isbn");
        String catagroy = request.getParameter("bookCatagory");
        String priceStr = request.getParameter("bookPrice") == null ? "0" : request.getParameter("bookPrice");
        Float price = Float.parseFloat(priceStr);
        String authors = request.getParameter("authors");
        BookEntityWrapper wrapper = new BookEntityWrapper();
        wrapper.setBookName(bookName);
        wrapper.setIsbn(isbn);
        wrapper.setAuthors(authors);
        wrapper.setBookCatagory(catagroy);
        wrapper.setBookPrice(price);
        bookService.addBook(wrapper);
        dataMap.put("success", true);
        return SUCCESS;
    }

    public String updateBook(){
        dataMap.clear();
        String bookName = request.getParameter("bookName");
        String isbn = request.getParameter("isbn");
        String catagroy = request.getParameter("bookCatagory");
        Float price = Float.parseFloat(request.getParameter("bookPrice") == null ? "0" : request.getParameter("bookPrice"));
        String authors = request.getParameter("authors");
        BookEntityWrapper wrapper = new BookEntityWrapper();
        wrapper.setBookName(bookName);
        wrapper.setIsbn(isbn);
        wrapper.setAuthors(authors);
        wrapper.setBookCatagory(catagroy);
        wrapper.setBookPrice(price);
        bookService.updateBook(wrapper);
        dataMap.put("success", true);
        return SUCCESS;
    }

    public String removeBookByName(){
        String bookName = request.getParameter("isbn");
        bookService.removeBookByIsbn(bookName);
        dataMap.put("success", true);
        return SUCCESS;
    }

    public String removeBookByIsbn(){
        String isbn = request.getParameter("isbn");
        bookService.removeBookByIsbn(isbn);
        dataMap.put("success", true);
        return SUCCESS;
    }

    public String getAllBooks(){
            int pageSize = Integer.parseInt(request.getParameter("rows") == null ? "0" : request.getParameter("rows"));
            int pageNum = Integer.parseInt(request.getParameter("page") == null ? "0" : request.getParameter("page"));

        List<BookEntityWrapper> bookEntityWrappers = bookService.getAllBooks(pageNum, pageSize);
        dataMap.put("total", bookService.getTotalSize());
        dataMap.put("rows", bookEntityWrappers);
        return SUCCESS;
    }


}
