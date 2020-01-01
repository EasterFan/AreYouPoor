package com.easter.user.controller;

import com.easter.user.dto.RegisterRequest;
import com.easter.user.dto.RegisterResponse;
import com.easter.user.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @JsonView(RegisterResponse.class)
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }
}
