package git.info.interceptors;

import git.info.services.MySessionServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@AllArgsConstructor
public class MainInterceptor implements HandlerInterceptor {

    MySessionServices sessionServices;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);

        Cookie tkn = WebUtils.getCookie(request, "tkn");        // caching github auth token in cookie
        if (tkn != null &&
                !tkn.getValue().equals(sessionServices.getToken())) {

            sessionServices.setToken(tkn.getValue());
        }

        if (System.currentTimeMillis() - session.getLastAccessedTime() > 1000*5) {   // 5s refresh
            response.sendRedirect("/git2");
        }

        if ((Integer) request
                .getAttribute("javax.servlet.error.status_code") != null) {     // catching every error

            response.sendRedirect("/errors?nr=" + (Integer) request
                    .getAttribute("javax.servlet.error.status_code"));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
