package com.easter.user.service;

import com.easter.user.dto.LoginRequest;
import com.easter.user.dto.LoginResponse;
import com.easter.user.dto.RegisterRequest;
import com.easter.user.dto.RegisterResponse;
import com.easter.user.entity.Token;
import com.easter.user.entity.TokenRepository;
import com.easter.user.entity.User;
import com.easter.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        Optional<User> userOptional = userRepository.findByUsername(registerRequest.getUsername());
        if (userOptional.isPresent()) {
            throw new RuntimeException("用户名已存在");
        }

        User user = userRepository.save(new User(registerRequest.getUsername(), registerRequest.getPassword()));
        Token token = tokenRepository.save(new Token(user.getId()));

        return new RegisterResponse(user.getId(), token.getId());
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent() && !userOptional.get().getPassword().equals(loginRequest.getPassword())) {
            throw new Exception("用户名或密码错误");
        }

        Optional<Token> tokenOptional = tokenRepository.findByUserId(userOptional.get().getId());
        if (tokenOptional.isPresent()) {
            loginResponse.setTokenId(tokenOptional.get().getId());
        } else {
            throw new Exception("token失效，请刷新重试");
        }
        return loginResponse;
    }
}
