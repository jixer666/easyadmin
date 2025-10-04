package com.abc.system.mapper;

import com.abc.system.domain.dto.RoleDTO;
import com.abc.system.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<String> selectRoleKeysByUserId(Long userId);

    List<Long> selectRoleIdsByUserId(Long userId);

    List<Role> selectRoleList(RoleDTO roleDTO);
}
