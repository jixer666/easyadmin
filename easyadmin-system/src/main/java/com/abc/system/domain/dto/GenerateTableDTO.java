package com.abc.system.domain.dto;

import cn.hutool.core.collection.CollUtil;
import com.abc.common.util.AssertUtils;
import lombok.Data;

import java.util.List;

@Data
public class GenerateTableDTO {

    private Long genTableId;

    private String tableName;

    // 用于批量删除
    private List<Long> genTableIds;

    // 用于导入表结构
    private List<String> tableNames;

    public void checkSaveParams() {
    }

    public void checkDeleteParams() {
    }

    public void checkUpdateParams() {
    }

    public void checkImportParams() {
        AssertUtils.isNotEmpty(this, "代码导入参数不能为空");
        AssertUtils.isTrue(CollUtil.isNotEmpty(tableNames), "表结构名称列表不能为空");
    }
}
