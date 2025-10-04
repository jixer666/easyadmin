<template>
  <div class="user-container">
    <div>
      <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="角色名称">
          <el-input v-model="form.userName" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit" size="medium">搜索</el-button>
          <el-button @click="onSubmit" size="medium">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="btn-div">
      <div>
        <el-button plain size="mini" type="primary" @click="handleAdd">新增</el-button>
        <el-button plain size="mini" type="danger">删除</el-button>
        <el-button plain size="mini" >导出</el-button>
      </div>
      <div>

      </div>
    </div>
    <div>
      <el-table v-loading="loading" :data="tableList">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column label="角色名称" align="center" key="userName" prop="userName" :show-overflow-tooltip="true" />
        <el-table-column label="角色字符" align="center" key="userKey" prop="userKey" :show-overflow-tooltip="true" />
        <el-table-column label="状态" align="center" key="status">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1">正常</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        </el-table-column>
        <el-table-column label="更新时间" align="center" prop="updateTime" width="160">
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="160"
        >
          <template slot-scope="scope">
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
      width="30%"
      :before-close="handleClose">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="角色名称">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="角色字符">
          <el-input v-model="form.userKey"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit" :loading="submitLoading">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getuserPage, adduser, updateuser } from '@/api/user'

export default {
  name: 'user',
  data() {
    return {
      searchForm: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
      form: {},
      tableList: [],
      loading: false,
      submitLoading: false,
      dialogVisible: false,
      dialogTitle: null,

    }
  },
  mounted() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      getuserPage(this.searchForm).then(res => {
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
          if (this.form.userId != undefined) {
            updateuser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.submitLoading = false;
              this.dialogVisible = false;
              this.getList();
            });
          } else {
            adduser(this.form).then(response => {
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
    handleDelete(item) {
    },
    handleAdd() {
      this.dialogTitle = "新增角色";
      this.dialogVisible = true;
    },
    handleClose() {
      this.form = {};
      this.dialogVisible = false;
    },
  }
}
</script>

<style lang="scss" scoped>
.user-container {
  padding: 20px;
}

.btn-div {
  display: flex;
  justify-content: space-between;
  align-content: center;
}
</style>
