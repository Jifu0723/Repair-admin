<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报修主键id" prop="repiId">
        <el-input
          v-model="queryParams.repiId"
          placeholder="请输入报修主键id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图片名称" prop="repimgName">
        <el-input
          v-model="queryParams.repimgName"
          placeholder="请输入图片名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图片相对路径" prop="repimgPath">
        <el-input
          v-model="queryParams.repimgPath"
          placeholder="请输入图片相对路径"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图片上传时间" prop="repimgCreateTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.repimgCreateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择图片上传时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="逻辑删除标识" prop="repimgIsDeleted">
        <el-input
          v-model="queryParams.repimgIsDeleted"
          placeholder="请输入逻辑删除标识"
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
          v-hasPermi="['repair:repimg:add']"
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
          v-hasPermi="['repair:repimg:edit']"
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
          v-hasPermi="['repair:repimg:remove']"
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
          v-hasPermi="['repair:repimg:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="imgList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增主键" align="center" prop="repimgId" />
      <el-table-column label="报修主键id" align="center" prop="repiId" />
      <el-table-column label="图片名称" align="center" prop="repimgName" />
      <el-table-column label="图片相对路径" align="center" prop="repimgPath" />
      <el-table-column label="图片上传时间" align="center" prop="repimgCreateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.repimgCreateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="逻辑删除标识" align="center" prop="repimgIsDeleted" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['repair:repimg:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['repair:repimg:remove']"
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

    <!-- 添加或修改维修单图片对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="报修主键id" prop="repiId">
          <el-input v-model="form.repiId" placeholder="请输入报修主键id" />
        </el-form-item>
        <el-form-item label="图片名称" prop="repimgName">
          <el-input v-model="form.repimgName" placeholder="请输入图片名称" />
        </el-form-item>
        <el-form-item label="图片相对路径" prop="repimgPath">
          <el-input v-model="form.repimgPath" placeholder="请输入图片相对路径" />
        </el-form-item>
        <el-form-item label="图片上传时间" prop="repimgCreateTime">
          <el-date-picker clearable size="small"
            v-model="form.repimgCreateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择图片上传时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="逻辑删除标识" prop="repimgIsDeleted">
          <el-input v-model="form.repimgIsDeleted" placeholder="请输入逻辑删除标识" />
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
import { listImg, getImg, delImg, addImg, updateImg, exportImg } from "@/api/repair/repimg";

export default {
  name: "Img",
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
      // 维修单图片表格数据
      imgList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        repiId: null,
        repimgName: null,
        repimgPath: null,
        repimgCreateTime: null,
        repimgIsDeleted: null
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
    /** 查询维修单图片列表 */
    getList() {
      this.loading = true;
      listImg(this.queryParams).then(response => {
        this.imgList = response.rows;
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
        repimgId: null,
        repiId: null,
        repimgName: null,
        repimgPath: null,
        repimgCreateTime: null,
        repimgIsDeleted: null
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
      this.ids = selection.map(item => item.repimgId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加维修单图片";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const repimgId = row.repimgId || this.ids
      getImg(repimgId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改维修单图片";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.repimgId != null) {
            updateImg(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addImg(this.form).then(response => {
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
      const repimgIds = row.repimgId || this.ids;
      this.$confirm('是否确认删除维修单图片编号为"' + repimgIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delImg(repimgIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有维修单图片数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportImg(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
