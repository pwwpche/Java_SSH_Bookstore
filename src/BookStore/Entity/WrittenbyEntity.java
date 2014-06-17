package BookStore.Entity;

/**
 * Created by pwwpche on 2014/6/10.
 */
public class WrittenbyEntity {
    private String isbn;
    private int aid;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WrittenbyEntity that = (WrittenbyEntity) o;

        if (aid != that.aid) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + aid;
        return result;
    }

}
