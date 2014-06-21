package BookStore.Dao.Wrapper;

import BookStore.Entity.BookEntity;
import BookStore.Entity.CartbookEntity;

/**
 * Created by pwwpche on 2014/6/22.
 */
public class CartBookWrapper {
    String isbn;
    String bookName;
    int quantity;
    Float price;

    public void setIsbn(String str){
        isbn = str;
    }
    public String getIsbn(){
        return  isbn;
    }

    public void setBookname(String str){
        bookName = str;
    }
    public String getBookname(){
        return  bookName;
    }

    public void setQuantity(int num){
        quantity = num;
    }

    public int getQuantity(){
        return quantity;
    }
    public void setPrice(Float num){
        this.price = num;
    }
    public Float getPrice(){
        return price;
    }

    public void generateFromBookWrapper(BookEntity bookEntity, CartbookEntity cartbookEntity){
        this.isbn = cartbookEntity.getIsbn();
        this.bookName = bookEntity.getBookName();
        this.price = bookEntity.getBookPrice();
        this.quantity = cartbookEntity.getQuantity();
    }
}
