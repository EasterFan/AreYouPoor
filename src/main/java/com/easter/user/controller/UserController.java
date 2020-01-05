package com.easter.user.controller;

import com.easter.user.dto.LoginRequest;
import com.easter.user.dto.LoginResponse;
import com.easter.user.dto.RegisterRequest;
import com.easter.user.dto.RegisterResponse;
import com.easter.user.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @JsonView(RegisterResponse.ViewAll.class)
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        return userService.register(registerRequest);
    }

    @GetMapping("/login")
    @JsonView(LoginResponse.class)
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        return userService.login(loginRequest);
    }

}
