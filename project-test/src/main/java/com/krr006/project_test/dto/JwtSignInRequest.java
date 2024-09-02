package com.krr006.project_test.dto;

import lombok.Data;

@Data
public class JwtSignInRequest {
    private String username;
    private String password;
}
