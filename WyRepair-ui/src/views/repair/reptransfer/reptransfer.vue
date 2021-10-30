<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="发起转单用户姓名" prop="reptransfeUser" label-width="140px">
        <el-select v-model="queryParams.reptransfeUser" placeholder="请选择发起转单用户姓名">
          <el-option
            v-for="item in userList"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="被转单用户姓名" prop="reptransfe_to_user" label-width="120px">
        <el-select v-model="queryParams.reptransfe_to_user" placeholder="请选择被转单用户姓名">
          <el-option
            v-for="item in userList"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="转单用户类型" prop="reptransfe_user_type" label-width="100px">
        <el-select v-model="queryParams.reptransfe_user_type" placeholder="请选择转单用户类型">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

    </el-form>
    <div>
      <el-col :span="24" :xs="24">
        <el-row>
          <el-col :span="6" v-for="(item,index) in reptransferList" :key="index" style="display: inline-block;">
            <el-card class="box-card"
                     style="margin-bottom: 5px;min-height: 6vh;max-height: 35vh;border: 1px solid skyblue">
              <div slot="header">
                <el-button style="margin-bottom: 10px;float: right" type="primary"> {{ index + 1 }}</el-button>
              </div>
              <div slot="header" class="clearfix">
                <span>转单用户类型：{{ item.reptransfe_user_type == 0 ? '维修专员 ' : '维修人员' }}</span>
              </div>

              <div slot="header" class="clearfix">
                <span style="color: #34bfa3">接单人姓名：【{{ item.nick_name }}】</span>
              </div>
              <div slot="header" class="clearfix">
                <span style="color: #34bfa3">接单人联系方式：【{{ item.phonenumber }}】</span>
              </div>
              <div slot="header" class="clearfix">
                <span style="color: #34bfa3">接单人部门：【{{ item.dept_name }}】</span>
              </div>
              <div class="text item">
                <span style="color: red">被转单人姓名：【{{ item.to_nick_name }}】</span>
              </div>
              <div class="text item">
                <span style="color: red">被转单人联系方式：【{{ item.tophonenumber }}】</span>
              </div>
              <div class="text item">
                <span style="color: red">被转单人部门：【{{ item.to_dept_name }}】</span>
              </div>
              <div style="margin-top: 10px">
                <el-button style="margin-bottom: 10px;float: right" type="success" plain
                           @click="handleUpdate(item)"
                >查看转单详情
                </el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
        =
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </div>

    <!-- 转单信息列表 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <h1
        style="display: flex;justify-content: center;margin-bottom: 30px;letter-spacing:20px;font-size: 30px;font-weight: bold">
        转单信息列表</h1>
      <el-form ref="form" :model="ReptransferList"  :rules="rules" label-width="100px">

        <table style="font-size: 16px" border="1" cellspacing="0" width="100%" class="table" rules="all" >

        <el-row style="margin-top:20px"  :gutter="0">
          <el-col :span="12">
            <el-form-item label="接单人姓名:" prop="nick_name" label-width="130px">
              <div style="font-size: 17px">
                【{{ ReptransferList.nick_name }}】
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="被转单人姓名:" prop="to_nick_name" label-width="150px">
              <div style="font-size: 17px">
                【{{ ReptransferList.to_nick_name }}】
              </div>
            </el-form-item>
          </el-col>
        </el-row>

          <el-row  :gutter="0">
            <el-col :span="12">
              <el-form-item label="接单人性别:" prop="sex" label-width="130px">
                <div style="font-size: 17px" v-if="ReptransferList.sex == 0">
                  【男】
                </div>
                <div style="font-size: 17px" v-if="ReptransferList.sex == 1">
                  【女】
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="被转单人性别:" prop="tosex" label-width="150px">
                <div style="font-size: 17px" v-if="ReptransferList.tosex == 0">
                  【男】
                </div>
                <div style="font-size: 17px" v-if="ReptransferList.tosex == 1">
                  【女】
                </div>
              </el-form-item>
            </el-col>
          </el-row>

        <el-row :gutter="0">
          <el-col :span="12">
            <el-form-item label="接单人部门:" prop="dept_name" label-width="130px">
              <div style="font-size: 17px">
                【{{ ReptransferList.dept_name }}】
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="被转单人部门:" prop="to_dept_name" label-width="150px">
              <div style="font-size: 17px">
                【{{ ReptransferList.to_dept_name }}】
              </div>
            </el-form-item>
          </el-col>
        </el-row>

          <el-row :gutter="0">
            <el-col :span="12">
              <el-form-item label="接单人联系方式:" prop="dept_name" label-width="160px">
                <div style="font-size: 17px">
                  【{{ ReptransferList.phonenumber }}】
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="被转单人联系方式:" prop="to_dept_name" label-width="175px">
                <div style="font-size: 17px">
                  【{{ ReptransferList.tophonenumber }}】
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="0">
            <el-col :span="13">
              <el-form-item  label="报修单编号:" prop="apply_no" label-width="130px">
                <div style="font-size: 17px">
                  【{{ ReptransferList.apply_no }}】
                </div>
              </el-form-item>
            </el-col>

            <el-col :span="7" >
              <el-form-item label="报修类型:"   prop="apply_address" label-width="80px">
                <div style="font-size: 17px" v-if="ReptransferList.apply_type == 1">
                  【网络设备】
                </div>
                <div style="font-size: 17px" v-if="ReptransferList.apply_type == 2">
                  【水电类】
                </div>
                <div style="font-size: 17px" v-if="ReptransferList.apply_type == 3">
                  【家具类】
                </div>
                <div style="font-size: 17px" v-if="ReptransferList.apply_type == 4">
                  【办公室设备】
                </div>
                <div style="font-size: 17px" v-if="ReptransferList.apply_type == 6">
                  【教学电子】
                </div>
                <div style="font-size: 17px" v-if="ReptransferList.apply_type == 7">
                  【电器类】
                </div>
                <div style="font-size: 17px" v-if="ReptransferList.apply_type == 8">
                  【木材类】
                </div>
              </el-form-item>
            </el-col>
          </el-row>


          <el-row :gutter="0">
            <el-col :span="12">
              <el-form-item label="报修地址:" prop="apply_address" label-width="120px">
                <div style="font-size: 17px">
                  【{{ ReptransferList.apply_address }}】
                </div>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="转单时间:" prop="reptransfeTime" label-width="120px">
                <div style="font-size: 17px">
                  【{{ ReptransferList.reptransfeTime }}】
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="0">
            <el-col :span="11">
              <el-form-item label="报修内容:" prop="apply_content" label-width="120px">
                <el-input v-model="ReptransferList.apply_content" style="font-size: 15px;" :readonly="true" placeholder=""
                          type="textarea" rows="6"
                          clearable :style="{width: '90%'}">
                </el-input>
              </el-form-item>
            </el-col>

            <el-col :span="11">
              <el-form-item label="转单反馈:" prop="reptransfe_message" label-width="160px">
                <el-input type="textarea" rows="6" v-model="ReptransferList.reptransfe_message" placeholder=""
                          style="font-size: 15px" :readonly="true"
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
          </el-row>


        </table>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>

