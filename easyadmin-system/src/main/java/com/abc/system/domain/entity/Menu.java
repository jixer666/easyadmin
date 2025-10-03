package com.abc.system.domain.entity;

import com.abc.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@TableName("tb_menu")
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends BaseEntity {

    @TableId
    private Long menuId;

    private Long parentId;

    private String menuName;

    private String path;

    private String component;

    private Integer menuType;

    private Integer orderNum;

    private String perms;

    private String icon;

    private Long userId;

}
