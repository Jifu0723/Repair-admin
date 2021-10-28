<template>
  <div class="app-container">

    <el-collapse v-model="activeName">
      <el-collapse-item title="展开/闭合搜索条件界面" name="1">
        <el-form :model="queryParams1" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

          <el-form-item label="报修类型" label-width="110px" prop="repairType">
            <el-select v-model="queryParams1.repairType" filterable
                       clearable placeholder="请选择报修类型">
              <el-option
                v-for="item in repairTypeList"
                :key="item.repairTypeId"
                :label="item.repairType"
                :value="item.repairTypeId">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="报修者姓名" prop="repairName" label-width="100px">
            <el-input
              v-model="queryParams1.repairName"
              placeholder="请输入报修者姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="设备维修后状态" prop="repairedState" label-width="120px">
            <el-select v-model="queryParams1.repairedState" placeholder="请选择设备维修后状态" clearable size="small">
              <el-option
                v-for="item in repaireTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="维修人员姓名" prop="repaireName" label-width="100px">
            <el-input
              v-model="queryParams1.repaireName"
              placeholder="请输入维修人员姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="报修时间" prop="repairCreateTime">
            <el-date-picker clearable size="small"
                            v-model="queryParams1.repairCreateTime"
                            type="date"
                            placeholder="选择报修时间">
            </el-date-picker>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery(0)">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>

        </el-form>
      </el-collapse-item>
    </el-collapse>

    <!--报餐统计分析-->
    <el-row style="margin-top: 20px">
      <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
      <div>
        <div style="width: 100%;height:150px;">
          <div class="card-panel"
               style="width: 250px;height: 120px; background-color: #1ab394;float: left;line-height: 60px;text-align: center;color: #ffffff;font-size: 28px;margin-left: 8%">
            <i class="el-icon-pear"></i>
            <count-to :end-val="WaitOrderTotal" :duration="3600" class="card-panel-num"/>
            <div class="card-panel-text">
              未处理
            </div>
          </div>

          <div class="card-panel"
               style="width: 250px;height: 120px; background-color: #1ab394;float: left;margin-left:40px;line-height: 60px;text-align: center;color: #ffffff;font-size: 28px">
            <i class="el-icon-grape"></i>
            <count-to :end-val="ReceivedOrderTotal" :duration="3600" class="card-panel-num"/>
            <div class="card-panel-text">
              处理中
            </div>
          </div>

          <div class="card-panel"
               style="width: 250px;height: 120px; background-color: #1ab394;float: left;margin-left:40px;line-height: 60px;text-align: center;color: #ffffff;font-size: 28px">
            <i class="el-icon-watermelon"></i>
            <count-to :end-val="TransferOrderTotal" :duration="3600" class="card-panel-num"/>
            <div class="card-panel-text">
              转单中
            </div>
          </div>

          <div class="card-panel"
               style="width: 250px;height: 120px; background-color: #1ab394;float: left;margin-left:40px;line-height: 60px;text-align: center;color: #ffffff;font-size: 28px">
            <i class="el-icon-apple"></i>
            <count-to :end-val="FinishOrderTotal" :duration="3600" class="card-panel-num"/>
            <div class="card-panel-text">
              已完成
            </div>
          </div>

          <div class="card-panel"
               style="width: 250px;height: 120px; background-color: #1ab394;float: left;margin-left:40px;line-height: 60px;text-align: center;color: #ffffff;font-size: 28px">
            <i class="el-icon-potato-strips"></i>
            <count-to :end-val="RepairOrderTotal" :duration="3600" class="card-panel-num"/>
            <div class="card-panel-text">
              全部
            </div>
          </div>
        </div>

        <div id="main" style="width: 90%;height:500px;margin: auto"></div>

      </div>
    </el-row>

    <el-collapse v-model="activeNames">
      <el-collapse-item title="展开/闭合搜索条件界面" name="1">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['repair:tb:edit']"
            >修改
            </el-button>
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
            >删除
            </el-button>
          </el-col>

          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

        </el-row>

        <el-table v-loading="loading" :data="tbList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column sortable
                           hidden
                           type="expand"
                           header-align="center"
                           align="center"
                           width="120"
                           label="展开报修流程进度">
            <template slot-scope="scope">
              <el-steps v-if="scope.row.repairState === 1" :active="scope.row.repairState" finish-status="success">
                <el-step title="待接单"></el-step>
                <el-step title="已接单"></el-step>
                <el-step title="转单中"></el-step>
                <el-step title="已完成"></el-step>
              </el-steps>
              <el-steps v-if="scope.row.repairState === 2" :active="scope.row.repairState" finish-status="success">
                <el-step title="待接单"></el-step>
                <el-step title="已接单"></el-step>
                <el-step title="转单中"></el-step>
                <el-step title="已完成"></el-step>
              </el-steps>
              <el-steps v-if="scope.row.repairState === 3" :active="scope.row.repairState" finish-status="success">
                <el-step title="待接单"></el-step>
                <el-step title="已接单"></el-step>
                <el-step title="转单中"></el-step>
                <el-step title="已完成"></el-step>
              </el-steps>
              <el-steps v-if="scope.row.repairState === 4" :active="scope.row.repairState" finish-status="success">
                <el-step title="待接单"></el-step>
                <el-step title="已接单"></el-step>
                <el-step title="转单中"></el-step>
                <el-step title="已完成"></el-step>
              </el-steps>
            </template>
          </el-table-column>
          <el-table-column label="报修单位" align="center" prop="repairDep">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.repairDep == 200">大数据与软件工程学院</el-tag>
              <el-tag v-if="scope.row.repairDep == 201">商学院</el-tag>
              <el-tag v-if="scope.row.repairDep == 204">电子与信息工程学院</el-tag>
              <el-tag v-if="scope.row.repairDep == 205">外国语学院</el-tag>
              <el-tag v-if="scope.row.repairDep == 206">教师教育学院</el-tag>
              <el-tag v-if="scope.row.repairDep == 207">机械与材料工程学院</el-tag>
              <el-tag v-if="scope.row.repairDep == 208">宝石与艺术设计学院</el-tag>
              <el-tag v-if="scope.row.repairDep == 209">文学与传媒学院</el-tag>
              <el-tag v-if="scope.row.repairDep == 210">法学与公共管理学院</el-tag>
              <el-tag v-if="scope.row.repairDep == 211">马克思主义学院</el-tag>
              <el-tag v-if="scope.row.repairDep == 212">图书馆</el-tag>
              <el-tag v-if="scope.row.repairDep == 213">体育教学部</el-tag>
              <el-tag v-if="scope.row.repairDep == 214">网络信息化管理办公室</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="报修者姓名" align="center" prop="repairName"/>
          <el-table-column label="报修类型" align="center" prop="repairType">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.repairType == 1" type="success">网络设备</el-tag>
              <el-tag v-if="scope.row.repairType == 2">水电类</el-tag>
              <el-tag v-if="scope.row.repairType == 3" type="primary">家具类</el-tag>
              <el-tag v-if="scope.row.repairType == 4" type="info">办公室设备</el-tag>
              <el-tag v-if="scope.row.repairType == 6" type="warning">教学电子</el-tag>
              <el-tag v-if="scope.row.repairType == 7" type="danger">电器类</el-tag>
              <el-tag v-if="scope.row.repairType == 8" type="danger">木材类</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="设备维修后的状态" align="center" prop="repairedState">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.repairedState == 0" type="success">已修复</el-tag>
              <el-tag v-if="scope.row.repairedState == 1">已换新</el-tag>
              <el-tag v-if="scope.row.repairedState == 2" type="primary">无法修复</el-tag>
              <el-tag v-if="scope.row.repairedState == 3" type="primary">未修复</el-tag>
            </template>
          </el-table-column>

          <el-table-column v-if="form.repairDestory === 1" label="销单时间" align="center" prop="repairDestoryTime"
                           width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.repairDestoryTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>

          <el-table-column label="报修时间" align="center" prop="repairCreateTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.repairCreateTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['repair:tb:edit']"
              >查看报修详情
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['repair:tb:remove']"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams1.pageNum"
          :limit.sync="queryParams1.pageSize"
          @pagination="getList"
        />

      </el-collapse-item>
    </el-collapse>

    <!-- 添加或修改报修单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body>
      <h2
        style="display: flex;justify-content: center;margin-bottom: 30px;letter-spacing:30px;font-size: 30px;font-weight: bold">
        报修单详情首页</h2>

      <el-form ref="form" :model="form" :rules="rules" size="mini" label-width="100px"
               style="border: #0f1325 1px solid;padding: 10px;">

        <el-row :gutter="0">
          <el-col :span="8">
            <el-form-item label="报修单编号" prop="repairNo" label-width="90px">
              <el-input v-model="form.repairNo" placeholder="" style="font-size: 15px" :readonly="true"
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="维修单单价" prop="repairMoney" label-width="110px">
              <el-input v-model="form.repairMoney" style="font-size: 15px" :readonly="true" placeholder="" type="text"
                        clearable :style="{width: '80%'}">
              </el-input>
              元
            </el-form-item>
          </el-col>

        </el-row>

        <el-row :gutter="0">
          <el-col :span="4">
            <el-form-item label="报修者姓名" prop="repairName" label-width="90px">
              <el-input v-model="form.repairName" style="font-size: 15px" :readonly="true" placeholder="" clearable
                        :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="期望维修形式" label-width="130px" prop="repairExpectType">
              <el-select v-model="form.repairExpectType" disabled="disabled">
                <el-option
                  v-for="item in repairexpectTypeList"
                  :key="item.repexpectTypeId"
                  :label="item.repexpectType"
                  :value="item.repexpectTypeId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="报修单位部门" label-width="120px" prop="repairDep">
              <el-select v-model="form.repairDep" filterable disabled
                         clearable placeholder="">
                <el-option
                  v-for="item in depList"
                  :key="item.deptId"
                  :label="item.deptName"
                  :value="item.deptId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="设备维修后状态" label-width="130px" prop="repairedState">
              <el-select v-model="form.repairedState" filterable disabled
                         clearable placeholder="">
                <el-option
                  v-for="item in repaireTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row :gutter="0">

          <el-col :span="4">
            <el-form-item label="维修人员姓名" prop="repaireName" label-width="100px">
              <el-input v-model="form.repaireName" style="font-size: 15px" :readonly="true" placeholder="" clearable
                        :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="报修类型" label-width="110px" prop="repairType">
              <el-select v-model="form.repairType" disabled
                         placeholder="">
                <el-option
                  v-for="item in repairTypeList"
                  :key="item.repairTypeId"
                  :label="item.repairType"
                  :value="item.repairTypeId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="报修地点" prop="repairAddress" label-width="100px">
              <el-input :readonly="true" style="font-size: 15px" v-model="form.repairAddress" placeholder="" clearable
                        :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="期望维修时间" prop="repairExpectTime" label-width="130px">
              <el-date-picker clearable size="small" :style="{width: '100%'}"
                              v-model="form.repairExpectTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              :readonly="true"
                              placeholder="选择期望维修时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider><i class="el-icon-edit"></i></el-divider>

        <el-row :gutter="0">

          <el-col :span="15">
            <el-form-item label="报修内容" prop="repairContent" label-width="80px">
              <el-input v-model="form.repairContent" style="font-size: 20px;width: 1000px;height: 100px" placeholder=""
                        type="textarea" :readonly="true"
              ></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="10">
            <el-form-item label="报修图片预览" prop="incomeSource" label-width="100px">
              <el-upload
                action=""
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :auto-upload="false"
                accept="image/*"
                multiple
                :before-upload="beforeAvatarUpload"
                :file-list="upload.fileList">
                <i class="el-icon-plus"></i>
              </el-upload>

                <el-dialog :visible.sync="dialogVisible" append-to-body>
                  <img
                    width="100%"
                    :src="dialogImageUrl"
                  />
                </el-dialog>

            </el-form-item>

          </el-col>
        </el-row>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {addTb, delTb, exportTb, getTb, listTb, reptbList, updateTb} from "@/api/repair/tb";
