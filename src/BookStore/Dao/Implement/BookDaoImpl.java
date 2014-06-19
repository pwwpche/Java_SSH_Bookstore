package BookStore.Dao.Implement;

import BookStore.Dao.Interface.BookDao;
import BookStore.Dao.Super.SuperDao;
import BookStore.Entity.AuthorEntity;
import BookStore.Entity.WrittenbyEntity;
import BookStore.Entity.WrittenbyEntityPK;
import BookStore.Dao.Wrapper.BookEntityWrapper;

import BookStore.Entity.BookEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * Created by pwwpche on 2014/6/9.
 *
 */
@Repository
public class BookDaoImpl extends SuperDao implements BookDao  {


    @Override
    public String createBook(BookEntityWrapper bookEntityWrapper) {
        Session session = sessionFactory.openSession();
        if(session.get(BookEntity.class, bookEntityWrapper.getIsbn()) == null)
        {
            Transaction transaction = session.beginTransaction();
            BookEntity bookEntity = wrapperToBookEntity(bookEntityWrapper);
            session.save(bookEntity);
            List<WrittenbyEntity> writtenbyEntities = new ArrayList<WrittenbyEntity>();
            writtenbyEntities.addAll(bookEntity.getWrittenbiesByIsbn());
            for (WrittenbyEntity writtenbyEntity : writtenbyEntities) {
                session.save(writtenbyEntity);
            }

            transaction.commit();
            session.close();
            return "success";
        }
        return "Book exists!";
    }

    @Override
    public String updateBook(BookEntityWrapper bookEntityWrapper) {
        Session session = sessionFactory.openSession();
        BookEntity bookEntity = wrapperToBookEntity(bookEntityWrapper);
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(bookEntity);
        transaction.commit();
        session.close();
        return "success";
    }



    @Override
    public List<BookEntityWrapper> getAllBooks(int pageNum, int pageSize) {
        List<BookEntityWrapper> bookEntityWrappers = new ArrayList<BookEntityWrapper>();
        try {
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from BookEntity ");
            query.setFirstResult((pageNum - 1) * pageSize);
            query.setMaxResults(pageSize);
            query.setCacheable(true);
            query.setCacheRegion("BookEntity");
            List result = query.list();
            for (Object aResult : result) {
                BookEntity bookEntity = (BookEntity) aResult;
                BookEntityWrapper wrapper = createFromBookEntity(bookEntity);
                bookEntityWrappers.add(wrapper);
            }
            session.close();
            return bookEntityWrappers;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return bookEntityWrappers;
    }

    @Override
    public Long getBooksTotal() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select count(*) from BookEntity as bookEntity");
        return Long.parseLong(query.uniqueResult().toString());
    }


    @Override
    public List<BookEntityWrapper> getBooksByIsbnList(List<String> list) {
        List<BookEntityWrapper> wrappers  = new ArrayList<BookEntityWrapper>();
        Session session = sessionFactory.openSession();
        for (String aList : list) {
            BookEntity bookEntity = (BookEntity) session.get(BookEntity.class, aList);
            BookEntityWrapper wrapper = createFromBookEntity(bookEntity);
            wrappers.add(wrapper);
        }
        return wrappers;
    }

