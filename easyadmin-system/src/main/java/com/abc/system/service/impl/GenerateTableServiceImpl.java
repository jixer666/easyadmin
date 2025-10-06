package com.abc.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.abc.common.constant.CommonConstants;
import com.abc.common.core.generate.VelocityInitializer;
import com.abc.common.core.service.BaseServiceImpl;
import com.abc.common.domain.vo.PageResult;
import com.abc.common.util.AssertUtils;
import com.abc.common.util.GenerateUtils;
import com.abc.system.convert.GenerateTableConvert;
import com.abc.system.domain.dto.GenerateTableDTO;
import com.abc.system.domain.entity.GenerateTable;
import com.abc.system.domain.vo.GenerateTablePreviewVO;
import com.abc.system.domain.vo.GenerateTableVO;
import com.abc.system.mapper.GenerateTableMapper;
import com.abc.system.service.GenerateTableColumnService;
import com.abc.system.service.GenerateTableService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.List;

@Service
public class GenerateTableServiceImpl extends BaseServiceImpl<GenerateTableMapper, GenerateTable> implements GenerateTableService {

    @Autowired
    private GenerateTableMapper generateTableMapper;

    @Autowired
    private GenerateTableColumnService generateTableColumnService;

    @Override
    public PageResult getGenerateTablePageWithUiParam(GenerateTableDTO generateTableDTO) {
        startPage();
        List<GenerateTable> generateTables = generateTableMapper.selectGenerateTableList(generateTableDTO);
        List<GenerateTableVO> generateTableVOList = pageList2CustomList(generateTables, (List<GenerateTable> list) -> {
            return BeanUtil.copyToList(list, GenerateTableVO.class);
        });

        return buildPageResult(generateTableVOList);
    }

    @Override
    public void updateGenerateTable(GenerateTableDTO generateTableDTO) {
        generateTableDTO.checkUpdateParams();
        GenerateTable generateTable = generateTableMapper.selectById(generateTableDTO.getGenTableId());
        AssertUtils.isNotEmpty(generateTable, "菜单不存在");
        BeanUtils.copyProperties(generateTableDTO, generateTable);
        generateTableMapper.updateById(generateTable);
    }

    @Override
    public void saveGenerateTable(GenerateTableDTO generateTableDTO) {
        generateTableDTO.checkSaveParams();
        GenerateTable generateTable = GenerateTableConvert.buildDefaultGenerateTableByGenerateTableDTO(generateTableDTO);
        generateTableMapper.insert(generateTable);
    }

    @Override
    public void deleteGenerateTable(GenerateTableDTO generateTableDTO) {
        generateTableDTO.checkDeleteParams();

        generateTableMapper.deleteBatchIds(generateTableDTO.getGenTableIds());
    }


    @Override
    public PageResult getGenerateDbPageWithUiParam(GenerateTableDTO generateTableTableDTO) {
        startPage();
        List<GenerateTable> generateTables = generateTableMapper.selectDbTableList(generateTableTableDTO);
        List<GenerateTableVO> generateTableVOList = pageList2CustomList(generateTables, (List<GenerateTable> list) -> {
            return BeanUtil.copyToList(list, GenerateTableVO.class);
        });

        return buildPageResult(generateTableVOList);
    }

    @Override
    @Transactional
    public void importTable(GenerateTableDTO generateTableTableDTO) {
        generateTableTableDTO.checkImportParams();
        List<GenerateTable> importTables = generateTableMapper.selectDbTableListByNames(generateTableTableDTO.getTableNames());
        importTablesAndColumn(importTables);
    }

    private void importTablesAndColumn(List<GenerateTable> importTables) {
        if (CollUtil.isEmpty(importTables)) {
            return;
        }
        for (GenerateTable generateTable : importTables) {
            GenerateTableConvert.initDefaultGenerateTable(generateTable);
            generateTableMapper.insert(generateTable);
            generateTableColumnService.importTableColumn(generateTable.getTableName(), generateTable.getGenTableId());
        }

    }

    @Override
    public GenerateTablePreviewVO previewCode(Long genTableId) {
        AssertUtils.isNotEmpty(genTableId, "生成代码表ID不能为空");
        GenerateTable table = getGenerateTableByGenTableId(genTableId);
        AssertUtils.isNotEmpty(table, "生成代码表不存在");

        VelocityInitializer.initVelocity();
        VelocityContext context = GenerateTableConvert.buildVelocityContextByGenerateTable(table);

        GenerateTablePreviewVO previewVO = new GenerateTablePreviewVO();
        for (String template : GenerateUtils.getTemplateList()) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CommonConstants.UTF8);
            tpl.merge(context, sw);
            previewVO.put(template, sw.toString());
        }

        return previewVO;
    }

    @Override
    public GenerateTable getGenerateTableByGenTableId(Long genTableId) {
        AssertUtils.isNotEmpty(genTableId, "生成代码表ID不能为空");

        return generateTableMapper.selectGenerateTableByGenTableId(genTableId);
    }
}
