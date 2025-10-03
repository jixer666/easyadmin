package com.abc.system.service;

import com.abc.system.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<String> getRoleKeysByUserId(Long userId);


}
