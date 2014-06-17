package BookStore.Dao.Implement;
import BookStore.Dao.Super.SuperDao;
import BookStore.Dao.Interface.CustomerDao;
import BookStore.Entity.CartEntity;
import BookStore.Entity.CustomerEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pwwpche on 2014/6/7.
 */
@Repository
public class CustomerDaoImpl extends SuperDao implements CustomerDao  {

    @Override
    public CustomerEntity getCustomerByName(String username) {
        CustomerEntity customerEntity = null;
        try {
            System.out.println("getCustomerByName " + username);
            Session session = sessionFactory.openSession();
            if(username != null) {
                customerEntity = (CustomerEntity) session.get(CustomerEntity.class, username);
            }
            session.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return customerEntity;
    }

    @Override
    public List<CustomerEntity> getAllCustomers(int pageNum, int pageSize) {
        List<CustomerEntity> customerEntities = new ArrayList<CustomerEntity>();
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from CustomerEntity ");
            query.setFirstResult((pageNum - 1) * pageSize);
            query.setMaxResults(pageSize);
            transaction.commit();

            List result = query.list();
            for(int i = 0 ; i < result.size() ; i++)
            {
                customerEntities.add((CustomerEntity)result.get(i));
            }
            session.close();
            return customerEntities;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return customerEntities;
    }

    @Override
    public Long getCustomersTotal() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select count(*) from CustomerEntity as customer");
        Long result =  Long.parseLong(query.uniqueResult().toString());
        session.close();
        return result;
    }

    @Override
    public String createCustomer(CustomerEntity customerEntity) {
        if(getCustomerByName(customerEntity.getUsername()) == null)
        {
            Session session = sessionFactory.openSession();
            session.save(customerEntity);
            CartEntity cartEntity = new CartEntity();
            cartEntity.setUsername(customerEntity.getUsername());
            session.save(cartEntity);
            session.flush();
            session.close();
            return "success";
        }
        return "User exists!";

    }

    @Override
    public String removeCustomerByName(String name) {
        Session session = sessionFactory.openSession();
        try {
            CustomerEntity customerEntity = getCustomerByName(name);
            if (customerEntity == null) {
                return "User Not Found!";
            }
            session.delete(customerEntity);
            session.flush();
            session.close();
            return "success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String setCustomerByName(CustomerEntity customerEntity) {
        Session session = sessionFactory.openSession();
        CustomerEntity oldEntity =getCustomerByName(customerEntity.getUsername());
        if(oldEntity == null)
        {
            session.close();
            return "User Not Found!";
        }
        session.update(customerEntity);
        session.flush();
        session.close();
        return "success";
    }
}
