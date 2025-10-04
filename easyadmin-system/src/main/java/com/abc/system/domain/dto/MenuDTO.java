package com.abc.system.domain.dto;

import com.abc.common.util.AssertUtils;
import lombok.Data;

import java.util.Date;

@Data
public class MenuDTO {

    private Long menuId;

    private Long parentId;

    private String menuName;

    private String icon;

    private String path;

    private String component;

    private Integer orderNum;

    private Integer menuType;

    private Integer hidden;

    private Integer status;

    public void checkUpdateParams() {
        AssertUtils.isNotEmpty(this, "菜单参数不能为空");
        AssertUtils.isNotEmpty(menuId, "菜单ID不能为空");
    }

    public void checkSaveParams() {
        AssertUtils.isNotEmpty(this, "菜单参数不能为空");
        AssertUtils.isNotEmpty(menuName, "菜单名称不能为空");
        AssertUtils.isNotEmpty(path, "路由路径不能为空");
        AssertUtils.isNotEmpty(component, "组件路径不能为空");
        AssertUtils.isNotEmpty(menuType, "菜单类型不能为空");
        AssertUtils.isNotEmpty(orderNum, "顺序不能为空");
    }
}
