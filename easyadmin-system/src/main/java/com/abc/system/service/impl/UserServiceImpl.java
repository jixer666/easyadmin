package com.abc.system.service.impl;

import com.abc.common.util.AssertUtils;
import com.abc.common.domain.entity.User;
import com.abc.system.convert.UserConvert;
import com.abc.system.domain.vo.UserVO;
import com.abc.system.mapper.UserMapper;
import com.abc.system.service.RoleService;
import com.abc.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public User getUserById(Long userId) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");
        return userMapper.selectById(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        AssertUtils.isNotEmpty(username, "账号不能为空");
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public void saveUser(User user) {
        AssertUtils.isNotEmpty(user, "用户参数不能为空");
        userMapper.insert(user);
    }

    @Override
    public UserVO getUserInfoWithUiParam(Long userId) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");
        User user = getUserById(userId);
        List<String> roleKeys = roleService.getRoleKeysByUserId(userId);

        return UserConvert.convertToUserVO(user, roleKeys);
    }
}