    @Override
    public BookEntityWrapper getBookByName(String bookName) {
        BookEntityWrapper bookEntityWrapper = null;
        try {
            System.out.println("getBookByName " + bookName);
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from BookEntity as bookEntity where bookEntity.bookName = :name");
            query.setString("name", bookName);
            query.setCacheable(true);
            query.setCacheRegion("BookEntity");
            transaction.commit();
            List result = query.list();
            if(result.size() > 0) {
                BookEntity bookEntity = (BookEntity)result.get(0);
                bookEntityWrapper = createFromBookEntity(bookEntity);
            }
            session.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return bookEntityWrapper;
    }

    @Override
    public BookEntityWrapper createFromBookEntity(BookEntity bookEntity) {
        BookEntityWrapper wrapper = new BookEntityWrapper();

        //Set up WrittenBy relations of the book
        Session session = sessionFactory.openSession();
        String hql = "from WrittenbyEntity as writtenby where writtenby.isbn = '"+ bookEntity.getIsbn() + "'";
        Query query = session.createQuery(hql);
        query.setCacheable(true);
        query.setCacheRegion("WrittenByEntity");
        List result = query.list();
        HashSet<WrittenbyEntity> writtenbyEntities = new HashSet<WrittenbyEntity>();
        writtenbyEntities.addAll((List<WrittenbyEntity>)result);
        bookEntity.setWrittenbiesByIsbn(writtenbyEntities);

        //CreateFromBookEntity will look for AuthorIds in writtenBy
        wrapper.createFromBookEntity(bookEntity);
        wrapper.setAuthors(getAuthorNamesById(wrapper.getAuthorIds()));
        return wrapper;
    }

    @Override
    public String removeBookByISBN(String ISBN) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        BookEntity bookEntity = (BookEntity)session.get(BookEntity.class, ISBN);
        if(bookEntity == null)
        {
            return "Book Not Found!";
        }

        //Remove all writtenBy relations first
        List<WrittenbyEntity> writtenbyEntities = new ArrayList<WrittenbyEntity>();
        writtenbyEntities.addAll(bookEntity.getWrittenbiesByIsbn());
        for (WrittenbyEntity writtenbyEntity : writtenbyEntities) {
            session.delete(writtenbyEntity);
        }

        //Remove the book
        session.delete(bookEntity);
        transaction.commit();
        session.close();
        return "success";
    }