import echarts from 'echarts'
import {countrepairAdminTimeByrepairType} from "@/api/repair/repairadminordertotal";
import {listRepType} from "@/api/repair/reptype";
import {listType} from "@/api/repair/expecttype";
import {listApply} from "@/api/repair/apply";
import countTo from 'vue-count-to';
import {listdept} from "@/api/system/dept";

export default {
  name: "repairadminorder",
  components: {countTo},

  data() {
    return {
      imgW: 1000,
      // 遮罩层
      loading: true,
      //图片上传
      dialogImageUrl: '',
      dialogVisible: false,
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 上传的文件列表
        fileList: []
      },
      // 导出遮罩层
      exportLoading: false,
      //二级学院列表
      depList: [],
      // 完成订单的总数
      FinishOrderTotal: 0,
      //待接单(待处理,处理中)
      WaitOrderTotal: 0,
      //已接单
      ReceivedOrderTotal: 0,
      //转单数量
      TransferOrderTotal: 0,
      //维修订单总数
      RepairOrderTotal: 0,
      //报修类型
      repairTypeList: [],
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
        repairNo: null,
        repairName: null,
        repairAddress: null,
        repairContent: null,
        repairType: null,
        repairExpectType: null,
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
        countType: null, // 查询类别 1按日查询 2按周 3按月
        repairCreateTime: new Date(),// 默认查询当天记录
        repairIsDelete: null,
        repairYear: null,
        repairMonth: null,
        repairDay: null
      },
      // 查询参数
      queryParams1: {
        pageNum: 1,
        pageSize: 10,
        repairNo: null,
        repairName: null,
        repairAddress: null,
        repairContent: null,
        repairType: null,
        repairExpectType: null,
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
        countType: null, // 查询类别 1按日查询 2按周 3按月
        repairCreateTime: new Date(),// 默认查询当天记录
        repairTime: null, // 查询报修时间
        repairIsDelete: null,
        repairYear: null,
        repairMonth: null,
        repairDay: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //期望维修形式
      repairexpectTypeList: [],
      //设备维修后状态
      repaireTypeList: [{
        value: 0,
        label: '已修复'
      }, {
        value: 1,
        label: '已换新'
      }, {
        value: 2,
        label: '无法修复'
      }, {
        value: 3,
        label: '未修复'
      }],
      chartBar: null,
      chartRich: null,
      chartLine: null,
      chartPie: null,
      activeNames: ["0"],//闭合
      activeName: ["0"],//闭合
      repairTime: {}, // 按时间统计用餐信息输出日、周、月报表
      netRepairNumber: [], // 网络设备量
      HyRepairNumber: [], // 水电类量
      FaRepaiNumber: [], // 家具类量
      OfRepairNumber: [], // 办公室设备量
      TeaeleRepairNumber: [], // 教学电子量
      EleRepairNumber: [], // 电器类量
      WoodRepairNumber: [], // 木材类量
      RepairOrderData: [], // 总报修量
    };
  },
  created() {
    this.baseUrl = process.env.VUE_APP_BASE_API
    this.handleQuery(0);
    this.getDepList();//获取二级学院信息
    this.getRepairTypeList();//获取报修类型
    this.getTransferOrder();//获取转单的维修单数量
    this.getWairOrderTotal();//等待接的维修单数量(未处理)
    this.getReceivedOrder();//已经接维修单的数量(处理中)
    this.getFinishOrderTotal();//已经完成的维修单数量(已完成)
    this.getRepairOrderTotal();//全部维修单数量
    this.getRepairexpectType();//查看期望维修形式
    this.getList();
  },
  activated() {
    // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
    if (this.chartLine) {
      this.chartLine.resize()
    }
    if (this.chartBar) {
      this.chartBar.resize()
    }
    if (this.chartPie) {
      this.chartPie.resize()
    }
    if (this.chartRich) {
      this.chartRich.resize()
    }
  },
  methods: {
    closeBigMeg() {
      this.imgW = 800;
    },
    /** 图片预上传格式限制 */
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    /** 图片预览 */
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    //获取二级学院信息
    getDepList() {
      listdept(this.queryParams).then(response => {
        this.depList = response.rows
      })
    },
    //查看期望维修形式
    getRepairexpectType() {
      listType(this.queryParams).then(response => {
        this.repairexpectTypeList = response.rows
      })
    },
    //获取报修类型
    getRepairTypeList() {
      listRepType(this.queryParams).then(response => {
        this.repairTypeList = response.rows
      })
    },
    //查询等待接的维修单总数
    getWairOrderTotal() {
      this.queryParams.repairState = 1 //待接的维修单数量
      reptbList(this.queryParams).then(response => {
        this.WaitOrderTotal = response.rows.length;
      });
    },
    //查询已接单维修单总数
    getReceivedOrder() {
      this.queryParams.repairState = 2 //已接单维修单数量
      reptbList(this.queryParams).then(response => {
        this.ReceivedOrderTotal = response.rows.length
      });
    },
    //查询转单的维修单总数
    getTransferOrder() {
      this.queryParams.repairState = 3 //转单的维修单数量
      reptbList(this.queryParams).then(response => {
        this.TransferOrderTotal = response.rows.length
      });
    },
    //查询完成的维修单总数
    getFinishOrderTotal() {
      this.queryParams.repairState = 4 //已完成的维修单数量
      reptbList(this.queryParams).then(response => {
        this.FinishOrderTotal = response.rows.length
      });
    },
    //查询全部维修单总数
    getRepairOrderTotal() {
      listApply(this.queryParams).then(response => {
        this.RepairOrderTotal = response.rows.length
      });
    },
    // echart图表
    initOrderMealData() {
      const that = this;
      if (this.repairTime != undefined && this.repairTime.dnetRepairNumber != null) {
        this.netRepairNumber = []// 网络设备量
        this.HyRepairNumber = []// 水电类量
        this.FaRepairNumber = []// 家具类量
        this.OfRepairNumber = []// 办公室设备量
        this.TeaeleRepairNumber = []// 教学电子量
        this.EleRepairNumber = [] // 电器类量
        this.WoodRepairNumber = [] // 木材类量
        this.RepairOrderData = []// 总报修量
        // 日报修量
        this.netRepairNumber.push(this.repairTime.dnetRepairNumber)// 网络设备量
        this.HyRepairNumber.push(this.repairTime.dHyRepairNumber)// 水电类量
        this.FaRepairNumber.push(this.repairTime.dFaRepairFoodNumber)// 家具类量
        this.OfRepairNumber.push(this.repairTime.dOfRepairFoodNumber)// 办公室设备量
        this.TeaeleRepairNumber.push(this.repairTime.dTeaeleRepairNumber)// 教学电子量
        this.EleRepairNumber.push(this.repairTime.dEleRepairNumber)// 电器类量
        this.WoodRepairNumber.push(this.repairTime.dWoodRepairNumber)// 木材类量
        this.RepairOrderData.push(this.repairTime.dayRepairTotal)// 总报修量
        // 周报修量
        this.netRepairNumber.push(this.repairTime.wnetRepairNumber)// 网络设备量
        this.HyRepairNumber.push(this.repairTime.wHyRepairNumber)// 水电类量
        this.FaRepairNumber.push(this.repairTime.wFaRepairFoodNumber)// 家具类量
        this.OfRepairNumber.push(this.repairTime.wOfRepairFoodNumber)// 办公室设备量
        this.TeaeleRepairNumber.push(this.repairTime.wTeaeleRepairNumber)// 教学电子量
        this.EleRepairNumber.push(this.repairTime.wEleRepairNumber)// 电器类量
        this.WoodRepairNumber.push(this.repairTime.wWoodRepairNumber)// 木材类量
        this.RepairOrderData.push(this.repairTime.weekRepairTotal)// 总报修量
        // 月订餐量
        this.netRepairNumber.push(this.repairTime.mnetRepairNumber)  // 网络设备量
        this.HyRepairNumber.push(this.repairTime.mHyRepairNumber)  // 水电类量
        this.FaRepairNumber.push(this.repairTime.mFaRepairFoodNumber)   // 家具类量
        this.OfRepairNumber.push(this.repairTime.mOfRepairFoodNumber)  // 办公室设备量
        this.TeaeleRepairNumber.push(this.repairTime.mTeaeleRepairNumber)   // 教学电子量
        this.EleRepairNumber.push(this.repairTime.mEleRepairNumber)  // 电器类量
        this.WoodRepairNumber.push(this.repairTime.mWoodRepairNumber) // 木材类量
        this.RepairOrderData.push(this.repairTime.monthRepairTotal)// 总报修量
      } else {
        that.msgError("报修信息渲染失败,请重新搜索！");
        return false
      }
      var option = {
        title: {text: '报修统计分析', subtext: '数据来梧州学院后勤报修数字化管理平台'},
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
            label: {
              show: true
            }
          }
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        calculable: true,
        legend: {
          data: ['网络设备', '水电类', '家具类', '办公室设备', '教学电子', '电器类', '木材类', '总报修']
        },
        grid: {
          top: '25%',
          left: '1%',
          right: '4%',
          containLabel: true
        },
        xAxis: {
          data: ["日报修量", "周报修量", "月报修量"]
        },
        yAxis: [
          {
            type: 'value',
            name: '报修量',
            axisLabel: {
              formatter: '{value} '
            }
          },
        ],
        series: [
          {
            name: '网络设备',
            type: 'bar',
            data: this.netRepairNumber,
            barWidth: 30,
            label: {
              show: true,
              position: 'top'
            },
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#f15a22',
                }, {
                  offset: 1,
                  color: '#f15a22'
                }]),
              }
            },
          }, {
            name: '水电类',
            type: 'bar',
            data: this.HyRepairNumber,
            barWidth: 30,
            label: {
              show: true,
              position: 'top'
            },
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#ffc20e'
                }, {
                  offset: 1,
                  color: '#ffc20e'
                }]),
              }
            },
          }, {
            name: '家具类',
            type: 'bar',
            data: this.FaRepairNumber,
            barWidth: 30,
            label: {
              show: true,
              position: 'top'
            },
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#78cdd1'
                }, {
                  offset: 1,
                  color: '#78cdd1'
                }]),
              }
            },
          }, {
            name: '办公室设备',
            type: 'bar',
            data: this.OfRepairNumber,
            barWidth: 30,
            label: {
              show: true,
              position: 'top'
            },
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#c77eb5'
                }, {
                  offset: 1,
                  color: '#c77eb5'
                }]),
              }
            },
          }, {
            name: '教学电子',
            type: 'bar',
            data: this.TeaeleRepairNumber,
            barWidth: 30,
            label: {
              show: true,
              position: 'top'
            },
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#afdfe4'
                }, {
                  offset: 1,
                  color: '#afdfe4'
                }]),
              }
            },
          }, {
            name: '电器类',
            type: 'bar',
            data: this.EleRepairNumber,
            barWidth: 30,
            label: {
              show: true,
              position: 'top'
            },
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#2585a6'
                }, {
                  offset: 1,
                  color: '#2585a6'
                }]),
              }
            },
          }, {
            name: '木材类',
            type: 'bar',
            data: this.WoodRepairNumber,
            barWidth: 30,
            label: {
              show: true,
              position: 'top'
            },
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#c99979'
                }, {
                  offset: 1,
                  color: '#c99979'
                }]),
              }
            },
          }, {
            name: '总报修量',
            type: 'bar',
            data: this.RepairOrderData,
            barWidth: 30,
            label: {
              show: true,
              position: 'top'
            },
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#436EEE'
                }, {
                  offset: 1,
                  color: '#436EEE'
                }]),
              }
            },
          }
        ],
      }
      this.chartBar = echarts.init(document.getElementById('main'))
      this.chartBar.clear()
      // 接触点击绑定时间，避免多次绑定点击事件
      this.chartBar.off('click');
      this.chartBar.on('click', function (data) {
        switch (data.dataIndex) {
          case 0:
            that.queryParams1.countType = 1
            break
          case 1:
            that.queryParams1.countType = 2
            break
          case 2:
            that.queryParams1.countType = 3
            break
          default:
            that.queryParams1.countType = 0
        }
        listTb(that.queryParams1).then(response => {
          that.tbList = response.rows;
          that.total = response.total;
          that.msgSuccess("报修信息渲染成功");
        }).catch(err => {
          that.msgError("报修信息渲染失败");
        });
      })
      this.chartBar.setOption(option)
      window.addEventListener('resize', () => {
        this.chartBar.resize()
      })

    },
    /** 查询报修单列表 */
    getList() {
      this.loading = true;
      listTb(this.queryParams1).then(response => {
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
        repairName: null,
        repairDep: null,
        applyId: null,
        repairMoney: null,
        repairDestoryTime: null,
        repairCreateTime: new Date(),// 默认查询当天记录
        repairIsDelete: null,
        appImgList: null
      };
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        repairId: null,
        repairNo: null,
        repairName: null,
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
        repairCreateTime: new Date(),// 默认查询当天记录
        repairIsDelete: null,
        countType: null,
        repairTime: null,
        timeSlot: null,
        startTime: null,
        endTime: null
      };
      this.resetForm("form");
    },

    // 格式化时间
    timeFormat(time) {
      // 时间格式化 2021-07-22 00:00:00
      let year = time.getFullYear();
      let month = time.getMonth() + 1 > 10 ? time.getMonth() + 1 : '0' + (time.getMonth() + 1);
      let day = time.getDate() >= 10 ? time.getDate() : "0" + time.getDate();
      let h = time.getHours() >= 10 ? time.getHours() : "0" + time.getHours();              //获取当前小时数(0-23)
      let m = time.getMinutes() >= 10 ? time.getMinutes() : "0" + time.getMinutes();         //获取当前分钟数(0-59)
      let s = time.getSeconds() >= 10 ? time.getSeconds() : "0" + time.getSeconds();         //获取当前分钟数(0-59)
      return year + "-" + month + "-" + day + " " + h + ":" + m + ":" + s;
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams1.pageNum = 1;
      this.countOrder = null
      //默认查询当天的报修数据并渲染到柱状图
      var repairtype = this.queryParams1.repairType
      if (this.queryParams1.repairCreateTime != null && repairtype == null) {
        this.queryParams1.repairTime = this.timeFormat(this.queryParams1.repairCreateTime)
        this.queryParams1.repairType = this.queryParams1.repairType
        countrepairAdminTimeByrepairType(this.queryParams1).then(response => {
          this.repairTime = response.data
          this.$nextTick(() => {
            this.initOrderMealData()
            this.msgSuccess("报修数据信息渲染成功!")
          })
          this.getList()
        });
      }
      //按报修时间和报修类型统计报修信息输出日、周、月报表
      var repairtype = this.queryParams1.repairType
      if (this.queryParams1.repairCreateTime != null && repairtype != null) {
        this.queryParams1.repairTime = this.timeFormat(this.queryParams1.repairCreateTime)
        this.queryParams1.repairType = this.queryParams1.repairType
        countrepairAdminTimeByrepairType(this.queryParams1).then(response => {
          this.repairTime = response.data
          this.$nextTick(() => {
            this.initOrderMealData()
            this.msgSuccess("报修数据信息渲染成功!")
          })
          this.getList()
        });
      }
      //按照维修人员姓名和报修时间查询
      var repairename = this.queryParams1.repaireName
      if (repairename != null && this.queryParams1.repairCreateTime != null) {
        this.queryParams1.repairTime = this.timeFormat(this.queryParams1.repairCreateTime)
        this.queryParams1.repaireName = this.queryParams1.repaireName
        countrepairAdminTimeByrepairType(this.queryParams1).then(response => {
          this.repairTime = response.data//报修时间
          this.$nextTick(() => {
            this.initOrderMealData()
            this.msgSuccess("报修数据信息渲染成功!")
          })
          this.getList()
        });
      }
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.repairId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const repairId = row.repairId || this.ids
      this.upload.fileList = [];
      getTb(repairId).then(response => {
        this.form = response.data;
        //报修申请图片
        this.upload.fileList = this.form.appImgList
        this.upload.fileList.forEach(item => {
          item.url = this.baseUrl + '/repair/appimg/downloadRepairImg?appimgId=' + item.appimgId
        })
        this.open = true;
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
      }).then(function () {
        return delTb(repairIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {
      });
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
      }).catch(() => {
      });
    }
  }
};
</script>
