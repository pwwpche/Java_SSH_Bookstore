package BookStore.Dao.Wrapper;
import BookStore.Entity.BookEntity;
import BookStore.Entity.WrittenbyEntity;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pwwpche on 2014/6/9.
 *
 */


public class BookEntityWrapper extends ActionSupport{
    private String isbn;
    private String bookName;
    private Float bookPrice;
    private String bookCatagory;
    private transient List<Integer> authorIds;
    private String authors;



    public void setAuthorIds(List<Integer> list){
        this.authorIds = list;
    }

    public List<Integer> getAuthorIds(){
        return authorIds;
    }

    public void setAuthorsByNameList(List<String> authorList){
        for (String anAuthorList : authorList) {
            this.authors += anAuthorList;
        }
    }

    public void setIsbn(String str){
        this.isbn = str;
    }

    public String getIsbn(){
        return isbn;
    }

    public void setBookName(String str){
        this.bookName = str;
    }

    public String getBookName(){
        return bookName;
    }

    public void setBookCatagory(String str){
        this.bookCatagory = str;
    }

    public String getBookCatagory(){
        return  bookCatagory;
    }

    public void setBookPrice(Float num){
        this.bookPrice = num;
    }

    public Float getBookPrice(){
        return bookPrice;
    }

    public String getAuthors(){
        return this.authors;
    }

    public void setAuthors(String str){
        this.authors = str;
    }

    public void createFromBookEntity(BookEntity bookEntity)
    {
        if(bookEntity == null){
            return ;
        }
        isbn = bookEntity.getIsbn();
        bookName = bookEntity.getBookName();
        bookPrice = bookEntity.getBookPrice();
        bookCatagory = bookEntity.getBookCatagory();
        authorIds = new ArrayList<Integer>();
        int size =  bookEntity.getWrittenbiesByIsbn().size();
        ArrayList<WrittenbyEntity> writtenbyEntities = new ArrayList<WrittenbyEntity>();
        writtenbyEntities.addAll(bookEntity.getWrittenbiesByIsbn());
        for(int i = 0 ; i < size ; i++) {
            authorIds.add(writtenbyEntities.get(i).getAid());
        }
    }

    
/*
    public BookEntity generateBookEntity(){
        BookEntity newBook = new BookEntity();
        newBook.setBookCatagory(this.bookCatagory);
        newBook.setBookName(this.bookName);
        newBook.setBookPrice(this.bookPrice);
        newBook.setIsbn(this.isbn);

        String authorNames[] = this.authors.split(";");
        int size = authorNames.length;
        Collection<WrittenbyEntity> writtenbyEntities = new ArrayList<WrittenbyEntity>();
        for(int i = 0 ; i < size ; i++)
        {

            WrittenbyEntity writtenbyEntity = authorService.getWrittenByFromISBN(isbn,authorNames[i]);
            if(writtenbyEntity == null){
                writtenbyEntity = authorService.createWrittenByFromISBN(isbn, authorNames[i]);
            }
            writtenbyEntities.add(writtenbyEntity);

        }
        newBook.setWrittenbiesByIsbn(writtenbyEntities);
        return newBook;
    }
    */
}
