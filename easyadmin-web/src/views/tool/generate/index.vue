<template>
  <div class="generateTable-container">
    <div>
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="角色名称">
          <el-input v-model="searchForm.generateTableName" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList" size="medium">搜索</el-button>
          <el-button @click="searchForm = {}" size="medium">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="btn-div">
      <div>
        <el-button plain size="mini" type="danger" @click="handleDelete(multipleSelectionIds)" :disabled="multipleSelectionIds.length === 0">批量删除</el-button>
        <el-button plain size="mini" type="success" @click="handleImport">导入</el-button>
      </div>
      <div>

      </div>
    </div>
    <div>
      <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column label="表名称" align="center" key="tableName" prop="tableName" :show-overflow-tooltip="true" />
        <el-table-column label="表注释" align="center" key="tableComment" prop="tableComment" :show-overflow-tooltip="true" />
        <el-table-column label="状态" align="center" key="status" width="100">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.common_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        </el-table-column>
        <el-table-column label="更新时间" align="center" prop="updateTime" width="160">
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="300"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handlePreview(scope.row)"
            >预览</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete([scope.row.generateTableId])"
            >删除</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-upload"
              @click="handleUpdate(scope.row)"
            >生成代码</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      v-show="searchForm.total > 0"
      :total="searchForm.total"
      :page.sync="searchForm.pageNum"
      :limit.sync="searchForm.pageSize"
      @pagination="getList"
    />
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-form ref="form" :model="form" label-width="80px" :rules="rules">
        <el-form-item label="角色名称" prop="generateTableName">
          <el-input v-model="form.generateTableName"></el-input>
        </el-form-item>
        <el-form-item label="角色字符" prop="generateTableKey">
          <el-input v-model="form.generateTableKey"></el-input>
        </el-form-item>
        <el-form-item label="角色状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="parseInt(item.value)" v-for="(item, index) in dict.type.common_status" :key="index">{{ item.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit" :loading="submitLoading">确 定</el-button>
      </span>
    </el-dialog>

    <el-drawer
      :title="dialogGenTableDbTitle"
      :visible.sync="drawerGenTableDbVisible"
      direction="rtl"
      size="50%"
      :before-close="handleClose">
      <div class="drawer-div">
        <el-table v-loading="genDbLoading" :data="genTableDbList">
          <el-table-column label="表名称" align="center" key="tableName" prop="tableName" :show-overflow-tooltip="true" />
          <el-table-column label="表注释" align="center" key="tableComment" prop="tableComment" :show-overflow-tooltip="true" />
          <el-table-column label="创建时间" align="center" key="createTime" prop="createTime" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="更新时间" align="center" key="updateTime" prop="updateTime" :show-overflow-tooltip="true" width="150"/>
          <el-table-column
            label="操作"
            align="center"
            width="100"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleImportGenTableDb([scope.row.tableName])"
              >导入</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="searchDbForm.total > 0"
          :total="searchDbForm.total"
          :page.sync="searchDbForm.pageNum"
          :limit.sync="searchDbForm.pageSize"
          @pagination="getList"
        />

      </div>

    </el-drawer>

    <el-dialog :title="dialogTitle" :visible.sync="dialogPreviewVisible" width="80%" top="5vh" append-to-body>
      <el-tabs v-model="previewTabActiveName">
        <el-tab-pane
          v-for="(value, key) in previewData"
          :label="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :name="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :key="key"
        >
          <el-link :underline="false" icon="el-icon-document-copy" v-clipboard:copy="value" v-clipboard:success="clipboardSuccess" style="float:right">复制</el-link>
          <pre><code class="hljs" v-html="highlightedCode(value, key)"></code></pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

  </div>
</template>

