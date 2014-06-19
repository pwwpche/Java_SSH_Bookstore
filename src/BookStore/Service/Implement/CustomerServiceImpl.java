package BookStore.Service.Implement;

import BookStore.Dao.Interface.CustomerDao;
import BookStore.Entity.CustomerEntity;
import BookStore.Service.Interface.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pwwpche on 2014/6/7.
 *
 */

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    CustomerDao customerDao;

    @Override
    public String addCustomer(CustomerEntity customerEntity) {
        return customerDao.createCustomer(customerEntity);
    }

    @Override
    public CustomerEntity getCustomer(String name) {
        return customerDao.getCustomerByName(name);
    }

    @Override
    public List<CustomerEntity> getAllCustomers(int pageNum, int pageSize) {
        return  customerDao.getAllCustomers(pageNum, pageSize);
    }

    @Override
    public Long getCustomerTotal() {
        return customerDao.getCustomersTotal();
    }

    @Override
    public String modifyCustomer(CustomerEntity customerEntity) {
        return customerDao.setCustomerByName(customerEntity);
    }

    @Override
    public String removeCustomer(String name) {
        return customerDao.removeCustomerByName(name);
    }
    /*
    @Override
    public String getAllCustomerToJsonString() {
        return getAllCustomerToJsonObject().toString().replace("\\", "");
    }

    @Override
    public JSONObject getAllCustomerToJsonObject() {
        List<CustomerEntity> customers = customerDao.getAllCustomers();
        JSONArray array = new JSONArray();
        JSONObject easyui = new JSONObject();
        try {
            for (int i = 0; i < customers.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("username", customers.get(i).getUsername());
                object.put("password", customers.get(i).getPassword());
                object.put("birthday", customers.get(i).getBirthday().toString());
                object.put("email", customers.get(i).getEmail());
                System.out.println("username:" + customers.get(i).getUsername() + " password:" + customers.get(i).getPassword());
                System.out.println(object.toString());
                array.put(object);

                //Wrap up into easyUI format
                easyui.put("total", customers.size());
                easyui.put("rows", array);
            }
        }catch (Exception e)
        {
            System.out.println(e.getCause());
        }
        System.out.println("getAllCustomerToJson() done!");
        System.out.println(easyui.toString().replace("\\",""));
        return easyui;
    }
*/

}
