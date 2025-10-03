package com.abc.system.service;

import com.abc.common.domain.entity.User;
import com.abc.system.domain.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UserService extends IService<User> {

    User getUserById(Long userId);

    User getUserByUsername(String username);

    void saveUser(User user);

    UserVO getUserInfoWithUiParam(Long userId);
}
