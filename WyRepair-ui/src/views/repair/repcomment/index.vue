<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报修单id" prop="repcId">
        <el-input
          v-model="queryParams.repcId"
          placeholder="请输入报修单id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报修单编号" prop="repcNo">
        <el-input
          v-model="queryParams.repcNo"
          placeholder="请输入报修单编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请表id" prop="applyId">
        <el-input
          v-model="queryParams.applyId"
          placeholder="请输入申请表id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请单编号" prop="applyNo">
        <el-input
          v-model="queryParams.applyNo"
          placeholder="请输入申请单编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="维修类型：网络设备、水电类、家具类、办公室设备" prop="repairType">
        <el-select v-model="queryParams.repairType" placeholder="请选择维修类型：网络设备、水电类、家具类、办公室设备" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="服务评星级" prop="repcommentService">
        <el-input
          v-model="queryParams.repcommentService"
          placeholder="请输入服务评星级"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评论用户id" prop="repcommenUserid">
        <el-input
          v-model="queryParams.repcommenUserid"
          placeholder="请输入评论用户id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评论时间" prop="repcommenCrteateTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.repcommenCrteateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择评论时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="逻辑删除" prop="repcIsDelete">
        <el-input
          v-model="queryParams.repcIsDelete"
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
          v-hasPermi="['repair:repcomment:add']"
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
          v-hasPermi="['repair:repcomment:edit']"
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
          v-hasPermi="['repair:repcomment:remove']"
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
          v-hasPermi="['repair:repcomment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="repcommentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增主键" align="center" prop="repcommentId" />
      <el-table-column label="报修单id" align="center" prop="repcId" />
      <el-table-column label="报修单编号" align="center" prop="repcNo" />
      <el-table-column label="申请表id" align="center" prop="applyId" />
      <el-table-column label="申请单编号" align="center" prop="applyNo" />
      <el-table-column label="维修类型：网络设备、水电类、家具类、办公室设备" align="center" prop="repairType" />
      <el-table-column label="评论内容" align="center" prop="repcommentContent" />
      <el-table-column label="服务评星级" align="center" prop="repcommentService" />
      <el-table-column label="评论用户id" align="center" prop="repcommenUserid" />
      <el-table-column label="评论时间" align="center" prop="repcommenCrteateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.repcommenCrteateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="逻辑删除" align="center" prop="repcIsDelete" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['repair:repcomment:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['repair:repcomment:remove']"
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

    <!-- 添加或修改报修单评论对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="报修单id" prop="repcId">
          <el-input v-model="form.repcId" placeholder="请输入报修单id" />
        </el-form-item>
        <el-form-item label="报修单编号" prop="repcNo">
          <el-input v-model="form.repcNo" placeholder="请输入报修单编号" />
        </el-form-item>
        <el-form-item label="申请表id" prop="applyId">
          <el-input v-model="form.applyId" placeholder="请输入申请表id" />
        </el-form-item>
        <el-form-item label="申请单编号" prop="applyNo">
          <el-input v-model="form.applyNo" placeholder="请输入申请单编号" />
        </el-form-item>
        <el-form-item label="维修类型：网络设备、水电类、家具类、办公室设备" prop="repairType">
          <el-select v-model="form.repairType" placeholder="请选择维修类型：网络设备、水电类、家具类、办公室设备">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="评论内容">
          <editor v-model="form.repcommentContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="服务评星级" prop="repcommentService">
          <el-input v-model="form.repcommentService" placeholder="请输入服务评星级" />
        </el-form-item>
        <el-form-item label="评论用户id" prop="repcommenUserid">
          <el-input v-model="form.repcommenUserid" placeholder="请输入评论用户id" />
        </el-form-item>
        <el-form-item label="评论时间" prop="repcommenCrteateTime">
          <el-date-picker clearable size="small"
            v-model="form.repcommenCrteateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择评论时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="逻辑删除" prop="repcIsDelete">
          <el-input v-model="form.repcIsDelete" placeholder="请输入逻辑删除" />
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
import { listRepcomment, getRepcomment, delRepcomment, addRepcomment, updateRepcomment, exportRepcomment } from "@/api/repair/repcomment";

export default {
  name: "Repcomment",
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
      // 报修单评论表格数据
      repcommentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        repcId: null,
        repcNo: null,
        applyId: null,
        applyNo: null,
        repairType: null,
        repcommentContent: null,
        repcommentService: null,
        repcommenUserid: null,
        repcommenCrteateTime: null,
        repcIsDelete: null
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
    /** 查询报修单评论列表 */
    getList() {
      this.loading = true;
      listRepcomment(this.queryParams).then(response => {
        this.repcommentList = response.rows;
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
        repcommentId: null,
        repcId: null,
        repcNo: null,
        applyId: null,
        applyNo: null,
        repairType: null,
        repcommentContent: null,
        repcommentService: null,
        repcommenUserid: null,
        repcommenCrteateTime: null,
        repcIsDelete: null
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
      this.ids = selection.map(item => item.repcommentId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加报修单评论";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const repcommentId = row.repcommentId || this.ids
      getRepcomment(repcommentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报修单评论";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.repcommentId != null) {
            updateRepcomment(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRepcomment(this.form).then(response => {
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
      const repcommentIds = row.repcommentId || this.ids;
      this.$confirm('是否确认删除报修单评论编号为"' + repcommentIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delRepcomment(repcommentIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有报修单评论数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportRepcomment(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
