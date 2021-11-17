
<template xmlns="http://www.w3.org/1999/html">

  <el-tabs border type="border-card" v-model="repairMonth" @tab-click="tabClick">
    <el-tab-pane label="1月"></el-tab-pane>
    <el-tab-pane label="2月"></el-tab-pane>
    <el-tab-pane label="3月"></el-tab-pane>
    <el-tab-pane label="4月"></el-tab-pane>
    <el-tab-pane label="5月"></el-tab-pane>
    <el-tab-pane label="6月"></el-tab-pane>
    <el-tab-pane label="7月"></el-tab-pane>
    <el-tab-pane label="8月"></el-tab-pane>
    <el-tab-pane label="9月"></el-tab-pane>
    <el-tab-pane label="10月"></el-tab-pane>
    <el-tab-pane label="11月"></el-tab-pane>
    <el-tab-pane label="12月"></el-tab-pane>
    <div id="printer">
      <div style="font-size: 30px;text-align: center;font-weight: 900;margin-top: 10px">
        梧州学院{{ new Date().getFullYear() }}年{{ queryParams.repairMonth }}月份后勤报修工作考勤表
      </div>
      <table style="font-size: 17px;margin-left: 10%;margin-top:20px;text-align: center" border="1" cellspacing="0" width="80%"
             class="table" rules="all">
        <tr align='center' style="height: 2.5rem">
          <th colspan="3">维修员姓名</th>
          <td colspan="1">{{ UserInformationList.nickName }}</td>
          <th colspan="2">用工部门</th>
          <td colspan="1" v-text="">梧州学院后勤部</td>
        </tr>
        <tr align='center' style="height: 2.5rem">
          <th colspan="3">工号</th>
          <td colspan="1" v-text=""> {{ UserInformationList.userName }}</td>
          <th colspan="2">二级学院</th>
          <td colspan="1" v-text=""> {{ UserInformationList.deptName }}</td>
        </tr>
        <tr align='center' style="height: 2.5rem">
          <th colspan="3">电子邮箱</th>
          <td colspan="1" v-text=""> {{ UserInformationList.email }}</td>
          <th colspan="2">联系电话</th>
          <td colspan="1" v-text="">{{ UserInformationList.phonenumber }}</td>
        </tr>
        <tr align='center' style="height: 2.5rem">
          <th colspan="3">银行卡使用者姓名</th>
          <td colspan="1" v-text=""> {{ UserInformationList.bankUser }}</td>
          <th colspan="2">开户银行名称</th>
          <td colspan="1" v-text="">{{ UserInformationList.bankName }}</td>
        </tr>
        <tr align='center' style="height: 2.5rem">
          <th colspan="2">银行帐号</th>
          <td colspan="5" v-text=""> {{ UserInformationList.bankAccount }}</td>
        </tr>
        <tr align='center' style="height: 2.5rem">
          <th colspan="2">身份证号码</th>
          <td colspan="5" v-text=""> {{ UserInformationList.cardno }}</td>
        </tr>
        <tr align='center' style="height: 2.5rem">
          <th colspan="2">工作内容</th>
          <td colspan="5">
							<span style="color: red" v-for="(item,index) in doubleMonthList"> {{
                  item.work1.repairType == 1 ? '网络设备' : item.work1.repairType == 2 ? '水电类' : item.work1.repairType == 3 ? '家具类' : item.work1.repairType == 4 ? '办公室设备'
                    : item.work1.repairType == 6 ? '教学电子' : item.work1.repairType == 7 ? '电器类' : item.work1.repairType == 8 ? '木材类' : ''
                }}</span>维修、换新、换配件
          </td>
        </tr>
        <tr align='center' class="th" style="height: 2.5rem">
          <th colspan="1">完成日期</th>
          <th colspan="3">完成的工作单内容及金额</th>
          <th colspan="1">完成日期</th>
          <th colspan="3">完成的工作单内容及金额</th>
        </tr>

        <tr align='center' class="th" v-for="(item,index) in doubleMonthList" style="height: 2.5rem">
          <td colspan="1">{{ doubleMonthList == null ? '' : item.work1.repairDay + '号' }}</td>

          <td colspan="3">
							<span> 日接单数：{{
                  doubleMonthList == null ? '' : item.work1.RepairOrderTotal
                }}单---日维修总金额：{{ doubleMonthList == null ? '' : item.work1.MoneyTotal }}元</span>
          </td>

          <td colspan="1" v-if="item.work2.repairDay!=''">
            {{ doubleMonthList == null ? '' : item.work2.repairDay + '号' }}
          </td>

          <td colspan="3">
							<span v-if="item.work2.repairDay!=''"> 日接单数：{{
                  doubleMonthList == null ? '' : item.work2.RepairOrderTotal
                }}单---日维修总金额：{{ doubleMonthList == null ? '' : item.work2.MoneyTotal }}元</span>
          </td>
        </tr>

        <tr align='center' style="height: 2.5rem;" >
          <th colspan="4" align="left">
         <span style="font-size: 20px;">   酬金详情说明：</span>
            <br/>
            1.设备换新、换配件每完成一单30元的提成
            <br/>
            2.设备维修每完成一单45元的提成
            <br/>
            3.维修人员每人月基础工资：2200
            <br/>
            4.维修员月总金额=基础工资+维修单总金额
            <br/>
            5.月考核等级划分：30单以上(优秀),20单以上为(良好),
            <br/>
            10单以上(合格),10单以下(不合格)
            <br/>
            6.每位维修员月参加工作的时间一般为30—40小时
          </th>
          <th colspan="6" align="left" >
         <span style="font-size: 20px;">   月酬金详情：</span>
            <br/>
            7.本月完成单总数：{{MoneyTotalList.RepairOrderTotal}}单
            <br/>
            8.本月维修总金额：{{MoneyTotalList.RepairMoneyTotal == null ? '0':MoneyTotalList.RepairMoneyTotal}}元
            <br/>
            9.本月考核等级为：{{MoneyTotalList.RepairOrderTotal > 30 ? '优秀':MoneyTotalList.RepairOrderTotal > 20 ? '良好':MoneyTotalList.RepairOrderTotal > 10 ? '合格':'不合格'}}
            <br/>
            10.本月金额：{{MoneyTotalList.RepairMoneyTotal == null ? '0': MoneyTotalList.RepairMoneyTotal + 2200}}元
            <br/>
            11.本人签字:
            <br/>
            12.用工部门负责人签字（盖公章）:
            <br/>
            ______年______ 月______ 日
          </th>
        </tr>
      </table>
    </div>
    <el-button style="margin-left: 86%" type="primary" v-print="'#printer'">打印</el-button>
  </el-tabs>
