package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.domain.entity.SysDept;
import com.gxuwz.wyrepair.common.core.domain.entity.SysRole;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import com.gxuwz.wyrepair.common.core.domain.model.LoginUser;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.ServletUtils;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.framework.web.service.TokenService;
import com.gxuwz.wyrepair.domain.RepairApply;
import com.gxuwz.wyrepair.domain.RepairProcess;
import com.gxuwz.wyrepair.domain.RepairRepTb;
import com.gxuwz.wyrepair.domain.RepairReptransfer;
import com.gxuwz.wyrepair.service.IRepairApplyService;
import com.gxuwz.wyrepair.service.IRepairProcessService;
import com.gxuwz.wyrepair.service.IRepairRepTbService;
import com.gxuwz.wyrepair.service.IRepairReptransferService;
import com.gxuwz.wyrepair.util.RepairCodeGen;
import com.gxuwz.wyrepair.system.service.ISysDeptService;
import com.gxuwz.wyrepair.system.service.ISysUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 报修单Controller
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Api(tags = "报修单模块")
@RestController
@RequestMapping("/api/repair/tb")
public class ApiRepairRepTbController extends BaseController {
    @Autowired
    private IRepairRepTbService repairRepTbService;
    @Autowired
    private IRepairApplyService applyService;
    @Autowired
    private IRepairProcessService processService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IRepairReptransferService transferService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysDeptService deptService;

    /**
     * 角色【维修专员】
     */
    private static final Long ROLE_REPAIR_ADMIN = 102L;
    /**
     * 角色【维修人员】
     */
    private static final Long ROLE_REPAIR = 101L;
    /**
     * 角色【互联网开发中心管理员】
     */
    private static final Long ROLE_IT_CENTER_ADMIN = 104L;


    /**
     * 查询报修单列表
     */
    @ApiOperation("查询报修单列表")
    @PreAuthorize("@ss.hasPermi('repair:tb:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairRepTb repairRepTb) {
        startPage();
        // 获取用户信息
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        SysRole sysRole = user.getRoles().get(0);
        if (repairRepTb.getRepairTransfer() == 1){
            //设置不是当前运转单，查询已转单记录
            RepairReptransfer rt = new RepairReptransfer();
            rt.setReptransfeUser(user.getUserId());
            List<RepairReptransfer> list2 = transferService.selectRepairReptransferList(rt);
            return getDataTable(list2);
        }else {
            // 设置当前运转单
            repairRepTb.setCurWork(1);
            List<RepairRepTb> list = repairRepTbService.selectRepairRepTbList(repairRepTb, user.getUserId());
            return getDataTable(list);
        }
    }

    /**
     * 查询【我的报修单】列表专用方法
     */
    @ApiOperation("查询我的报修单列表")
    @PreAuthorize("@ss.hasPermi('repair:tb:list')")
    @GetMapping("/myrepairs")
    public TableDataInfo myRepairs(RepairRepTb repairRepTb) {
        startPage();
        // 获取用户信息
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        // 设置当前运转单
        repairRepTb.setCurWork(1);
        List<RepairRepTb> list = repairRepTbService.selectRepairTbListOfOthers(repairRepTb, user.getUserId());
        return getDataTable(list);
    }

    @ApiOperation("维修人员接单")
    @PreAuthorize("@ss.hasPermi('repair:tb:add')")
    @PostMapping("/receiveRep")
    public AjaxResult receiveRep(@RequestBody RepairRepTb repairRepTb) {
        // 验证维修单是否已被接单
        RepairRepTb tb = repairRepTbService.selectRepairRepTbById(repairRepTb.getRepairId());
        if (tb.getRepairState() == 1) {
            // 获取用户信息
            SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
            repairRepTb.setRepairDep(user.getDeptId());
            // 更新维修单状态
            repairRepTb.setRepairState(2);
            repairRepTbService.updateRepairRepTb(repairRepTb);
            // 维修单更新
            RepairApply apply = applyService.selectRepairApplyById(repairRepTb.getApplyId());
            // 维修过程信息记录
            RepairProcess process = new RepairProcess();
            process.initProcess(apply, repairRepTb, user.getUserName(), user.getUserId());
            processService.insertRepairProcess(process);
            return AjaxResult.success("接单成功！");
        }
        return AjaxResult.error("很抱歉，该维修单已被其他维修人员接单！");
    }


