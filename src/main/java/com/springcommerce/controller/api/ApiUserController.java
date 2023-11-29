package com.springcommerce.controller.api;

import com.springcommerce.dto.request.DefaultRequest;
import com.springcommerce.dto.request.UserRequest;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.dto.response.UserResponse;
import com.springcommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ApiUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/get_all_user")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping("/get_user")
    public ResponseEntity<UserResponse> getUserById(@RequestBody DefaultRequest request) {
        return ResponseEntity.ok(userService.getUserById(request));
    }

    @PostMapping("/get_user_by_token")
    public ResponseEntity<UserResponse> getUserByToken(@RequestBody String token) {
        return ResponseEntity.ok(userService.getUserByToken(token));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody UserRequest request, @PathVariable("id") String id) {
        return ResponseEntity.ok(userService.updateUser(request, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
