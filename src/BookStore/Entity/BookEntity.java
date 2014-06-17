package BookStore.Entity;

import java.util.Collection;

/**
 * Created by pwwpche on 2014/6/10.
 */
public class BookEntity {
    private String isbn;
    private String bookName;
    private Float bookPrice;
    private String bookCatagory;
    private Collection<WrittenbyEntity> writtenbiesByIsbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookCatagory() {
        return bookCatagory;
    }

    public void setBookCatagory(String bookCatagory) {
        this.bookCatagory = bookCatagory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookEntity that = (BookEntity) o;

        if (bookCatagory != null ? !bookCatagory.equals(that.bookCatagory) : that.bookCatagory != null) return false;
        if (bookName != null ? !bookName.equals(that.bookName) : that.bookName != null) return false;
        if (bookPrice != null ? !bookPrice.equals(that.bookPrice) : that.bookPrice != null) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + (bookName != null ? bookName.hashCode() : 0);
        result = 31 * result + (bookPrice != null ? bookPrice.hashCode() : 0);
        result = 31 * result + (bookCatagory != null ? bookCatagory.hashCode() : 0);
        return result;
    }

    public Collection<WrittenbyEntity> getWrittenbiesByIsbn() {
        return writtenbiesByIsbn;
    }

    public void setWrittenbiesByIsbn(Collection<WrittenbyEntity> writtenbiesByIsbn) {
        this.writtenbiesByIsbn = writtenbiesByIsbn;
    }
}
