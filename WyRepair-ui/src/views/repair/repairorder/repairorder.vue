<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报修单编号" prop="repairNo">
        <el-input
          v-model="queryParams.repairNo"
          placeholder="请输入报修单编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报修地点" prop="repairAddress">
        <el-input
          v-model="queryParams.repairAddress"
          placeholder="请输入报修地点"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="维修类型" prop="repairType">
        <el-select v-model="queryParams.repairType" placeholder="请选择维修类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="期望维修时间" prop="repairExpectTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.repairExpectTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择期望维修时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="报修状态" prop="repairState">
        <el-input
          v-model="queryParams.repairState"
          placeholder="请输入报修状态"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="维修后设备状态描述" prop="repairedState">
        <el-input
          v-model="queryParams.repairedState"
          placeholder="请输入维修后设备状态描述"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="是否销单" prop="repairDestory">-->
<!--        <el-input-->
<!--          v-model="queryParams.repairDestory"-->
<!--          placeholder="请输入是否销单"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否转单" prop="repairTransfer">-->
<!--        <el-input-->
<!--          v-model="queryParams.repairTransfer"-->
<!--          placeholder="请输入是否转单"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否评论" prop="repairComment">-->
<!--        <el-input-->
<!--          v-model="queryParams.repairComment"-->
<!--          placeholder="请输入是否评论"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否为当前运转单" prop="curWork">-->
<!--        <el-input-->
<!--          v-model="queryParams.curWork"-->
<!--          placeholder="请输入是否为当前运转单"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="初次报修所属部门id" prop="repairDep">-->
<!--        <el-input-->
<!--          v-model="queryParams.repairDep"-->
<!--          placeholder="请输入初次报修所属部门id"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="申请表id" prop="applyId">-->
<!--        <el-input-->
<!--          v-model="queryParams.applyId"-->
<!--          placeholder="请输入申请表id"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="销单时间" prop="repairDestoryTime">-->
<!--        <el-date-picker clearable size="small"-->
<!--          v-model="queryParams.repairDestoryTime"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="选择销单时间">-->
<!--        </el-date-picker>-->
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
          v-hasPermi="['repair:tb:add']"
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
          v-hasPermi="['repair:tb:edit']"
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
          v-hasPermi="['repair:tb:remove']"
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
          v-hasPermi="['repair:tb:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="报修单自增主键" align="center" prop="repairId" />-->
<!--      <el-table-column label="报修单编号" align="center" prop="repairNo" />-->
      <el-table-column label="报修地点" align="center" prop="repairAddress" />
      <el-table-column label="报修内容" align="center" prop="repairContent" />
      <el-table-column label="维修类型" align="center" prop="repairType" />
<!--      <el-table-column label="期望维修时间" align="center" prop="repairExpectTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.repairExpectTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="报修状态" align="center" prop="repairState" />
      <el-table-column label="维修后设备状态描述" align="center" prop="repairedState" />
<!--      <el-table-column label="是否销单" align="center" prop="repairDestory" />-->
<!--      <el-table-column label="是否转单" align="center" prop="repairTransfer" />-->
<!--      <el-table-column label="是否评论" align="center" prop="repairComment" />-->
<!--      <el-table-column label="是否为当前运转单" align="center" prop="curWork" />-->
<!--      <el-table-column label="初次报修所属部门id" align="center" prop="repairDep" />-->
<!--      <el-table-column label="申请表id" align="center" prop="applyId" />-->
      <el-table-column label="销单时间" align="center" prop="repairDestoryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.repairDestoryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="创建时间" align="center" prop="repairCreateTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.repairCreateTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="逻辑删除状态" align="center" prop="repairIsDelete" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['repair:tb:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['repair:tb:remove']"
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

    <!-- 添加或修改报修单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="报修单编号" prop="repairNo">
          <el-input v-model="form.repairNo" placeholder="请输入报修单编号" />
        </el-form-item>
        <el-form-item label="报修地点" prop="repairAddress">
          <el-input v-model="form.repairAddress" placeholder="请输入报修地点" />
        </el-form-item>
        <el-form-item label="报修内容">
          <editor v-model="form.repairContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="维修类型" prop="repairType">
          <el-select v-model="form.repairType" placeholder="请选择维修类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="期望维修时间" prop="repairExpectTime">
          <el-date-picker clearable size="small"
            v-model="form.repairExpectTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择期望维修时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报修状态" prop="repairState">
          <el-input v-model="form.repairState" placeholder="请输入报修状态" />
        </el-form-item>
        <el-form-item label="维修后设备状态描述" prop="repairedState">
          <el-input v-model="form.repairedState" placeholder="请输入维修后设备状态描述" />
        </el-form-item>
<!--        <el-form-item label="是否销单" prop="repairDestory">-->
<!--          <el-input v-model="form.repairDestory" placeholder="请输入是否销单" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="是否转单" prop="repairTransfer">-->
<!--          <el-input v-model="form.repairTransfer" placeholder="请输入是否转单" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="是否评论" prop="repairComment">-->
<!--          <el-input v-model="form.repairComment" placeholder="请输入是否评论" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="是否为当前运转单" prop="curWork">-->
<!--          <el-input v-model="form.curWork" placeholder="请输入是否为当前运转单" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="初次报修所属部门id" prop="repairDep">-->
<!--          <el-input v-model="form.repairDep" placeholder="请输入初次报修所属部门id" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="申请表id" prop="applyId">-->
<!--          <el-input v-model="form.applyId" placeholder="请输入申请表id" />-->
<!--        </el-form-item>-->
        <el-form-item label="销单时间" prop="repairDestoryTime">
          <el-date-picker clearable size="small"
            v-model="form.repairDestoryTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择销单时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="创建时间" prop="repairCreateTime">
          <el-date-picker clearable size="small"
            v-model="form.repairCreateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="逻辑删除状态" prop="repairIsDelete">
          <el-input v-model="form.repairIsDelete" placeholder="请输入逻辑删除状态" />
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
import { listTb, getTb, delTb, addTb, updateTb, exportTb } from "@/api/repair/tb";

export default {
  name: "Tb",
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
      // 报修单表格数据
      tbList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        repairNo: null,
        repairAddress: null,
        repairContent: null,
        repairType: null,
        repairExpectTime: null,
        repairState: null,
        repairedState: null,
        repairDestory: null,
        repairTransfer: null,
        repairComment: null,
        curWork: null,
        repairDep: null,
        applyId: null,
        repairDestoryTime: null,
        repairCreateTime: null,
        repairIsDelete: null
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
    /** 查询报修单列表 */
    getList() {
      this.loading = true;
      listTb(this.queryParams).then(response => {
        this.tbList = response.rows;
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
        repairId: null,
        repairNo: null,
        repairAddress: null,
        repairContent: null,
        repairType: null,
        repairExpectTime: null,
        repairState: null,
        repairedState: null,
        repairDestory: null,
        repairTransfer: null,
        repairComment: null,
        curWork: null,
        repairDep: null,
        applyId: null,
        repairDestoryTime: null,
        repairCreateTime: null,
        repairIsDelete: null
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
      this.ids = selection.map(item => item.repairId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加报修单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const repairId = row.repairId || this.ids
      getTb(repairId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报修单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.repairId != null) {
            updateTb(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTb(this.form).then(response => {
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
      const repairIds = row.repairId || this.ids;
      this.$confirm('是否确认删除报修单编号为"' + repairIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTb(repairIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有报修单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportTb(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