</template>

<script>
import {
  addReptransfer,
  delReptransfer,
  exportReptransfer,
  getReptransfer,
  listReptransfer,
  updateReptransfer,
  UserList
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
      value: '',
      options: [{
        value: '1',
        label: '维修人员'
      }, {
        value: '0',
        label: '维修专员'
      }],
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
      // 转单记录表格数据
      ReptransferList: [],
      //用户信息列表
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tosex : null,
        reptId: null,
        apply_no: null,
        reptToId: null,
        reptToNo: null,
        applyId: null,
        reptNo: null,
        tophonenumber: null,
        phonenumber: null,
        reptransfer_id: null,
        reptransfeMessage: null,
        reptransfe_message: null,
        reptransfeUser: null,
        reptransfe_to_user: null,
        reptransfe_user_type: null,
        reptransfeUserType: null,
        reptransfeToUser: null,
        apply_type: null,
        nick_name: null,
        parentId: null,
        deptName: null,
        to_nick_name: null,
        reptransfeTime: null,
        to_dept_name: null,
        dept_name: null,
        apply_address: null,
        apply_content: null,
        apply_userid: null,
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
    this.getUserList();
    this.getTreeselect();
    this.getList();
  },
  methods: {
    //获取用户信息
    getUserList() {
      UserList(this.queryParams).then(response => {
        this.userList = response.rows
        console.log(this.userList)
      })
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
        reptId: null,
        apply_no: null,
        reptToId: null,
        reptToNo: null,
        applyId: null,
        reptransfer_id: null,
        reptransfe_message: null,
        reptransfeUser: null,
        reptransfe_to_user: null,
        reptransfe_user_type: null,
        nick_name: null,
        parentId: null,
        deptName: null,
        to_nick_name: null,
        reptransfeTime: null,
        to_dept_name: null,
        dept_name: null,
        reptransfeIsDelete: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      listReptransfer(this.queryParams).then(response => {
        this.reptransferList = response.rows;
        console.log(this.reptransferList)
        this.total = response.total;
        this.loading = false;
      });
      this.getList();
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
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
    handleUpdate(item) {
      this.reset();
      const reptransferId = item.reptransfer_id
      console.log(reptransferId)
      getReptransfer(reptransferId).then(response => {
        this.ReptransferList = response.data;
        console.log(this.ReptransferList)
        this.open = true;
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
