package com.springcommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcommerce.authentication.AuthenticationRequest;
import com.springcommerce.authentication.AuthenticationResponse;
import com.springcommerce.authentication.RegisterRequest;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.entity.Cart;
import com.springcommerce.entity.Token;
import com.springcommerce.entity.User;
import com.springcommerce.exception.UserExistException;
import com.springcommerce.exception.UserNotFoundException;
import com.springcommerce.repository.TokenRepository;
import com.springcommerce.repository.UserRepository;
import com.springcommerce.util.Role;
import com.springcommerce.util.TokenType;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import java.util.Date;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CartService cartService;

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws UserNotFoundException, IOException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername());
        if (user != null) {
            var accessToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);
            Cookie cookie = new Cookie("role", user.getRole().toString());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 24 * 30);
            httpResponse.addCookie(cookie);
            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public AuthenticationResponse register(RegisterRequest request) throws UserExistException {
        if (userRepository.existsUserByUsername(request.getUsername())) {
            throw new UserExistException("User already exist");
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .date_created(Date.from(java.time.Instant.now()))
                .date_updated(null)
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        cartService.addCart(Cart.builder()
                .user(savedUser)
                .total(0.0F)
                .date_created(Date.from(java.time.Instant.now()))
                .build());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public MessageResponse logout(String token, HttpServletResponse response) {
        System.out.println(token);
        var validToken = tokenRepository.findByToken(token);
        if (validToken.isPresent()) {
            var user = validToken.get().getUser();
            revokeAllUserTokens(user);
            Cookie cookie = new Cookie("role", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return MessageResponse.builder()
                    .message("Logout success")
                    .build();
        }
        return MessageResponse.builder()
                .message("Logout failed")
                .build();
    }

    protected void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .date_created(Date.from(java.time.Instant.now()))
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException, java.io.IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String username;


        if (authHeader == null || !authHeader.startsWith(TokenType.BEARER.getTokenType())) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractEmail(refreshToken);
        if (username != null) {
            var user = this.userRepository.findByUsername(username);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }

        }
    }

    protected void revokeAllUserTokens(User user) {
        var validToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validToken.isEmpty()) {
            return;
        }
        validToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            token.setDate_updated(Date.from(java.time.Instant.now()));
        });
        tokenRepository.saveAll(validToken);
    }
}
