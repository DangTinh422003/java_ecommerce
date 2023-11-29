package com.springcommerce.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ApiAdminController {

    @PostMapping("/get_all_users")
    public String getAllUsers() {
        return "redirect:/api/user/get_all_user";
    }
}
