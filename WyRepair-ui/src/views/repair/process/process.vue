<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

<!--      <el-form-item label="报修单编号" prop="repaireNo">-->
<!--        <el-input-->
<!--          v-model="queryParams.repaireNo"-->
<!--          placeholder="请输入报修单编号"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->

<!--      <el-form-item label="申请单编号" prop="applyNo">-->
<!--        <el-input-->
<!--          v-model="queryParams.applyNo"-->
<!--          placeholder="请输入申请单编号"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="维修人员工号" prop="repaireUserid" label-width="100px">
        <el-input
          v-model="queryParams.repaireUserid"
          placeholder="请输入维修人员工号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="维修人员姓名" prop="repaireName" label-width="100px">
        <el-input
          v-model="queryParams.repaireName"
          placeholder="请输入维修人员姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="维修人员所属部门" prop="repaireDep">-->
<!--        <el-input-->
<!--          v-model="queryParams.repaireDep"-->
<!--          placeholder="请输入维修人员所属部门"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="工作量" prop="workFinish">-->
<!--        <el-input-->
<!--          v-model="queryParams.workFinish"-->
<!--          placeholder="请输入工作量"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="逻辑删除" prop="processIsDelete">-->
<!--        <el-input-->
<!--          v-model="queryParams.processIsDelete"-->
<!--          placeholder="请输入逻辑删除"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
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
          v-hasPermi="['repair:process:add']"
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
          v-hasPermi="['repair:process:edit']"
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
          v-hasPermi="['repair:process:remove']"
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
          v-hasPermi="['repair:process:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="自增主键" align="center" prop="processId" />-->
<!--      <el-table-column label="报修单id" align="center" prop="repaireId" />-->
      <el-table-column label="报修单编号" align="center" prop="repaireNo" />
<!--      <el-table-column label="申请单id" align="center" prop="applyId" />-->
      <el-table-column label="申请单编号" align="center" prop="applyNo" />
      <el-table-column label="维修人员工号" align="center" prop="repaireUserid" />
      <el-table-column label="维修人员姓名" align="center" prop="repaireName" />
      <el-table-column label="维修人员所属部门" align="center" prop="repaireDep" />
      <el-table-column label="工作量" align="center" prop="workFinish" />
      <el-table-column label="维修过程描述" align="center" prop="processDescribe" />
<!--      <el-table-column label="逻辑删除" align="center" prop="processIsDelete" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['repair:process:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['repair:process:remove']"
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

    <!-- 添加或修改维修过程描述对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="报修单id" prop="repaireId">
          <el-input v-model="form.repaireId" placeholder="请输入报修单id" />
        </el-form-item>
        <el-form-item label="报修单编号" prop="repaireNo">
          <el-input v-model="form.repaireNo" placeholder="请输入报修单编号" />
        </el-form-item>
        <el-form-item label="申请单id" prop="applyId">
          <el-input v-model="form.applyId" placeholder="请输入申请单id" />
        </el-form-item>
        <el-form-item label="申请单编号" prop="applyNo">
          <el-input v-model="form.applyNo" placeholder="请输入申请单编号" />
        </el-form-item>
        <el-form-item label="维修人员工号" prop="repaireUserid">
          <el-input v-model="form.repaireUserid" placeholder="请输入维修人员工号" />
        </el-form-item>
        <el-form-item label="维修人员姓名" prop="repaireName">
          <el-input v-model="form.repaireName" placeholder="请输入维修人员姓名" />
        </el-form-item>
        <el-form-item label="维修人员所属部门" prop="repaireDep">
          <el-input v-model="form.repaireDep" placeholder="请输入维修人员所属部门" />
        </el-form-item>
        <el-form-item label="工作量" prop="workFinish">
          <el-input v-model="form.workFinish" placeholder="请输入工作量" />
        </el-form-item>
        <el-form-item label="维修过程描述" prop="processDescribe">
          <el-input v-model="form.processDescribe" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="逻辑删除" prop="processIsDelete">
          <el-input v-model="form.processIsDelete" placeholder="请输入逻辑删除" />
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
import { listProcess, getProcess, delProcess, addProcess, updateProcess, exportProcess } from "@/api/repair/process";

export default {
  name: "Process",
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
      // 维修过程描述表格数据
      processList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        repaireId: null,
        repaireNo: null,
        applyId: null,
        applyNo: null,
        repaireUserid: null,
        repaireName: null,
        repaireDep: null,
        workFinish: null,
        processDescribe: null,
        processIsDelete: null
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
    /** 查询维修过程描述列表 */
    getList() {
      this.loading = true;
      listProcess(this.queryParams).then(response => {
        this.processList = response.rows;
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
        processId: null,
        repaireId: null,
        repaireNo: null,
        applyId: null,
        applyNo: null,
        repaireUserid: null,
        repaireName: null,
        repaireDep: null,
        workFinish: null,
        processDescribe: null,
        createTime: null,
        processIsDelete: null
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
      this.ids = selection.map(item => item.processId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加维修过程描述";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const processId = row.processId || this.ids
      getProcess(processId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改维修过程描述";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.processId != null) {
            updateProcess(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcess(this.form).then(response => {
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
      const processIds = row.processId || this.ids;
      this.$confirm('是否确认删除维修过程描述编号为"' + processIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delProcess(processIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有维修过程描述数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportProcess(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
