<template xmlns="http://www.w3.org/1999/html">

  <el-tabs border type="border-card"  v-model="repairMonth" @tab-click="tabClick">
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
    <div style="font-size: 30px;text-align:  center;font-weight: 900;margin-top: 10px" >
      梧州学院{{ new Date().getFullYear()}}年{{ new Date().getMonth()}}月份后勤报修工作考勤表
    </div>
      <div id='content'>
          <el-row>
            <table style="font-size: 16px" border="1" v-for="(item,index) in UserInformationList"   cellspacing="0" width="85%"  class="table" rules="all" >
              <tr align='center' style="height: 2.5rem">
                <th colspan="3">维修员姓名</th>
                <td colspan="1">{{item.nickName}}</td>
                <th colspan="2">用工部门</th>
                <td colspan="1" v-text="">梧州学院后勤部</td>
              </tr>
              <tr align='center' style="height: 2.5rem">
                <th colspan="3">工号</th>
                <td colspan="1" v-text="">  {{item.userName}}</td>
                <th colspan="2">二级学院</th>
                <td colspan="1" v-text=""> {{item.deptName}}</td>
              </tr>
              <tr align='center' style="height: 2.5rem">
                <th colspan="3">电子邮箱</th>
                <td colspan="1" v-text=""> {{item.email}}</td>
                <th colspan="2">联系电话</th>
                <td colspan="1" v-text="">{{item.phonenumber}}</td>
              </tr>
              <tr align='center' style="height: 2.5rem">
                <th colspan="2">银行帐号</th>
                <td colspan="5" v-text="">  64515451574521245415452</td>
              </tr>
              <tr align='center' style="height: 2.5rem">
                <th colspan="2">工作内容</th>
                <td colspan="5" v-text="">  教学电子换新、办公室设备换配件</td>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >完成日期</th>
                <th colspan="3">完成的工作单内容及金额</th>
                <th colspan="1">完成日期</th>
                <th colspan="3">完成的工作单内容及金额</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >1号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">17号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >2号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">18号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >3号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">19号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >4号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">20号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>

              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >5号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">21号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >6号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">22号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >7号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">22号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>

              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >8号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">23号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >9号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">24号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >10号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">25号</th>
                <th colspan="3">打发士大夫大师傅</th>


              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >11号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">26号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >12号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">27号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >13号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">28号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>

              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >14号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">29号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >15号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">30号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center' class="th" style="height: 2.5rem">
                <th colspan="1" >16号</th>
                <th colspan="3">打发士大夫大师傅</th>
                <th colspan="1">31号</th>
                <th colspan="3">打发士大夫大师傅</th>
              </tr>
              <tr align='center'style="height: 2.5rem">
                <th  colspan="6" align="left">
                  酬金详情说明：
                  <br />
                  1.设备换新、换配件每完成一单30元的提成
                  <br />
                  2.设备维修每完成一单45元的提成
                  <br />
                  3.维修人员每人月基础工资：2200
                  <br />
                  4.维修员月总金额=基础工资+维修单总金额
                  <br />
                  每位维修员月参加工作的时间一般为30—40小时</th>
                <th colspan="6" align="left">月酬金详情：
                  <br />
                  6.本月完成单总数：
                  <br />
                  7.本月维修总金额：
                  <br />
                  8.本月考核等级为：
                  <br />
                  9.本月金额：
                  <br />
                  10.用工部门负责人签字（盖公章）
                  <br />
                  年   月   日</th>
              </tr>
            </table>
          </el-row>
        </div>
      <el-button style="margin-left: 94%" type="primary"   @click="doPrint">打印</el-button>

  </el-tabs>



</template>
<style>
#content{
  margin-left: 150px;
  margin-top: 20px;
}
table td:nth-child(1) > p{
  width: 20px;
  display: inline-block;
  writing-mode: vertical-rl;
  -webkit-writing-mode: vertical-rl;
}
</style>

