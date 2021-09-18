<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="维修类型" prop="applyType">
        <el-select v-model="queryParams.applyType" placeholder="请选择维修类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>

      <el-form-item label="报修用户姓名" prop="applyUser" label-width="100px">
        <el-input
          v-model="queryParams.applyUser"
          placeholder="请输入报修用户姓名"
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
          v-hasPermi="['repair:apply:add']"
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
          v-hasPermi="['repair:apply:edit']"
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
          v-hasPermi="['repair:apply:remove']"
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
          v-hasPermi="['repair:apply:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="applyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请单编号" align="center" prop="applyNo" />
      <el-table-column label="报修地点" align="center" prop="applyAddress" />
      <el-table-column label="报修内容" align="center" prop="applyContent" />
      <el-table-column label="维修类型" align="center" prop="applyType" />
      <el-table-column label="期望维修形式" align="center" prop="applyExpectType" />
      <el-table-column label="期望维修时间" align="center" prop="applyExpectTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyExpectTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报修用户姓名" align="center" prop="applyUser" />
      <el-table-column label="维修价格" align="center" prop="repairPrice" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['repair:apply:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['repair:apply:remove']"
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

    <!-- 添加或修改报修申请单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="申请单编号" prop="applyNo">
          <el-input v-model="form.applyNo" placeholder="请输入申请单编号" />
        </el-form-item>
        <el-form-item label="报修地点" prop="applyAddress">
          <el-input v-model="form.applyAddress" placeholder="请输入报修地点" />
        </el-form-item>
        <el-form-item label="报修内容">
          <editor v-model="form.applyContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="维修类型" prop="applyType">
          <el-select v-model="form.applyType" placeholder="请选择维修类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="期望维修形式" prop="applyExpectType">
          <el-select v-model="form.applyExpectType" placeholder="请选择期望维修形式">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="期望维修时间" prop="applyExpectTime">
          <el-date-picker clearable size="small"
            v-model="form.applyExpectTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择期望维修时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="初次报修所属部门id" prop="applyDep">
          <el-input v-model="form.applyDep" placeholder="请输入初次报修所属部门id" />
        </el-form-item>
        <el-form-item label="申报用户id" prop="applyUserid">
          <el-input v-model="form.applyUserid" placeholder="请输入申报用户id" />
        </el-form-item>
        <el-form-item label="报修用户姓名" prop="applyUser">
          <el-input v-model="form.applyUser" placeholder="请输入报修用户姓名" />
        </el-form-item>
        <el-form-item label="申请用户角色" prop="applyRole">
          <el-input v-model="form.applyRole" placeholder="请输入申请用户角色" />
        </el-form-item>
        <el-form-item label="申请单创建时间" prop="applyCreateTime">
          <el-date-picker clearable size="small"
            v-model="form.applyCreateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择申请单创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="逻辑删除状态" prop="applyIsDelete">
          <el-input v-model="form.applyIsDelete" placeholder="请输入逻辑删除状态" />
        </el-form-item>
        <el-form-item label="维修价格" prop="repairPrice">
          <el-input v-model="form.repairPrice" placeholder="请输入维修价格" />
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
import { listApply, getApply, delApply, addApply, updateApply, exportApply } from "@/api/repair/apply";

export default {
  name: "Apply",
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
      // 报修申请单表格数据
      applyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applyNo: null,
        applyAddress: null,
        applyContent: null,
        applyType: null,
        applyExpectType: null,
        applyExpectTime: null,
        applyDep: null,
        applyUserid: null,
        applyUser: null,
        applyRole: null,
        applyCreateTime: null,
        applyIsDelete: null,
        repairPrice: null
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
    /** 查询报修申请单列表 */
    getList() {
      this.loading = true;
      listApply(this.queryParams).then(response => {
        this.applyList = response.rows;
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
        applyId: null,
        applyNo: null,
        applyAddress: null,
        applyContent: null,
        applyType: null,
        applyExpectType: null,
        applyExpectTime: null,
        applyDep: null,
        applyUserid: null,
        applyUser: null,
        applyRole: null,
        applyCreateTime: null,
        applyIsDelete: null,
        repairPrice: null
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
      this.ids = selection.map(item => item.applyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加报修申请单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const applyId = row.applyId || this.ids
      getApply(applyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报修申请单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.applyId != null) {
            updateApply(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addApply(this.form).then(response => {
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
      const applyIds = row.applyId || this.ids;
      this.$confirm('是否确认删除报修申请单编号为"' + applyIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delApply(applyIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有报修申请单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportApply(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
