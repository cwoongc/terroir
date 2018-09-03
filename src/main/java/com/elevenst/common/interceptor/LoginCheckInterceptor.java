package com.elevenst.common.interceptor;

import com.elevenst.common.auth.AuthCheckerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthCheckerService authCheckerService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        try {

            if(request.getMethod().equals(HttpMethod.OPTIONS.name())) return super.preHandle(request, response, handler);

            String requestURI = request.getRequestURI();

            log.info("requestURI:" + requestURI);

            boolean isAuth = authCheckerService.authCheck(request, response);

            log.info("isAuth:" + isAuth);

            if (!isAuth) {
                //redirect login page
                return false;
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return super.preHandle(request, response, handler);

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        super.afterCompletion(request, response, handler, ex);

    }

}
