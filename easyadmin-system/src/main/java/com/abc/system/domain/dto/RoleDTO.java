package com.abc.system.domain.dto;

import com.abc.common.util.AssertUtils;
import lombok.Data;

@Data
public class RoleDTO {

    private Long roleId;

    private String roleName;

    private String roleKey;

    public void checkUpdateParams() {
        AssertUtils.isNotEmpty(this, "角色参数不能为空");
        AssertUtils.isNotEmpty(roleId, "角色ID不能为空");

    }

    public void checkSaveParams() {
        AssertUtils.isNotEmpty(this, "角色参数不能为空");
        AssertUtils.isNotEmpty(roleName, "角色名称不能为空");
        AssertUtils.isNotEmpty(roleKey, "角色字符不能为空");
    }
}
