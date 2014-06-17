package BookStore.Dao.Interface;

import BookStore.Entity.CustomerEntity;

import java.util.List;

/**
 * Created by pwwpche on 2014/6/7.
 */
public interface CustomerDao {
    public CustomerEntity getCustomerByName(String name);
    public List<CustomerEntity> getAllCustomers(int pageNum, int pageSize);
    public Long getCustomersTotal();
    public String createCustomer(CustomerEntity customerEntity);
    public String removeCustomerByName(String name);
    public String setCustomerByName(CustomerEntity customerEntity);
}
