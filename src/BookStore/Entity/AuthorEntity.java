package BookStore.Entity;

/**
 * Created by pwwpche on 2014/6/10.
 */
public class AuthorEntity {
    private int aid;
    private String authorName;
    private String nation;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorEntity that = (AuthorEntity) o;

        if (aid != that.aid) return false;
        if (authorName != null ? !authorName.equals(that.authorName) : that.authorName != null) return false;
        if (nation != null ? !nation.equals(that.nation) : that.nation != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = aid;
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (nation != null ? nation.hashCode() : 0);
        return result;
    }
}
