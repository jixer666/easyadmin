package com.abc.system.service;

import com.abc.system.domain.dto.LoginDTO;
import com.abc.system.domain.dto.RegisterDTO;

public interface IndexService {
    String login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);
}
