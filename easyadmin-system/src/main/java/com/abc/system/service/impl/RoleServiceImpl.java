package com.abc.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.abc.common.core.service.BaseServiceImpl;
import com.abc.common.domain.vo.PageResult;
import com.abc.common.util.AssertUtils;
import com.abc.common.util.SecurityUtils;
import com.abc.system.convert.RoleConvert;
import com.abc.system.domain.dto.RoleDTO;
import com.abc.system.domain.entity.Role;
import com.abc.system.domain.vo.MenuVO;
import com.abc.system.domain.vo.RoleMenuTreeVO;
import com.abc.system.domain.vo.RoleVO;
import com.abc.system.mapper.RoleMapper;
import com.abc.system.service.MenuService;
import com.abc.system.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getRoleKeysByUserId(Long userId) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");
        return roleMapper.selectRoleKeysByUserId(userId);
    }

    @Override
    public PageResult getRolePageWithUiParam(RoleDTO roleDTO) {
        startPage();
        List<Role> roles = roleMapper.selectRoleList(roleDTO);
        List<RoleVO> roleVOList = pageList2CustomList(roles, (List<Role> list) -> {
            return BeanUtil.copyToList(list, RoleVO.class);
        });

        return buildPageResult(roleVOList);
    }

    @Override
    public void updateRole(RoleDTO roleDTO) {
        roleDTO.checkUpdateParams();
        Role menu = roleMapper.selectById(roleDTO.getRoleId());
        AssertUtils.isNotEmpty(menu, "菜单不存在");
        BeanUtils.copyProperties(roleDTO, menu);
        roleMapper.updateById(menu);
    }

    @Override
    public void saveRole(RoleDTO roleDTO) {
        roleDTO.checkSaveParams();
        Role menu = RoleConvert.buildDefaultRoleByRoleDTO(roleDTO);
        roleMapper.insert(menu);
    }


    @Override
    public RoleMenuTreeVO getRoleMenuTree(Long roleId) {
        AssertUtils.isNotEmpty(roleId, "角色ID不能为空");
        MenuService menuService = SpringUtil.getBean(MenuService.class);
        List<?> menus = menuService.getMenuPageWithUiParam(null).getList();
        List<Long> menuIds = menuService.getMenuIdsByRoleId(roleId);

        return RoleConvert.buildRoleMenuTree(menus, menuIds);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");
        return roleMapper.selectRoleIdsByUserId(userId);
    }

}
