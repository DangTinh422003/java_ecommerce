package com.springcommerce.service;

import com.springcommerce.configuration.ApplicationConfiguration;
import com.springcommerce.dto.request.DefaultRequest;
import com.springcommerce.dto.request.UserRequest;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.dto.response.UserResponse;
import com.springcommerce.entity.Cart;
import com.springcommerce.entity.Token;
import com.springcommerce.entity.User;
import com.springcommerce.exception.UserNotFoundException;
import com.springcommerce.repository.TokenRepository;
import com.springcommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    private ApplicationConfiguration applicationConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Modifying
    public void updatePassword(User user) {
        user.setPassword(applicationConfig.passwordEncoder().encode(user.getPassword()));
    }

    public UserResponse convertUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .order(user.getOrder().stream().map(orderService::convertOrder).toList())
                .date_created(user.getDate_created())
                .date_updated(user.getDate_updated())
                .build();
    }

    public List<UserResponse> getAllUser() {
        var users = userRepository.findAll().stream().map(this::convertUser);
        return users.toList();
    }

    public UserResponse getUserById(DefaultRequest request) {
        var user = userRepository.findById(request.getId()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return convertUser(user);
    }

    public UserResponse getUserByToken(String token) {
        var validToken = tokenRepository.findUserByToken(token);
        if (validToken != null) {
           var user = userRepository.findUserById(validToken.getUser().getId());
           return convertUser(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public MessageResponse updateUser(UserRequest request, String id) {
        var user = userRepository.findUserById(id);
        if (user != null) {
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setDate_updated(Date.from(java.time.Instant.now()));
            userRepository.save(user);
            return MessageResponse.builder()
                    .message("User updated")
                    .build();
        } else {
            return MessageResponse.builder()
                    .message("User not found")
                    .build();
        }
    }


    public MessageResponse deleteUser(String id) {
        var user = userRepository.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        } else {
            tokenRepository.deleteAllTokenByUserId(user.getId());
            cartService.deleteCartOfUser(user);
            orderService.deleteOrderOfUser(user);
            userRepository.delete(user);
            return MessageResponse.builder()
                    .message("Delete user successfully")
                    .build();
        }
    }
}
