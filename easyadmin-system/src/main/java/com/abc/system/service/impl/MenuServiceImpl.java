package com.abc.system.service.impl;

import com.abc.common.constant.MenuConstants;
import com.abc.common.core.service.BaseServiceImpl;
import com.abc.common.domain.vo.PageResult;
import com.abc.common.util.AssertUtils;
import com.abc.system.convert.MenuConvert;
import com.abc.system.domain.dto.MenuDTO;
import com.abc.system.domain.entity.Menu;
import com.abc.system.domain.vo.MenuVO;
import com.abc.system.mapper.MenuMapper;
import com.abc.system.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenusByUserId(Long userId) {
        return menuMapper.selectMenusByUserId(userId);
    }

    @Override
    public PageResult getMenuPageWithUiParam(MenuDTO menuDTO) {
        startPage();
        List<Menu> menus = getMenusByUserId(null);
        List<MenuVO> menuVOList = pageList2CustomList(menus,
                (List<Menu> list) -> buildMenuVO(MenuConstants.DEFAULT_MENU_PARENT_ID, menus)
        );

        return buildPageResult(menuVOList);
    }

    private List<MenuVO> buildMenuVO(Long menuId, List<Menu> menus) {
        List<MenuVO> routers = new ArrayList<>();

        List<Menu> filterMenus = menus.stream().filter(item -> item.getParentId().equals(menuId)).sorted(new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o1.getOrderNum() - o2.getOrderNum();
            }
        }).collect(Collectors.toList());

        for (Menu menu : filterMenus) {
            List<MenuVO> children = buildMenuVO(menu.getMenuId(), menus);
            routers.add(MenuConvert.convertToMenuVO(menu, children));
        }

        return routers;
    }

    @Override
    public void updateMenu(MenuDTO menuDTO) {
        menuDTO.checkUpdateParams();
        Menu menu = menuMapper.selectById(menuDTO.getMenuId());
        AssertUtils.isNotEmpty(menu, "菜单不存在");
        BeanUtils.copyProperties(menuDTO, menu);
        menuMapper.updateById(menu);
    }

    @Override
    public void saveMenu(MenuDTO menuDTO) {
        menuDTO.checkSaveParams();
        Menu menu = MenuConvert.buildDefaultMenuByMenuDTO(menuDTO);
        menuMapper.insert(menu);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        AssertUtils.isNotEmpty(roleId, "角色ID不存在");

        return menuMapper.selectMenuIdsByRoleId(roleId);
    }
}
