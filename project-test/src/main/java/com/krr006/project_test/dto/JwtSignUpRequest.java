package com.krr006.project_test.dto;

import lombok.Data;

@Data
public class JwtSignUpRequest {
    private String email;
    private String username;
    private String password;
}

