package com.springcommerce.controller.api;

import com.springcommerce.authentication.AuthenticationRequest;
import com.springcommerce.authentication.AuthenticationResponse;
import com.springcommerce.authentication.RegisterRequest;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request,
                                                               HttpServletRequest httpRequest,
                                                               HttpServletResponse httpResponse) {
        return ResponseEntity.ok(authenticationService.authenticate(request, httpRequest, httpResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestBody String token, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.logout(token, response));
    }

}
