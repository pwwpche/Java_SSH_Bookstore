package BookStore.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by pwwpche on 2014/6/15.
 *
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获得在下面代码中要用的request,response,session对象
        //把ServletRequest和ServletResponse转换成真正的类型
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpSession session = req.getSession();

        String path = req.getRequestURI();
        String username =  (String)session.getAttribute("username");

        if(!path.contains(".jsp") || path.contains("Register") || path.contains("index")
                || path.contains("shopping")) {
            chain.doFilter(request, response);
            return;
        }

        if (username == null || "".equals(username)) {
            resp.sendRedirect("/index.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
