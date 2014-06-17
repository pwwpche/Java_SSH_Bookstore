package BookStore.Dao.Implement;

import BookStore.Dao.Interface.CartDao;
import BookStore.Dao.Super.SuperDao;
import BookStore.Entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pwwpche on 2014/6/10.
 *
 */
@Repository
public class CartDaoImpl  extends SuperDao implements CartDao{

    @Override
    public String addToCart(String username, BookEntity bookEntity, int quantity) {
        CartEntity cartEntity = getCartByUsername(username);
        if(cartEntity != null){
            //Get Cart Contents
            HashSet<CartbookEntity> cartbookEntities = new HashSet<CartbookEntity>();
            cartbookEntities.addAll(cartEntity.getCartbooksByCid());

            //Create new Cart-Book association
            CartbookEntity cartbookEntity = new CartbookEntity();
            cartbookEntity.setBookByIsbn(bookEntity);
            cartbookEntity.setCid(cartEntity.getCid());
            cartbookEntity.setQuantity(quantity);
            cartbookEntity.setIsbn(bookEntity.getIsbn());

            cartbookEntities.add(cartbookEntity);
            cartEntity.setCartbooksByCid(cartbookEntities);
            Session session = sessionFactory.openSession();
            session.save(cartEntity);
            session.close();
            return "success";
        }
        return "Error: Cart not exist.";
    }

    @Override
    public String updateInCart(String username,BookEntity bookEntity, int quantity) {
        CartEntity cartEntity = getCartByUsername(username);
        if(cartEntity != null){
            //Get Cart Contents
            HashSet<CartbookEntity> cartbookEntities = new HashSet<CartbookEntity>();
            cartbookEntities.addAll(cartEntity.getCartbooksByCid());
            for(Iterator it=cartbookEntities.iterator();it.hasNext();)
            {
                CartbookEntity temp = (CartbookEntity)it.next();
                if(temp.getIsbn().equals(bookEntity.getIsbn()))
                {
                    temp.setQuantity(quantity);
                }
            }
            cartEntity.setCartbooksByCid(cartbookEntities);
            Session session = sessionFactory.openSession();
            session.saveOrUpdate(cartEntity);
            session.close();
            return "success";
        }
        return "Error: Cart not exist.";
    }

    @Override
    public String removeFromCart(String username, String ISBN) {
        CartEntity cartEntity = getCartByUsername(username);
        if(cartEntity != null){
            //Get Cart Contents
            HashSet<CartbookEntity> cartbookEntities = new HashSet<CartbookEntity>();
            cartbookEntities.addAll(cartEntity.getCartbooksByCid());
            Session session = sessionFactory.openSession();
            for(Iterator it=cartbookEntities.iterator();it.hasNext();)
            {
                CartbookEntity temp = (CartbookEntity)it.next();
                if(temp.getIsbn().equals(ISBN))
                {
                    session.delete(temp);
                    it.remove();
                    break;
                }
            }
            cartEntity.setCartbooksByCid(cartbookEntities);

            session.saveOrUpdate(cartEntity);
            session.close();
            return "success";
        }
        return "Error: Cart not exist.";
    }

    @Override
    public String removeAll(String username) {
        CartEntity cartEntity = getCartByUsername(username);
        if(cartEntity != null){

            //Get Cart Contents
            Session session = sessionFactory.openSession();
            int size = cartEntity.getCartbooksByCid().size();
            List<CartbookEntity> cartbookEntities = new ArrayList<CartbookEntity>();
            cartbookEntities.addAll(cartEntity.getCartbooksByCid());
            for (int i = 0; i < size; i++) {
                CartbookEntityPK pk = new CartbookEntityPK();
                pk.setCid(cartbookEntities.get(i).getCid());
                pk.setIsbn(cartbookEntities.get(i).getIsbn());
                if(session.get(CartbookEntity.class,pk ) != null) {
                   session.delete(cartbookEntities.get(i));
                }
            }
            cartEntity.setCartbooksByCid(new HashSet<CartbookEntity>());
            session.saveOrUpdate(cartEntity);
            return "success";
        }
        return "Error: Cart not exist.";
    }

    @Override
    public String buy(String username, List<String> bookIsbn, List<Integer> quantity) {
        CartEntity cartEntity = getCartByUsername(username);
        if (cartEntity != null) {
            //Get Cart Contents
            Session session = sessionFactory.openSession();
            for (String aBookIsbn : bookIsbn) {
                CartbookEntityPK pk = new CartbookEntityPK();
                pk.setIsbn(aBookIsbn);
                pk.setCid(cartEntity.getCid());
                CartbookEntity cartbookEntity = (CartbookEntity) session.get(CartbookEntity.class, pk);
                if (cartbookEntity != null) {
                    session.save(cartbookEntity);
                }
            }
            cartEntity.setCartbooksByCid(new HashSet<CartbookEntity>());
            session.saveOrUpdate(cartEntity);
            session.close();
            return "success";
        }
        return "Error: Cart not found";
    }

    @Override
    public CartEntity getCartByUsername(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        //Get customer's cart
        String hql = "from CartEntity as cartEntity where cartEntity.username = :username";
        Query query = session.createQuery(hql);
        query.setString("username", username);
        transaction.commit();
        List result = query.list();
        if(result.size() > 0) {
            //Get Cart Contents
            return (CartEntity) result.get(0);
        }
        else {
            return  null;
        }
    }

}
