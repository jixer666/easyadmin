<template>
  <div class="menu-container">
    <div>
      <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="菜单名称">
          <el-input v-model="form.menuName" placeholder="请输入菜单名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList" size="medium">搜索</el-button>
          <el-button @click="onSubmit" size="medium">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table v-loading="loading" :data="tableList" row-key="menuId">
        <el-table-column label="菜单名称" align="center" key="menuName" prop="menuName" :show-overflow-tooltip="true" />
        <el-table-column prop="icon" label="图标" align="center" width="100" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <svg-icon :icon-class="scope.row.icon" />
          </template>
        </el-table-column>
        <el-table-column label="顺序" align="center" key="orderNum" prop="orderNum" :show-overflow-tooltip="true" />
        <el-table-column label="组件路径" align="center" key="component" prop="component" :show-overflow-tooltip="true" />
        <el-table-column label="状态" align="center" key="status">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1">正常</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="160">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="250"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-plus"
              @click="handleAdd(scope.row)"
            >新增</el-button>
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
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="45%"
      :before-close="handleClose">
      <el-form ref="menuForm" :model="menuForm" label-width="80px">
        <el-form-item label="父级菜单">
          <el-cascader v-model="menuForm.parentId" :options="cascaderTreeList" filterable
                       clearable placeholder="请选择父级菜单" :props="{ emitPath: false,checkStrictly: true }"></el-cascader>
        </el-form-item>
        <el-form-item label="菜单类型">
          <el-radio-group v-model="menuForm.menuType">
            <el-radio :label="1">目录</el-radio>
            <el-radio :label="2">菜单</el-radio>
            <el-radio :label="3">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-row>
          <el-col :span="11">
            <el-form-item label="菜单名称">
              <el-input v-model="menuForm.menuName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="菜单顺序">
              <el-input-number v-model="menuForm.orderNum" :min="1" ></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="路由路径">
              <el-input v-model="menuForm.path"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="组件路径">
              <el-input v-model="menuForm.component"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="菜单图标">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <IconSelect ref="iconSelect" @selected="selected" :active-icon="menuForm.icon" />
                <el-input slot="reference" v-model="menuForm.icon" placeholder="点击选择图标" readonly>
                  <svg-icon
                    v-if="menuForm.icon"
                    slot="prefix"
                    :icon-class="menuForm.icon"
                    style="width: 25px;"
                  />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="权限字符">
              <el-input v-model="menuForm.perms"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="显示状态">
              <el-radio-group v-model="menuForm.hidden">
                <el-radio :label="1">显示</el-radio>
                <el-radio :label="2">隐藏</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="菜单状态">
              <el-radio-group v-model="menuForm.status">
                <el-radio :label="1">正常</el-radio>
                <el-radio :label="2">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit" :loading="submitLoading">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {getMenuPage, addMenu, updateMenu} from "@/api/menu";
import IconSelect from "@/components/IconSelect";

export default {
  name: 'Menu',
  components: {
    IconSelect
  },
  data() {
    return {
      form: {
        menuName: null,
        pageNum: 1,
        pageSize: 1000,
        total: 0,
      },
      tableList: [],
      loading: false,
      submitLoading: false,
      dialogTitle: null,
      dialogVisible: false,
      menuForm: {
        icon: ''
      },
      cascaderTreeList: [],

    }
  },
  mounted() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      getMenuPage(this.form).then(res => {
        this.tableList = res.data.list;
        this.form.total = parseInt(res.data.total);
        this.loading = false;
        this.cascaderTreeList = this.buildCascaderTree(this.tableList);
      }).catch(res => {
        this.loading = false;
      })
    },
    handleClose() {
      this.menuForm = {};
      this.dialogVisible = false;
    },
    handleAdd(item) {
      this.dialogTitle = "新增菜单";
      this.menuForm.parentId = item.menuId;
      this.dialogVisible = true;
    },
    handleUpdate(item) {
      this.dialogTitle = "修改菜单";
      this.menuForm = item;
      this.dialogVisible = true;
    },
    onSubmit() {
      this.$refs["menuForm"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.menuForm.menuId != undefined) {
            updateMenu(this.menuForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.submitLoading = false;
              this.dialogVisible = false;
              this.getList();
            });
          } else {
            addMenu(this.menuForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.submitLoading = false;
              this.dialogVisible = false;
              this.getList();
            });
          }
        }
      });
    },
    buildCascaderTree(list) {
      const result = [];
      list.forEach(item => {
        const menuItem = {
          value: item.menuId,
          label: item.menuName
        };
        if (item.children !== null && item.children.length !== 0) {
          menuItem['children'] = this.buildCascaderTree(item.children);
        }
        result.push(menuItem);
      })
      return result;
    },
    selected(name) {
      this.menuForm.icon = name;
    },
  }
}
</script>

<style lang="scss" scoped>
.menu-container {
  padding: 20px;
}
</style>