<script>
import { MonthWorkList,UserInformation} from "@/api/repair/tb";

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
      repairMonth: '',
      MonthWorkList: [],
      UserInformationList:[],
      nickName:'',
      // 查询参数
      queryParams: {
        repairNo: null,
        repairAddress: null,
        repairContent: null,
        repairMoney: null,
        repairType: null,
        repairExpectType: null,
        repairExpectTime: null,
        repairState: null,
        repairedState: null,
        repairDestory: null,
        repairTransfer: null,
        repairDep: null,
        repairComment: null,
        applyId: null,
        curWork: null,
        repairDestoryTime: null,
        repairCreateTime: null,
        repairIsDelete: null,
        repairYear: null,
        repairMonth: null,
        repairDay: null,
        repairName: null,
        repaireName: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getUserInformation();//获取用户信息（姓名、手机号码、所在二级学院等）
    this.getList();
  },
  methods: {
    doPrint(){
      const el = document.getElementById('content');
      //删除上一个 start
      const del = document.getElementById('iframe')
      if(del){document.body.removeChild(del);}
      //删除上一个 end
      const iframe = document.createElement('IFRAME');
      let doc = null;
      iframe.setAttribute('style', 'position:absolute;width:0px;height:0px;left:500px;top:500px;');
      iframe.id = 'iframe'
      // iframe.contentWindow.document.getElementsByTagName('body')[0].style.transform = 'scale(0.7)'
      document.body.appendChild(iframe);

      doc = iframe.contentWindow.document;
      // doc.getElementsByTagName('body')[0].style.zoom = '0.7'
      // doc.style.transform = 'scale(0.7)';
      //添加样式 start
      // doc.write('<LINK rel="stylesheet" type="text/css" href="css/print.css">'); 此方法单页面无效，心态炸了，直接写js导出css字符串解决
      //参考源码导出css加载到iframe中
      doc.write(`<style>${printCss}</style>`);
      //添加样式 end
      doc.write(el.innerHTML);
      doc.close();
      // 获取iframe的焦点，从iframe开始打印
      iframe.contentWindow.focus();
      // iframe.contentWindow.document.getElementsByTagName('body')[0].style.transform = 'scale(0.7)'
      iframe.contentWindow.print();
      if (navigator.userAgent.indexOf("MSIE") > 0){ document.body.removeChild(iframe); }
    },
    //获取二级学院信息
    getUserInformation() {
      UserInformation(this.queryParams).then(response => {
        this.UserInformationList = response.data
        this.total = response.total;
        console.log(this.UserInformationList)
      })
    },
    /** 查询报修单列表 */
    getList() {
      this.loading = true;
      MonthWorkList(this.queryParams).then(response => {
        this.MonthWorkList = response.rows;
        console.log(  this.MonthWorkList)
        this.total = response.total;
        this.loading = false;
      });
    },

    tabClick(){
      console.log(this.repairMonth)
      if (this.repairMonth == 0){
        this.queryParams.repairMonth = 1
        this.getList()
        this.getUserInformation()
      } else if (this.repairMonth == 1){
        this.queryParams.repairMonth = 2
        this.getList()
        this.getUserInformation()
      }else if (this.repairMonth == 2){
        this.queryParams.repairMonth = 3
        this.getList()
        this.getUserInformation()
      } else if (this.repairMonth == 3){
        this.queryParams.repairMonth = 4
        this.getList()
        this.getUserInformation()
      } else if (this.repairMonth == 4){
        this.queryParams.repairMonth = 5
        this.getList()
        this.getUserInformation()
      } else if (this.repairMonth == 5){
        this.queryParams.repairMonth = 6
        this.getList()
        this.getUserInformation()
      } else if (this.repairMonth == 6){
        this.queryParams.repairMonth = 7
        this.getList()
        this.getUserInformation()
      } else if (this.repairMonth == 7){
        this.queryParams.repairMonth = 8
        this.getList()
      } else if (this.repairMonth == 8){
        this.queryParams.repairMonth = 9
        this.getList()
      } else if (this.repairMonth == 9){
        this.queryParams.repairMonth = 10
        this.getList()
        this.getUserInformation()
      } else if (this.repairMonth == 10){
        this.queryParams.repairMonth = 11
        this.getList()
        this.getUserInformation()
      }  else if (this.repairMonth == 11){
        this.queryParams.repairMonth = 12
        this.getList()
        this.getUserInformation()
      }
    },


  }
};
</script>
