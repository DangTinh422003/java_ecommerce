package com.springcommerce.controller;

import com.springcommerce.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request) {
        if (adminService.isAdminLogin(request)) {
            return "pages/admin/dashboard";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/user")
    public String user(HttpServletRequest request) {
        if (adminService.isAdminLogin(request)) {
            return "pages/admin/components/user/user";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "pages/auth/adminlogin";
    }

    @GetMapping("/user/update/{id}")
    public String updateUser(HttpServletRequest request, @PathVariable("id") String id) {
        if (adminService.isAdminLogin(request)) {
            return "pages/user/update";
        } else {
            return "redirect:/error";
        }
    }
}