    /**
     * 查询【待修】报修单列表
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:list')")
    @GetMapping("/waitReplist")
    public TableDataInfo waitRepairlist(RepairRepTb repairRepTb) {
//        startPage();
        // 获取用户信息
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        repairRepTb.setCurWork(1);
        repairRepTb.setRepairState(1);
        repairRepTb.setRepairDep(user.getDeptId());
        List<RepairRepTb> list = repairRepTbService.selectRepairRepTbList(repairRepTb, null);
        return getDataTable(list);
    }

    /*
     * 转单{
     *     1. 维修专员【跨部门转单】
     *     2. 维修人员【转维修人员、跨部门转部门内部单】
     *     3. 维修人员【转回部门内部单】
     *
     * }
     * @param repairRepTb
     * @param transferName 转给的维修人员姓名
     * @return
     */

    /**
     * 1. 维修专员【跨部门转单】
     *
     * @param repairRepTb 报修单
     * @return AjaxResult
     */
    @ApiOperation("转单（维修专员跨部门转单）")
    @PreAuthorize("@ss.hasPermi('repair:tb:transferToDep')")
    @GetMapping("/transferFromDep")
    public AjaxResult transferFromDep(@ApiParam(name = "repairRepTb", value = "维修单实体") RepairRepTb repairRepTb, long deptId, Long repairType) {
        // 原报修单运转状态改变
        repairRepTb.setCurWork(0);
        repairRepTbService.updateRepairRepTb(repairRepTb);
        // 获取用户信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        RepairApply apply = applyService.selectRepairApplyById(repairRepTb.getApplyId());
        SysUser sysUser = loginUser.getUser();
        // 新的维修单
        RepairRepTb newRepair = new RepairRepTb();
        newRepair.initRepairApply(apply, RepairCodeGen.genApplyNo(), deptId);
        newRepair.setRepairType(repairType);
        repairRepTbService.insertRepairRepTb(newRepair);
        // 转单记录保存
        RepairReptransfer transferRep = new RepairReptransfer();
        transferRep.initTransfer(repairRepTb, newRepair, apply.getApplyId(), sysUser.getUserId(), 0);
        // 查询上一次转单记录
        RepairReptransfer rt = new RepairReptransfer();
        rt.setReptToNo(repairRepTb.getRepairNo());
        List<RepairReptransfer> repairReptransfers = transferService.selectRepairReptransferList(rt);
        if (repairReptransfers.size() > 0 && repairReptransfers.get(0) != null) {
            transferRep.setParentId(repairReptransfers.get(0).getReptransferId());
        }
        transferService.insertRepairReptransfer(transferRep);
        return AjaxResult.success();
    }

    /**
     * 2. 维修人员【转维修人员、转部门内部单】
     *
     * @param repairRepTb 报修单
     * @param tUser       转给的维修人员
     * @return AjaxResult
     */
    @ApiOperation("转单（维修人员转维修人员）")
    @PreAuthorize("@ss.hasPermi('repair:tb:transferToPer')")
    @GetMapping("/transferRepair")
    public AjaxResult transferRepair(
            @ApiParam(name = "repairRepTb", value = "维修单实体") RepairRepTb repairRepTb,
            @ApiParam(name = "transferName", value = "转单对象") SysUser tUser) {

        Long repairDep = repairRepTb.getRepairDep();
        // 获取用户信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        // 原申请单获取
        RepairApply apply = applyService.selectRepairApplyById(repairRepTb.getApplyId());
        // 原报修单运转状态改变
        repairRepTb.setCurWork(0);
        repairRepTb.setRepairTransfer(1);
        repairRepTbService.updateRepairRepTb(repairRepTb);
        // 新的维修单
        RepairRepTb newRepair = new RepairRepTb();
        newRepair.initRepairApply(apply, RepairCodeGen.genApplyNo(),repairDep);
        repairRepTbService.insertRepairRepTb(newRepair);
        // 维修单过程信息保存
        RepairProcess process = new RepairProcess();
        process.initProcess(apply, newRepair, tUser.getUserName(), tUser.getUserId());
        processService.insertRepairProcess(process);
        // 转单记录保存
        RepairReptransfer transferRep = new RepairReptransfer();
        transferRep.initTransfer(repairRepTb, newRepair, apply.getApplyId(), sysUser.getUserId(), 1);
        transferRep.setReptransfeToUser(tUser.getUserId());
        transferRep.setReptToDept(repairDep);
        // 查询上一次转单记录
        RepairReptransfer rt = new RepairReptransfer();
        rt.setReptToNo(repairRepTb.getRepairNo());
        List<RepairReptransfer> repairReptransfers = transferService.selectRepairReptransferList(rt);
        if (repairReptransfers.size() > 0 && repairReptransfers.get(0) != null) {
            transferRep.setParentId(repairReptransfers.get(0).getReptransferId());
        }
        transferService.insertRepairReptransfer(transferRep);
        return AjaxResult.success();
    }

