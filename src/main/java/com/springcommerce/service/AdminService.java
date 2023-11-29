package com.springcommerce.service;

import com.springcommerce.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AuthenticationService authenticationService;


    public boolean isAdminLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String role = null;

        if (cookies == null) {
            return false;
        }

        for (Cookie cookie: cookies) {
            if(cookie.getName().equals("role")) {
                role = cookie.getValue();
                if (role.equals("ADMIN")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