    @Override
    public String removeBookByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from BookEntity as book where book.bookName = :bookName";
        Query query = session.createQuery(hql);
        query.setString("bookName", name);
        List result = query.list();
        if(result.size() == 0)
        {
            return "Book Not Found!";
        }
        session.delete(result.get(0));
        transaction.commit();
        session.close();
        return "success";
    }

    private String getAuthorNamesById(List<Integer> ids) {
        Session session = sessionFactory.openSession();
        String result = "";
        for (Integer id : ids) {
            AuthorEntity authorEntity = (AuthorEntity) session.get(AuthorEntity.class, id);
            if (authorEntity != null) {
                result += authorEntity.getAuthorName();
                result += ";";
            }
        }
        session.close();
        return result;
    }

    private WrittenbyEntity createWrittenByFromISBN(String isbn, String authorName) {
        Session session = sessionFactory.openSession();
        AuthorEntity authorEntity = new AuthorEntity();

        //Check whether need to create this user
        Transaction transaction = session.beginTransaction();
        String hql = "from AuthorEntity as author where author.authorName = :authorName";
        Query query = session.createQuery(hql);
        query.setCacheable(true);
        query.setCacheRegion("AuthorEntity");
        query.setString("authorName", authorName);
        List result = query.list();
        if (result.size() == 0) {
            authorEntity.setAuthorName(authorName);
            authorEntity.setNation("China");
            session.save(authorEntity);
            session.persist(authorEntity);
            transaction.commit();
            transaction = session.beginTransaction();
            System.out.println("authorEntity persist id=" + authorEntity.getAid());
        }else {
            authorEntity = (AuthorEntity) result.get(0);
        }
        WrittenbyEntity writtenbyEntity = new WrittenbyEntity();
        writtenbyEntity.setIsbn(isbn);
        writtenbyEntity.setAid(authorEntity.getAid());
        if(null != session.get(BookEntity.class, isbn) ) {
            session.save(writtenbyEntity);
            transaction.commit();
        }
        session.close();
        return writtenbyEntity;
    }

    private WrittenbyEntity getWrittenByFromISBN(String isbn, String authorName) {
        Session session = sessionFactory.openSession();
        //Get Author
        String hql = "from AuthorEntity as author where author.authorName = :authorName";
        Query query = session.createQuery(hql);
        query.setCacheable(true);
        query.setString("authorName", authorName);
        List result = query.list();
        if(result.size() == 0) {
            return null;
        }

        AuthorEntity authorEntity = (AuthorEntity)result.get(0);

        WrittenbyEntityPK writtenbyEntityPK = new WrittenbyEntityPK();
        writtenbyEntityPK.setAid(authorEntity.getAid());
        writtenbyEntityPK.setIsbn(isbn);
        //session.close();
        return (WrittenbyEntity)session.get(WrittenbyEntity.class, writtenbyEntityPK);
    }

    private List<AuthorEntity> authorNameToEntity(String names){
        String[] authorNames = names.split(";");
        Session session = sessionFactory.openSession();
        List<AuthorEntity> authorEntities = new ArrayList<AuthorEntity>();
        for (String authorName : authorNames) {
            authorEntities.add(getAuthorEntityByName(authorName));
        }
        session.close();
        return authorEntities;
    }

    @Override
    public BookEntity wrapperToBookEntity(BookEntityWrapper wrapper) {
        BookEntity newBook = new BookEntity();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        //newBook = (BookEntity)session.get(BookEntity.class, wrapper.getIsbn());

/*
        List<AuthorEntity> authorEntities = authorNameToEntity(wrapper.getAuthors());
        for(int i = 0  ; i < authorEntities.size() ; i++){
            WrittenbyEntity writtenbyEntity = new WrittenbyEntity();
            writtenbyEntity.setIsbn(wrapper.getIsbn());
            writtenbyEntity.setAid(authorEntities.get(i).getAid());
            //AuthorEntity authorEntity = (AuthorEntity)session.get(AuthorEntity.class, wrapper.getAuthorIds().get(i));
            writtenbyEntity.setAuthorByAid(authorEntities.get(i));
            session.saveOrUpdate(writtenbyEntity);
        }
        transaction.commit();
        session.close();
        return  newBook;
*/
        //Remove Previous writtenBy relations
        Query query = session.createQuery("from WrittenbyEntity as writtenBy where writtenBy.isbn = '" + wrapper.getIsbn() + "'");
        query.setCacheable(true);
        List result = query.list();
        for (Object aResult : result) {
            session.delete( aResult);
        }
        transaction.commit();
        session.flush();
        //Temporarily set up a new book
        newBook.setBookCatagory(wrapper.getBookCatagory());
        newBook.setBookName(wrapper.getBookName());
        newBook.setBookPrice(wrapper.getBookPrice());
        newBook.setIsbn(wrapper.getIsbn());

        //Get author names
        String authorNames[] = wrapper.getAuthors().split(";");
        Collection<WrittenbyEntity> writtenbyEntities =  new HashSet<WrittenbyEntity>();
        for (String authorName : authorNames) {
            WrittenbyEntity writtenbyEntity = createWrittenByFromISBN(wrapper.getIsbn(), authorName);
            writtenbyEntities.add(writtenbyEntity);
        }
        newBook.setWrittenbiesByIsbn(writtenbyEntities);
        session.close();
        return newBook;

    }

    private AuthorEntity getAuthorEntityByName(String name){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from AuthorEntity as author where author.authorName = '" + name + "'";
        Query query = session.createQuery(hql);
        query.setCacheable(true);
        query.setCacheRegion("AuthorEnitty");
        List<AuthorEntity> result = (List<AuthorEntity>) query.list();

        if(result.size() > 0) {
            session.close();
            return result.get(0);
        }
        else {
            AuthorEntity authorEntity = new AuthorEntity();
            authorEntity.setAuthorName(name);
            authorEntity.setNation("China");
            session.save(authorEntity);
            transaction.commit();
            session.close();
            return authorEntity;
        }
    }
}
