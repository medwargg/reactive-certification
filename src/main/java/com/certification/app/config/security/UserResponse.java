package com.certification.app.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// This class returns the token's information in case the credentials provided were valid.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String token;
    private Date expirationDate;

}
