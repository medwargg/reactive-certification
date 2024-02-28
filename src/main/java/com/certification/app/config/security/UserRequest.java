package com.certification.app.config.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This request is used to get the be authenticated and get the token provided by the auth service.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String userName;
    private String password;

}
