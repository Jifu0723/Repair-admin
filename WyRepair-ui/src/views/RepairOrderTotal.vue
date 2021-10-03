<template>
  <div class="app-container">

    <el-collapse v-model="activeNames">
      <el-collapse-item title="展开/闭合搜索条件界面" name="1">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

          <el-form-item label="报修类型" prop="repairType" label-width="120px">
            <el-select v-model="queryParams.repairType" placeholder="请选择报修类型" clearable size="small">
              <el-option
                v-for="item in repairTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="报修者姓名" prop="repairName" label-width="100px">
            <el-input
              v-model="queryParams.repairName"
              placeholder="请输入报修者姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

            <el-form-item label="设备维修后状态" prop="repairedState" label-width="120px">
              <el-select v-model="queryParams.repairedState" placeholder="请选择设备维修后状态" clearable size="small">
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
                v-model="queryParams.repaireName"
                placeholder="请输入维修人员姓名"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="报修时间" prop="repairCreateTime">
              <el-date-picker clearable size="small"
                              v-model="queryParams.repairCreateTime"
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
          <el-table-column label="报修者姓名" align="center" prop="repairName"/>
          <el-table-column label="维修人员姓名" align="center" prop="repaireName"/>
          <el-table-column label="报修地点" align="center" prop="repairAddress"/>
          <el-table-column label="报修内容" align="center" prop="repairContent"/>

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
              >修改
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
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import {addTb, delTb, exportTb, getTb, listTb, updateTb} from "@/api/repair/tb";
import echarts from 'echarts'
import {countrepairTimeByrepairType} from "@/api/repair/repairordertotal";

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
        repairDay: null,
        timeSlot: null, // 时间段查询参数
        startTime: null, // 时间段查询开始时间
        endTime: null // 时间段查询结束时间

      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
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
      //报修类型
      repairTypeList: [{
        value: 1,
        label: '网络设备'
      }, {
        value: 2,
        label: '水电类'
      }, {
        value: 3,
        label: '家具类'
      }, {
        value: 4,
        label: '办公室设备'
      }, {
        value: 6,
        label: '教学电子'
      }, {
        value: 7,
        label: '电器类'
      }, {
        value: 8,
        label: '木材类'
      }],
      chartBar: null,
      chartRich: null,
      chartLine: null,
      chartPie: null,
      activeNames: ["0"],//闭合
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
    this.handleQuery(0);
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
          top: '20%',
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
              // color: '#f1dd2b'
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#f44'
                }, {
                  offset: 1,
                  color: '#f44'
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
              // color: '#f1dd2b'
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#5CACEE'
                }, {
                  offset: 1,
                  color: '#5CACEE'
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
              // color: '#1ec141'
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#1C86EE'
                }, {
                  offset: 1,
                  color: '#1C86EE'
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
              // color: '#1ec141'
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#1C86EE'
                }, {
                  offset: 1,
                  color: '#1C86EE'
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
              // color: '#1ec141'
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#1C86EE'
                }, {
                  offset: 1,
                  color: '#1C86EE'
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
              // color: '#1ec141'
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#1C86EE'
                }, {
                  offset: 1,
                  color: '#1C86EE'
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
              // color: '#1ec141'
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#1C86EE'
                }, {
                  offset: 1,
                  color: '#1C86EE'
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
              // color:  'rgba(92, 123, 217)'
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
            that.queryParams.countType = 1
            break
          case 1:
            that.queryParams.countType = 2
            break
          case 2:
            that.queryParams.countType = 3
            break
          default:
            that.queryParams.countType = 0
        }
        // that.$nextTick(() => {
        listTb(that.queryParams).then(response => {
          that.tbList = response.rows;
          that.total = response.total;
          that.msgSuccess("报修信息渲染成功");
        }).catch(err => {
          that.msgError("报修信息渲染失败");
        });
        // })
      })
      this.chartBar.setOption(option)
      // that.msgSuccess("报餐信息渲染成功");
      window.addEventListener('resize', () => {
        this.chartBar.resize()
      })

    },
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
        repairName: null,
        repairDep: null,
        applyId: null,
        repairDestoryTime: null,
        repairCreateTime: null,
        repairIsDelete: null
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
        repairCreateTime: null,
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
      this.queryParams.pageNum = 1;
      this.countOrder = null
      //默认查询当天的报修数据并渲染到柱状图
      var repairtype = this.queryParams.repairType
      if (this.queryParams.repairCreateTime != null && repairtype == null) {
        this.queryParams.repairTime = this.timeFormat(this.queryParams.repairCreateTime)
        this.queryParams.repairType = this.queryParams.repairType
        countrepairTimeByrepairType(this.queryParams).then(response => {
          this.repairTime = response.data
          this.$nextTick(() => {
            this.initOrderMealData()
            this.msgSuccess("报修数据信息渲染成功!")
          })
          this.getList()
        });
      }
    //按报修时间和报修类型统计报修信息输出日、周、月报表
      var repairtype = this.queryParams.repairType
      if (this.queryParams.repairCreateTime != null && repairtype != null) {
        this.queryParams.repairTime = this.timeFormat(this.queryParams.repairCreateTime)
        this.queryParams.repairType = this.queryParams.repairType
        countrepairTimeByrepairType(this.queryParams).then(response => {
          this.repairTime = response.data
          this.$nextTick(() => {
            this.initOrderMealData()
            this.msgSuccess("报修数据信息渲染成功!")
          })
          this.getList()
        });
      }
      //按照维修人员姓名和报修时间查询
      var repairename = this.queryParams.repaireName
      if (repairename != null && this.queryParams.repairCreateTime != null) {
        this.queryParams.repairTime = this.timeFormat(this.queryParams.repairCreateTime)
        this.queryParams.repaireName = this.queryParams.repaireName
        countrepairTimeByrepairType(this.queryParams).then(response => {
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