    /**
     * 3. 维修人员【转回部门内部单】
     *
     * @param repairRepTb  报修单
     * @param transferName 转给的维修人员姓名
     * @return AjaxResult
     */
    @ApiOperation("转单（转回部门内部单）")
    @PreAuthorize("@ss.hasPermi('repair:tb:transferToShare')")
    @GetMapping("/transferToDep")
    public AjaxResult transferDep(@ApiParam(name = "repairRepTb", value = "维修单实体") RepairRepTb repairRepTb,
                                  @ApiParam(name = "transferName", value = "转单对象姓名") String transferName,
                                  @ApiParam(name = "repairUserid", value = "转单对象工号") Long repairUserid) {
        RepairApply apply = applyService.selectRepairApplyById(repairRepTb.getApplyId());
        SysUser sysUser = sysUserService.selectUserById(repairUserid);
        // 新的维修单
        RepairRepTb newRepair = new RepairRepTb();
        newRepair.initRepairApply(apply, RepairCodeGen.genApplyNo(), repairRepTb.getRepairDep());
        newRepair.setRepairState(1);
        repairRepTbService.insertRepairRepTb(newRepair);
        // 维修单过程信息保存
        RepairProcess process = new RepairProcess();
        process.initProcess(apply, newRepair, transferName, repairUserid);
        processService.insertRepairProcess(process);
        // 转单记录保存
        RepairReptransfer transferRep = new RepairReptransfer();
        transferRep.initTransfer(repairRepTb, newRepair, apply.getApplyId(), sysUser.getUserId(), 1);
        // 查询上一次转单记录
        RepairReptransfer rt = new RepairReptransfer();
        rt.setReptToNo(repairRepTbService.selectRepairRepTbById(repairRepTb.getApplyId()).getRepairNo());
        List<RepairReptransfer> repairReptransfers = transferService.selectRepairReptransferList(rt);
        if (repairReptransfers.size() > 0 && repairReptransfers.get(0) != null) {
            transferRep.setParentId(repairReptransfers.get(0).getReptransferId());
        }
        transferService.insertRepairReptransfer(transferRep);
        return AjaxResult.success();
    }

    /**
     * 维修人员自主接单
     *
     * @param repTb 维修单
     * @return AjaxResult
     */
    @ApiOperation("维修人员自主接单")
    @PreAuthorize("@ss.hasPermi('repair:tb:accept')")
    @Log(title = "维修人员自主接单", businessType = BusinessType.UPDATE)
    @RequestMapping("/receive")
    public AjaxResult getRepair(RepairRepTb repTb) {
        // 获取申请单信息
        RepairApply oApply = applyService.selectRepairApplyById(repTb.getApplyId());
        // 更新维修单信息
        RepairRepTb repairRepTb = new RepairRepTb();
        repairRepTb.setRepairState(2);
        repairRepTbService.updateRepairRepTb(repairRepTb);
        // 生成维修过程信息
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        RepairProcess process = new RepairProcess();
        process.initProcess(oApply, repTb, user.getUserName(), user.getUserId());
        processService.insertRepairProcess(process);
        return AjaxResult.success();
    }

    /**
     * 维修专员指定派单
     *
     * @param repTb      维修单
     * @param userName 维修人员姓名
     * @param userId   维修人员工号
     * @return AjaxResult
     */
    @ApiOperation("维修专员指定派单")
    @PreAuthorize("@ss.hasPermi('repair:tb:appoint')")
    @Log(title = "维修专员指定派单", businessType = BusinessType.UPDATE)
    @RequestMapping("/arrange")
    public AjaxResult sendRepair(
            @ApiParam(name = "repTb", value = "维修单对象") RepairRepTb repTb,
            @ApiParam(name = "userName", value = "派单对象姓名") String userName,
            @ApiParam(name = "userId", value = "派单对象工号") Long userId) {
        // 检验该单是否已被维修人员抢单
        RepairRepTb checkRep = repairRepTbService.selectRepairRepTbById(repTb.getRepairId());
        if(checkRep.getRepairState()==2){
            return AjaxResult.error("转单失败，该单已被维修人员接单了！");
        }
        //更新维修单状态
        repTb.setRepairState(2);
        repairRepTbService.updateRepairRepTb(repTb);
        RepairApply apply = applyService.selectRepairApplyById(repTb.getApplyId());
        //生成维修过程信息
        RepairProcess process = new RepairProcess();
        process.initProcess(apply, repTb, userName, userId);
        processService.insertRepairProcess(process);
        return AjaxResult.success();
    }

