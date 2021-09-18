<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属报修单id" prop="reptId">
        <el-input
          v-model="queryParams.reptId"
          placeholder="请输入所属报修单id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属报修单编号" prop="reptNo">
        <el-input
          v-model="queryParams.reptNo"
          placeholder="请输入所属报修单编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所转to报修单id" prop="reptToId">
        <el-input
          v-model="queryParams.reptToId"
          placeholder="请输入所转to报修单id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所转to报修单编号" prop="reptToNo">
        <el-input
          v-model="queryParams.reptToNo"
          placeholder="请输入所转to报修单编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请单id" prop="applyId">
        <el-input
          v-model="queryParams.applyId"
          placeholder="请输入申请单id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发起转单用户id" prop="reptransfeUser">
        <el-input
          v-model="queryParams.reptransfeUser"
          placeholder="请输入发起转单用户id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所转用户id" prop="reptransfeToUser">
        <el-input
          v-model="queryParams.reptransfeToUser"
          placeholder="请输入所转用户id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="转单用户类型" prop="reptransfeUserType">
        <el-select v-model="queryParams.reptransfeUserType" placeholder="请选择转单用户类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="上一次转单记录id" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入上一次转单记录id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="转单时间" prop="reptransfeTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.reptransfeTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择转单时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="逻辑删除" prop="reptransfeIsDelete">
        <el-input
          v-model="queryParams.reptransfeIsDelete"
          placeholder="请输入逻辑删除"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['repair:reptransfer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['repair:reptransfer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['repair:reptransfer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['repair:reptransfer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reptransferList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增主键" align="center" prop="reptransferId" />
      <el-table-column label="所属报修单id" align="center" prop="reptId" />
      <el-table-column label="所属报修单编号" align="center" prop="reptNo" />
      <el-table-column label="所转to报修单id" align="center" prop="reptToId" />
      <el-table-column label="所转to报修单编号" align="center" prop="reptToNo" />
      <el-table-column label="申请单id" align="center" prop="applyId" />
      <el-table-column label="转单留言维修情况" align="center" prop="reptransfeMessage" />
      <el-table-column label="发起转单用户id" align="center" prop="reptransfeUser" />
      <el-table-column label="所转用户id" align="center" prop="reptransfeToUser" />
      <el-table-column label="转单用户类型" align="center" prop="reptransfeUserType" />
      <el-table-column label="上一次转单记录id" align="center" prop="parentId" />
      <el-table-column label="转单时间" align="center" prop="reptransfeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reptransfeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="逻辑删除" align="center" prop="reptransfeIsDelete" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['repair:reptransfer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['repair:reptransfer:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改转单记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属报修单id" prop="reptId">
          <el-input v-model="form.reptId" placeholder="请输入所属报修单id" />
        </el-form-item>
        <el-form-item label="所属报修单编号" prop="reptNo">
          <el-input v-model="form.reptNo" placeholder="请输入所属报修单编号" />
        </el-form-item>
        <el-form-item label="所转to报修单id" prop="reptToId">
          <el-input v-model="form.reptToId" placeholder="请输入所转to报修单id" />
        </el-form-item>
        <el-form-item label="所转to报修单编号" prop="reptToNo">
          <el-input v-model="form.reptToNo" placeholder="请输入所转to报修单编号" />
        </el-form-item>
        <el-form-item label="申请单id" prop="applyId">
          <el-input v-model="form.applyId" placeholder="请输入申请单id" />
        </el-form-item>
        <el-form-item label="转单留言维修情况" prop="reptransfeMessage">
          <el-input v-model="form.reptransfeMessage" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="发起转单用户id" prop="reptransfeUser">
          <el-input v-model="form.reptransfeUser" placeholder="请输入发起转单用户id" />
        </el-form-item>
        <el-form-item label="所转用户id" prop="reptransfeToUser">
          <el-input v-model="form.reptransfeToUser" placeholder="请输入所转用户id" />
        </el-form-item>
        <el-form-item label="转单用户类型" prop="reptransfeUserType">
          <el-select v-model="form.reptransfeUserType" placeholder="请选择转单用户类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="上一次转单记录id" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入上一次转单记录id" />
        </el-form-item>
        <el-form-item label="转单时间" prop="reptransfeTime">
          <el-date-picker clearable size="small"
            v-model="form.reptransfeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择转单时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="逻辑删除" prop="reptransfeIsDelete">
          <el-input v-model="form.reptransfeIsDelete" placeholder="请输入逻辑删除" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listReptransfer, getReptransfer, delReptransfer, addReptransfer, updateReptransfer, exportReptransfer } from "@/api/repair/reptransfer";

export default {
  name: "Reptransfer",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 转单记录表格数据
      reptransferList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        reptId: null,
        reptNo: null,
        reptToId: null,
        reptToNo: null,
        applyId: null,
        reptransfeMessage: null,
        reptransfeUser: null,
        reptransfeToUser: null,
        reptransfeUserType: null,
        parentId: null,
        reptransfeTime: null,
        reptransfeIsDelete: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询转单记录列表 */
    getList() {
      this.loading = true;
      listReptransfer(this.queryParams).then(response => {
        this.reptransferList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        reptransferId: null,
        reptId: null,
        reptNo: null,
        reptToId: null,
        reptToNo: null,
        applyId: null,
        reptransfeMessage: null,
        reptransfeUser: null,
        reptransfeToUser: null,
        reptransfeUserType: null,
        parentId: null,
        reptransfeTime: null,
        reptransfeIsDelete: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.reptransferId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加转单记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reptransferId = row.reptransferId || this.ids
      getReptransfer(reptransferId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改转单记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.reptransferId != null) {
            updateReptransfer(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReptransfer(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const reptransferIds = row.reptransferId || this.ids;
      this.$confirm('是否确认删除转单记录编号为"' + reptransferIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delReptransfer(reptransferIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有转单记录数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportReptransfer(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
