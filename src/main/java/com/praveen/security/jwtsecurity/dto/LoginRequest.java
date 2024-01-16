package com.praveen.security.jwtsecurity.dto;


import javax.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = "username may not be blank or null") String userName,
                           @NotBlank(message = "password may not be blank or null") String password) {
}