<script>
import {
  getGenerateTablePage,
  addGenerateTable,
  updateGenerateTable,
  deleteGenerateTable,
  getGenerateTableDbPage, importGenerateTable, previewGenerateTable
} from '@/api/system/generateTable';
import hljs from "highlight.js/lib/highlight";
import "highlight.js/styles/github-gist.css";
import clipboard from '@/directive/clipboard/index.js';
hljs.registerLanguage("java", require("highlight.js/lib/languages/java"));
hljs.registerLanguage("xml", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("html", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("vue", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("javascript", require("highlight.js/lib/languages/javascript"));
hljs.registerLanguage("sql", require("highlight.js/lib/languages/sql"));

export default {
  name: 'GenerateTable',
  dicts: ['common_status'],
  directives: {
    clipboard
  },
  data() {
    return {
      searchForm: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
      searchDbForm: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
      form: {},
      tableList: [],
      genTableDbList: [],
      loading: false,
      submitLoading: false,
      genDbLoading: false,
      dialogVisible: false,
      dialogPreviewVisible: false,
      drawerGenTableDbVisible: false,
      dialogTitle: null,
      dialogGenTableDbTitle: null,
      rules: {
        generateTableName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        generateTableKey: [
          { required: true, message: "角色字符不能为空", trigger: "blur" }
        ],
      },
      multipleSelectionIds: [],
      previewData: {},
      previewTabActiveName: "entity.java"
    }
  },
  mounted() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      getGenerateTablePage(this.searchForm).then(res => {
        this.tableList = res.data.list;
        this.searchForm.total = parseInt(res.data.total);
        this.loading = false;
      }).catch(error => {
        this.loading = false;
      })
    },
    onSubmit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.form.generateTableId != undefined) {
            updateGenerateTable(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.submitLoading = false;
              this.dialogVisible = false;
              this.getList();
            });
          } else {
            addGenerateTable(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.submitLoading = false;
              this.dialogVisible = false;
              this.getList();
            });
          }
        }
      });
    },
    handleUpdate(item) {
      this.form = item;
      this.dialogTitle = "修改角色";
      this.dialogVisible = true;
    },
    handleDelete(ids) {
      if (ids === null || ids.length === 0) {
        this.$modal.msgWarning("未选中角色列表");
        return;
      }
      this.$modal.confirm('是否确认删除角色编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return deleteGenerateTable({
          generateTableIds: ids
        });
      }).then(() => {
        this.$modal.msgSuccess("删除成功");
        this.loading = false;
        this.getList();
      }).catch((error) => {
        this.loading = false;
      });
    },
    handleClose() {
      this.form = {
        menuCheckStrictly: true,
      };
      this.dialogVisible = false;
      this.drawerGenTableDbVisible = false;
    },
    handleSelectionChange(val) {
      this.multipleSelectionIds = val.map(item => item.generateTableId);
    },
    handleImport() {
      this.dialogGenTableDbTitle = "导入数据库表";
      this.drawerGenTableDbVisible = true;
      this.getGenerateTableDbPage();
    },
    getGenerateTableDbPage() {
      this.genDbLoading = true;
      getGenerateTableDbPage(this.searchDbForm).then(res => {
        this.genTableDbList = res.data.list;
        this.searchDbForm.total = parseInt(res.data.total);
        this.genDbLoading = false;
      }).catch(error => {
        this.genDbLoading = false;
      })
    },
    handleImportGenTableDb(item) {
      this.genDbLoading = true;
      importGenerateTable({
        tableNames: item
      }).then(res => {
        this.$message.success("导入成功");
        this.drawerGenTableDbVisible = false;
        this.genDbLoading = false;
        this.getList();
      }).catch(error => {
        this.genDbLoading = false;
      })
    },
    handlePreview(item) {
      previewGenerateTable(item.genTableId).then(res => {
        this.previewData = res.data;
        this.dialogTitle = "预览代码";
        this.dialogPreviewVisible = true;
      })
    },
    clipboardSuccess() {
      this.$modal.msgSuccess("复制成功");
    },
    highlightedCode(code, key) {
      const vmName = key.substring(key.lastIndexOf("/") + 1, key.indexOf(".vm"));
      var language = vmName.substring(vmName.indexOf(".") + 1, vmName.length);
      const result = hljs.highlight(language, code || "", true);
      return result.value || '&nbsp;';
    },
  }
}
</script>

<style lang="scss" scoped>
.generateTable-container {
  padding: 20px;
}

.btn-div {
  display: flex;
  justify-content: space-between;
  align-content: center;
}

.tree-border {
  margin-top: 5px;
  border: 1px solid #e5e6e7;
  background: #FFFFFF none;
  border-radius: 4px;
}

.drawer-div {
  padding: 0 10px;
}
</style>
