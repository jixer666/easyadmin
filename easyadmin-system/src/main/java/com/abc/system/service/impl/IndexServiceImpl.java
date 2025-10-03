package com.abc.system.service.impl;

import com.abc.system.domain.dto.LoginDTO;
import com.abc.common.domain.dto.LoginUserDTO;
import com.abc.system.domain.dto.RegisterDTO;
import com.abc.system.factory.LoginStrategyFactory;
import com.abc.system.service.IndexService;
import com.abc.system.service.TokenService;
import com.abc.system.strategy.login.AuthStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private TokenService tokenService;

    @Override
    public String login(LoginDTO loginDTO) {
        AuthStrategy authStrategy = LoginStrategyFactory.getAuthStrategy(loginDTO.getAuthType());
        LoginUserDTO loginUser = authStrategy.authenticate(loginDTO);
        return tokenService.createToken(loginUser);
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        AuthStrategy authStrategy = LoginStrategyFactory.getAuthStrategy(registerDTO.getAuthType());
        authStrategy.doRegister(registerDTO);
    }
}