    /**
     * 获取报修单详细信息
     */
    @ApiOperation("获取报修单详细信息")
    @ApiImplicitParam(name = "repairId", value = "报修单ID", required = true, dataType = "int", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:tb:query')")
    @GetMapping(value = "/{repairId}")
    public AjaxResult getInfo(@PathVariable("repairId") Long repairId) {
        return AjaxResult.success(repairRepTbService.selectRepairRepTbById(repairId));
    }

    /**
     * 新增报修单
     */
    @ApiOperation("新增报修单")
    @ApiImplicitParam(name = "RepairRepTb", value = "新增报修单", dataType = "RepairRepTb")
    @PreAuthorize("@ss.hasPermi('repair:tb:add')")
    @Log(title = "报修单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairRepTb repairRepTb) {
        return toAjax(repairRepTbService.insertRepairRepTb(repairRepTb));
    }

    /**
     * 修改报修单
     */
    @ApiOperation("修改报修单")
    @ApiImplicitParam(name = "RepairRepTb", value = "修改报修单", dataType = "RepairRepTb")
    @PreAuthorize("@ss.hasPermi('repair:tb:edit')")
    @Log(title = "报修单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairRepTb repairRepTb) {
        return toAjax(repairRepTbService.updateRepairRepTb(repairRepTb));
    }

    /**
     * 删除报修单
     */
    @ApiOperation("删除报修单")
    @ApiImplicitParam(name = "repairIds", value = "报修单ID", required = true, dataType = "int,int[]", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:tb:remove')")
    @Log(title = "报修单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{repairIds}")
    public AjaxResult remove(@PathVariable Long[] repairIds) {
        return toAjax(repairRepTbService.deleteRepairRepTbByIds(repairIds));
    }

    /**
     * 导出报修单列表
     */
    @ApiOperation("导出报修单列表")
    @PreAuthorize("@ss.hasPermi('repair:tb:export')")
    @Log(title = "报修单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairRepTb repairRepTb) {
        List<RepairRepTb> list = repairRepTbService.selectRepairRepTbList(repairRepTb, null);
        ExcelUtil<RepairRepTb> util = new ExcelUtil<RepairRepTb>(RepairRepTb.class);
        return util.exportExcel(list, "报修单数据");
    }

    /**
     * 通过部门ID查询用户
     *
     * @param deptId 部门id
     * @return AjaxResult
     */
    @ApiOperation("通过部门ID查询用户")
    @ApiImplicitParam(name = "deptId", value = "部门ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:tb:transferToPer')")
    @GetMapping("/findByDeptIdForUser")
    public AjaxResult findByDeptIdForUser(@ApiParam(name = "deptId", value = "部门ID") Long deptId) {
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(deptId);
        Long[] roleIds = {101L};
        sysUser.setRoleIds(roleIds);
        List<SysUser> list = sysUserService.selectUserList(sysUser);
        return AjaxResult.success(list);
    }

    /**
     * 查询所有部门信息
     *
     * @return AjaxResult
     */
    @ApiOperation("查询所有部门")
    @GetMapping("/findAllDept")
    public AjaxResult findAllDept() {
        SysDept sysDept = new SysDept();
        List<SysDept> depts = deptService.selectDeptList(sysDept);
        return AjaxResult.success(depts);
    }

    /**
     * 销单,传入的json格式： repairId：报修单ID
     */
    @ApiOperation("根据报修单ID销单")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "repairId", value = "报修单ID", required = true, dataType = "String", paramType = "path"),
        @ApiImplicitParam(name = "repairedState",value = "维修后状态",required = true,dataType = "long",paramType = "path"),
        @ApiImplicitParam(name = "processDescribe",value = "维修过程描述",required = true,dataType = "String",paramType = "query")
    })
    @PreAuthorize("@ss.hasPermi('repair:tb:destroy')")
    @Log(title = "报修单", businessType = BusinessType.UPDATE)
    @GetMapping("/relieveRepair/{repairId}/{repairedState}")
    public AjaxResult relieveRepair(@PathVariable("repairId") String repairId,@PathVariable("repairedState") int repairedState, String processDescribe) {
        long repId = Long.parseLong(repairId);
        RepairRepTb repairRepTb = new RepairRepTb();
        repairRepTb.setRepairId(repId);
        // 标记【已完成】状态
        repairRepTb.setRepairState(4);
        // 标记【已销单】状态
        repairRepTb.setRepairDestory(1);
        // 设置销单时间
        repairRepTb.setRepairDestoryTime(new Date());
        //标记维修后状态
        repairRepTb.setRepairedState(repairedState);
        int i = repairRepTbService.updateRepairRepTb(repairRepTb);

        // 维修过程表记录
        RepairProcess process = new RepairProcess();
        process.setRepaireId(repId);
        List<RepairProcess> processList = processService.selectRepairProcessList(process);
        if(processList.size()>0){
            RepairProcess p = processList.get(0);
            p.setProcessDescribe(processDescribe);
            processService.updateRepairProcess(p);
        }
        return i == 0 ? AjaxResult.error() : AjaxResult.success("销单操作成功");
    }
}
