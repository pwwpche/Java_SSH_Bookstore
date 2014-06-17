package Utility; /**
 * Created by pwwpche on 2014/5/6.
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

@WebListener()
public class SessionListener implements
        HttpSessionListener {
    //Session 集合
    public static  Map<String,HttpSession>  session_map = new  HashMap<String,HttpSession>();
    /**
     *  创建一个 session
     */
    public void  sessionCreated(HttpSessionEvent sessionEvent) {
        String session_id=sessionEvent.getSession().getId();
        System. out .println( "new Session,id is: " +session_id );
        session_map .put(session_id, sessionEvent.getSession());
    }
    /**
     *  销毁
     */
    public void  sessionDestroyed(HttpSessionEvent sessionEvent) {
        System. out .println( "Session removed,id is: " + sessionEvent.getSession().getId());
        session_map .remove(sessionEvent.getSession());
    }
    /**
     *  获得 session
     *  @param  session_id
     *  @return
     */
    public static  HttpSession getSessionById(String session_id){
        System. out .println("Session get,id is: " + session_id);
        System. out .println("Session content is: " + (session_map.get(session_id).getAttribute("inputtext") == null ? "null" : session_map.get(session_id).getAttribute("inputtext").toString()));
        return session_map .get(session_id);
    }

    public static void  removeSessionById(String session_id){
        session_map .remove(session_id);
    }
    // Public constructor is required by servlet spec
    public SessionListener() {
    }

}
