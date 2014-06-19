package BookStore.Service.Implement;

import BookStore.Dao.Interface.CustomerDao;
import BookStore.Entity.CustomerEntity;
import BookStore.Service.Interface.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by pwwpche on 2014/6/7.
 *
 */
@Service
public class LoginServiceImpl implements LoginService{
    @Resource
    CustomerDao customerDao;
    @Override
    public String verify(String username, String password) {
        CustomerEntity customerEntity = customerDao.getCustomerByName(username);
        if(customerEntity == null)
        {
            return "User Not Found";
        }
        else
        {
            if(customerEntity.getPassword().equals(password))
            {
                return "success";
            }
            else
            {
                return "Password Not Correct";
            }
        }
    }
}
