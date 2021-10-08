<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="配件名称" prop="partsName">
        <el-input
          v-model="queryParams.partsName"
          placeholder="请输入配件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="配件品种" prop="partsVariety">
        <el-input
          v-model="queryParams.partsVariety"
          placeholder="请输入配件品种"
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
          v-hasPermi="['repair:parts:add']"
        >配件购买申请</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['repair:parts:edit']"
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
          v-hasPermi="['repair:parts:remove']"
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
          v-hasPermi="['repair:parts:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="partsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="配件购买人" align="center" prop="partsPerson" />

      <el-table-column label="配件名称" align="center" prop="partsName" />
      <el-table-column label="配件数量" align="center" prop="partsNumbers" />
      <el-table-column label="配件单价" align="center" prop="partsPrice" />
      <el-table-column label="配件购买数量" align="center" prop="partsPurchase" />
      <el-table-column label="配件购买说明" align="center" prop="partsPurchaseExplain" />
      <el-table-column label="配件购买时间" align="center" prop="partsTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.partsTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="配件总价格" align="center" prop="partsTotalPrice" />
      <el-table-column label="配件剩余数量" align="center" prop="partsNumber" />
      <el-table-column label="配件使用率状态" align="center" prop="partsState" />
<!--      <el-table-column label="配件代码" align="center" prop="partsNo" />-->
<!--      <el-table-column label="配件品种" align="center" prop="partsVariety" />-->
<!--      <el-table-column label="配件生产机构" align="center" prop="partsMechanism" />-->
<!--      <el-table-column label="配件售后服务热线" align="center" prop="partsPhone" />-->
<!--      <el-table-column label="配件登记机关" align="center" prop="partsOffice" />-->
<!--      <el-table-column label="配件维保单位地址" align="center" prop="partsAddress" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['repair:parts:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['repair:parts:remove']"
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

    <!-- 添加或修改配件信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="配件名称" prop="partsName">
          <el-input v-model="form.partsName" placeholder="请输入配件名称" />
        </el-form-item>

        <el-form-item label="配件数量" prop="partsNumbers">
          <el-input v-model="form.partsNumbers" placeholder="请输入配件数量" />
        </el-form-item>

        <el-form-item label="配件单价" prop="partsPrice">
          <el-input v-model="form.partsPrice" placeholder="请输入配件单价" />
        </el-form-item>

        <el-form-item label="配件购买数量" prop="partsPurchase">
          <el-input v-model="form.partsPurchase" placeholder="请输入配件购买数量" />
        </el-form-item>

        <el-form-item label="配件购买说明" prop="partsPurchaseExplain">
          <el-input v-model="form.partsPurchaseExplain" placeholder="请输入配件购买说明" />
        </el-form-item>

        <el-form-item label="配件购买时间" prop="partsTime">
          <el-date-picker clearable size="small"
                          v-model="form.partsTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择配件购买时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="配件总价格" prop="partsTotalPrice">
          <el-input v-model="form.partsTotalPrice" placeholder="请输入配件总价格" />
        </el-form-item>

        <el-form-item label="配件剩余数量" prop="partsNumber">
          <el-input v-model="form.partsNumber" placeholder="请输入配件剩余数量" />
        </el-form-item>

        <el-form-item label="配件使用率状态" prop="partsState">
          <el-input v-model="form.partsState" placeholder="请输入配件使用率状态" />
        </el-form-item>

        <el-form-item label="配件品种" prop="partsVariety">
          <el-input v-model="form.partsVariety" placeholder="请输入配件品种" />
        </el-form-item>

        <el-form-item label="配件生产机构" prop="partsMechanism">
          <el-input v-model="form.partsMechanism" placeholder="请输入配件生产机构" />
        </el-form-item>

        <el-form-item label="配件售后服务热线" prop="partsPhone">
          <el-input v-model="form.partsPhone" placeholder="请输入配件售后服务热线" />
        </el-form-item>

        <el-form-item label="配件登记机关" prop="partsOffice">
          <el-input v-model="form.partsOffice" placeholder="请输入配件登记机关" />
        </el-form-item>

        <el-form-item label="配件维保单位地址" prop="partsAddress">
          <el-input v-model="form.partsAddress" placeholder="请输入配件维保单位地址" />
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
import { listParts, getParts, delParts, addParts, updateParts, exportParts } from "@/api/repair/parts";

export default {
  name: "Parts",
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
      // 配件信息表格数据
      partsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        partsPerson: null,
        partsNo: null,
        partsName: null,
        partsNumbers: null,
        partsPrice: null,
        partsPurchase: null,
        partsPurchaseExplain: null,
        partsTime: null,
        partsTotalPrice: null,
        partsNumber: null,
        partsState: null,
        partsVariety: null,
        partsMechanism: null,
        partsPhone: null,
        partsOffice: null,
        partsAddress: null
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
    /** 查询配件信息列表 */
    getList() {
      this.loading = true;
      listParts(this.queryParams).then(response => {
        this.partsList = response.rows;
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
        partsId: null,
        partsPerson: null,
        partsNo: null,
        partsName: null,
        partsNumbers: null,
        partsPrice: null,
        partsPurchase: null,
        partsPurchaseExplain: null,
        partsTime: null,
        partsTotalPrice: null,
        partsNumber: null,
        partsState: null,
        partsVariety: null,
        partsMechanism: null,
        partsPhone: null,
        partsOffice: null,
        partsAddress: null
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
      this.ids = selection.map(item => item.partsId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加配件信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const partsId = row.partsId || this.ids
      getParts(partsId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改配件信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.partsId != null) {
            updateParts(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addParts(this.form).then(response => {
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
      const partsIds = row.partsId || this.ids;
      this.$confirm('是否确认删除配件信息编号为"' + partsIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delParts(partsIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有配件信息数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportParts(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
