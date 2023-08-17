package com.app.travelmaker.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private static String uri = "/main/main";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Security가 요청을 가로챈 경우 사용자가 원래 요청했던 URI 정보를 저장한 객체
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // 있을 경우 URI 등 정보를 가져와서 사용
        if (savedRequest != null) {
            uri = savedRequest.getRedirectUrl();

            // 세션에 저장된 객체를 다 사용한 뒤에는 지워줘서 메모리 누수 방지
            requestCache.removeRequest(request, response);

            System.out.println(uri);
        }

        // 세션 Attribute 확인
        Enumeration<String> list = request.getSession().getAttributeNames();
        while (list.hasMoreElements()) {
            System.out.println(list.nextElement());
        }

        response.sendRedirect(uri);
    }

}
