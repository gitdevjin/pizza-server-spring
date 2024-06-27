package project.pizza.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import project.pizza.web.SessionConst;

@Slf4j
public class AdminAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        log.info("[AdminAuthInterceptor] -- Authorization Start");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.Admin_Member) == null) {
            log.info("AdminAuthInterceptor] -- Unauthorized User");
            response.sendRedirect("/admin/login");
            return false;
        }

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("[AdminAuthInterceptor] -- Admin Member Authorized");
    }
}
