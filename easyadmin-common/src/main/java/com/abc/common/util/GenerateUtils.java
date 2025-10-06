package com.abc.common.util;

import com.abc.common.constant.GeneratorConstants;
import org.apache.commons.lang3.RegExUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateUtils {

    public static String getClassName(String tableName) {
        if (GeneratorConstants.autoRemovePre && tableName.startsWith(GeneratorConstants.tablePrefix)) {
            tableName = tableName.replace(GeneratorConstants.tablePrefix, "");
        }
        return StringUtils.convertToCamelCase(tableName);
    }

    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        if (lastIndex == -1) {
            return "";
        }
        return packageName.substring(lastIndex + 1);
    }

    public static String getBusinessName(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    public static String getFunctionName(String tableComment) {
        return RegExUtils.replaceAll(tableComment, "(?:表)", "");
    }


    public static String getColumnType(String columnType) {
        if (columnType.contains("(")) {
            return StringUtils.substringBefore(columnType, "(");
        }
        return columnType;
    }

    public static String getJavaType(String columnType) {
        if (GeneratorConstants.COLUMN_TYPE_STR.contains(columnType)) {
            return GeneratorConstants.TYPE_STRING;
        } else if (GeneratorConstants.COLUMN_TYPE_TEXT.contains(columnType)) {
            return GeneratorConstants.TYPE_STRING;
        } else if (GeneratorConstants.COLUMN_TYPE_TIME.contains(columnType)) {
            return GeneratorConstants.TYPE_DATE;
        } else if (GeneratorConstants.COLUMN_TYPE_INT.contains(columnType)) {
            return GeneratorConstants.TYPE_INTEGER;
        } else if (GeneratorConstants.COLUMN_TYPE_BIGINT.contains(columnType)) {
            return GeneratorConstants.TYPE_LONG;
        } else if (GeneratorConstants.COLUMN_TYPE_FLOAT.contains(columnType)) {
            return GeneratorConstants.TYPE_BIGDECIMAL;
        }
        return GeneratorConstants.TYPE_STRING;
    }

    public static List<String> getTemplateList() {
        return Arrays.asList(
                "vm/java/entity.java.vm",
                "vm/java/controller.java.vm",
                "vm/java/service.java.vm",
                "vm/java/serviceImpl.java.vm",
                "vm/java/mapper.java.vm",
                "vm/js/api.js.vm",
                "vm/vue/index.vue.vm"
        );
    }



}
