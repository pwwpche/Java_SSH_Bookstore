package BookStore.Service.Interface;

import BookStore.Entity.CustomerEntity;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pwwpche on 2014/6/7.
 */

public interface CustomerService {
    public String addCustomer(CustomerEntity customerEntity);
    public CustomerEntity getCustomer(String name);
    public List<CustomerEntity> getAllCustomers(int pageNum, int pageSize);
    public Long getCustomerTotal();
    public String modifyCustomer(CustomerEntity customerEntity);
    public String removeCustomer(String name);
}
