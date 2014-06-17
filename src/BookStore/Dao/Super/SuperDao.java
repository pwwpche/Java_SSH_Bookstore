package BookStore.Dao.Super;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * Created by pwwpche on 2014/6/7.
 */
@Component
public class SuperDao {
    @Resource
    public SessionFactory sessionFactory;
}
