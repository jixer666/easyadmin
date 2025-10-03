package com.abc.system.service.impl;

import com.abc.common.util.AssertUtils;
import com.abc.system.domain.entity.Role;
import com.abc.system.mapper.RoleMapper;
import com.abc.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getRoleKeysByUserId(Long userId) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");
        return roleMapper.selectRoleKeysByUserId(userId);
    }






}
