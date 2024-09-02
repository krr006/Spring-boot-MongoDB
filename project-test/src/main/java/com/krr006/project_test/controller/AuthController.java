package com.krr006.project_test.controller;

import com.krr006.project_test.dto.JwtResponse;
import com.krr006.project_test.dto.JwtSignInRequest;
import com.krr006.project_test.dto.JwtSignUpRequest;
import com.krr006.project_test.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public JwtResponse signUp(@RequestBody @Valid JwtSignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> signIn(@RequestBody @Valid JwtSignInRequest request) {
        return authenticationService.signIn(request);
    }
}
