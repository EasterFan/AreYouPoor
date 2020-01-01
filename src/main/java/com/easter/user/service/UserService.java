package com.easter.user.service;

import com.easter.user.dto.RegisterRequest;
import com.easter.user.dto.RegisterResponse;
import com.easter.user.entity.Token;
import com.easter.user.entity.TokenRepository;
import com.easter.user.entity.User;
import com.easter.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public RegisterResponse register(RegisterRequest registerRequest) {
        userRepository.save(new User(registerRequest.getUsername(), registerRequest.getPassword()));
        tokenRepository.save(new Token(registerRequest.getUserId()));
        return tokenRepository.findByUserId(registerRequest.getUserId()).orElse(null);
    }
}