</template>


<script>
import Vue from 'vue'
import Print from 'vue-print-nb'
import {MonthWorkList, UserInformation,MoneyTotal} from "@/api/repair/tb";
Vue.use(Print)
export default {
  name: "monthwork",
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
      RepairMoneyTotal:'',
      repairMonth: '',
      bankAccount: '',
      bankUser: '',
      cardno: '',
      bankName: '',
      MoneyTotal: '',
      MonthWorkList: '',
      UserInformationList: [],
      MoneyTotalList:[],
      nickName: '',
      // 查询参数
      queryParams: {
        repairMoney: null,
        repairType: null,
        repairMonth: null,
        repairDay: null,
        repairName: null,
        repaireName: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      doubleMonthList: []
    };
  },
  created() {
    this.getUserInformation(); //获取用户信息（姓名、手机号码、所在二级学院等）
    this.getMonthWorkList() //查询报修单列表
    this.getMoneyTotal()// 月酬金总金额、月维修单总数查询
  },
  methods: {
    PrintRow(index, row) {
      this.$print(this.$refs.print) // 使用
    },
      // 月酬金总金额、月维修单总数查询
    getMoneyTotal() {
      MoneyTotal(this.queryParams).then(response => {
        this.MoneyTotalList = response.data
        this.total = response.total;
      })
    },
    //获取维修人员信息、银行卡信息
    getUserInformation() {
      UserInformation(this.queryParams).then(response => {
        this.UserInformationList = response.data
        this.total = response.total;
      })
    },
    /** 查询报修单列表 */
    getMonthWorkList() {
      this.doubleMonthList = []
      MonthWorkList(this.queryParams).then(response => {
        this.MonthWorkList = response.rows
        this.total = response.total;
        let len = this.MonthWorkList.length
        for (let i = 0; i < len; i++) {
          let item = {
            work1: {},
            work2: {},
          }
          item.work1 = this.MonthWorkList[i] //双
          if (i + 1 >= len) {
            item.work2 = {
              MoneyTotal: '',
              RepairOrderTotal: '',
              repairDay: '',
            }
          } else {
            item.work2 = this.MonthWorkList[i + 1]//单
          }
          this.doubleMonthList.push(item)
          i = i + 1
        }

      })
    },
    tabClick() {
      if (this.repairMonth == 0) {
        this.queryParams.repairMonth = 1
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      } else if (this.repairMonth == 1) {
        this.queryParams.repairMonth = 2
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      } else if (this.repairMonth == 2) {
        this.queryParams.repairMonth = 3
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      } else if (this.repairMonth == 3) {
        this.queryParams.repairMonth = 4
        this.getMonthWorkList()
        this.getMoneyTotal()
        this.getUserInformation()
      } else if (this.repairMonth == 4) {
        this.queryParams.repairMonth = 5
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      } else if (this.repairMonth == 5) {
        this.queryParams.repairMonth = 6
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      } else if (this.repairMonth == 6) {
        this.queryParams.repairMonth = 7
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      } else if (this.repairMonth == 7) {
        this.queryParams.repairMonth = 8
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      } else if (this.repairMonth == 8) {
        this.queryParams.repairMonth = 9
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      } else if (this.repairMonth == 9) {
        this.queryParams.repairMonth = 10
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      } else if (this.repairMonth == 10) {
        this.queryParams.repairMonth = 11
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      } else if (this.repairMonth == 11) {
        this.queryParams.repairMonth = 12
        this.getMonthWorkList()
        this.getUserInformation()
        this.getMoneyTotal()
      }
    },

  }

};

</script>
