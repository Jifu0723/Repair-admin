<template>
  <div class="app-container">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
    <div>
      <el-col :span="20" :xs="24">
    <el-tabs border type="border-card" v-model="status" @tab-click="tabClick">
      <el-tab-pane label="维修人员转单"></el-tab-pane>
      <el-tab-pane label="维修专员转单"></el-tab-pane>
      <el-row>
        <el-col :span="7" v-for="(item,index) in reptransferList" :key="index" style="display: inline-block;">
          <el-card class="box-card"
                   style="margin-right: 3px;margin-bottom: 5px;min-height: 6vh;max-height: 35vh;border: 1px solid skyblue">
<!--            <div slot="header" class="clearfix">-->
<!--              <span style="color: #34bfa3">报修单编号：【{{ item.apply_no }}】</span>-->

<!--            </div>-->
            <div slot="header" class="clearfix">
              <span style="color: #34bfb3">转单用户类型：{{ item.reptransfe_user_type == 0 ? '维修专员 ' : '维修人员' }}</span>

            </div>
            <div slot="header" class="clearfix">
              <span style="color: #34bfa3">接单人姓名：【{{ item.nick_name }}】</span>

            </div>
            <div slot="header" class="clearfix">
              <span style="color: #34bfa3">接单人部门：【{{ item.dept_name }}】</span>

            </div>

<!--            <div class="text item">-->
<!--              <span>转单留言维修情况：【{{ item.reptransfe_message }}】</span>-->
<!--            </div>-->
            <div class="text item">
              <span style="color: red">被转单人姓名：【{{ item.to_nick_name }}】</span>
            </div>
            <div class="text item">
              <span style="color: red">被转单人部门：【{{ item.to_dept_name }}】</span>
            </div>
<!--            <div class="text item">-->
<!--              <span style="color: red">转单时间：【{{ item.reptransfeTime }}】</span>-->
<!--            </div>-->
          </el-card>
        </el-col>
      </el-row>
    </el-tabs>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </div>


  </div>

</template>

<script>
import {
  addReptransfer,
  delReptransfer,
  exportReptransfer,
  getReptransfer,
  listReptransfer,
  updateReptransfer
} from "@/api/repair/reptransfer";
import {treeselect} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";

export default {
  name: "Reptransfer",
  components: {Treeselect},
  data() {
    return {
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 部门树选项
      deptOptions: undefined,
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
        apply_no: null,
        reptToId: null,
        reptToNo: null,
        applyId: null,
        reptransfe_message: null,
        reptransfeUser: null,
        reptransfeToUser: null,
        reptransfe_user_type: null,
        nick_name: null,
        parentId: null,
        to_nick_name:null,
        reptransfeTime: null,
        to_dept_name: null,
        dept_name: null,
        reptransfeIsDelete: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      status: 0
    };
  },
  created() {
    this.getTreeselect();
    this.getList();
  },
  methods: {
    tabClick() {
      if (this.status == 0) {
        this.queryParams.reptransfe_user_type = 1
        this.getList()
      } else {
        this.queryParams.reptransfe_user_type = 0
        this.getList()
      }
    },
    /** 查询转单记录列表 */
    getList() {
      this.loading = true;
      listReptransfer(this.queryParams).then(response => {
        this.reptransferList = response.rows;
        console.log(this.reptransferList)
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
        pageNum: 1,
        pageSize: 10,
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
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
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
      this.single = selection.length !== 1
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
      }).then(function () {
        return delReptransfer(reptransferIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {
      });
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
      }).catch(() => {
      });
    }
  }
};
</script>
