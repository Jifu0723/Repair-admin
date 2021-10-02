package com.gxuwz.wyrepair.controller;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
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
import com.gxuwz.wyrepair.util.DateUtil;
import com.gxuwz.wyrepair.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 报修单Controller
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@RestController
@RequestMapping("/repair/tb")
public class RepairRepTbController extends BaseController {
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

    /**
     * 查询报修单列表
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:list')")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam Map<String, Object> params,RepairRepTb repairRepTb) {
        startPage();
        // 获取报修时间
        String repairTime = (String) params.get("repairTime");
        // 获取用户信息
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        List<SysRole> roles = user.getRoles();
        SysRole sysRole = roles.get(0);
        System.out.println(sysRole);
        repairRepTb.setCurWork(1);
        List<RepairRepTb> list = new ArrayList<>();
        if ("repairadmin".equals(sysRole.getRoleKey()) && (repairTime!=null && !"".equals(repairTime))) {
            LocalDateTime localDateTime = DateUtil.parseToLocaDateTime(repairTime, "yyyy-MM-dd HH:mm:ss");
            System.out.println(localDateTime);
            params.put("repairYear",localDateTime.getYear());
            System.out.println(localDateTime.getYear());
            params.put("repairMonth",localDateTime.getMonthValue());
            System.out.println(localDateTime.getMonthValue());
            params.put("repairDay",localDateTime.getDayOfMonth());
            System.out.println(localDateTime.getDayOfMonth());
            repairRepTb.setRepairDep(user.getDeptId());
            list = repairRepTbService.selectRepairPersonRepTbList(repairRepTb,null);
        } else if ("repair".equals(sysRole.getRoleKey()) && (repairTime!=null && !"".equals(repairTime))) {
            repairRepTb.setRepairDep(user.getDeptId());
            list = repairRepTbService.selectRepairPersonRepTbList(repairRepTb,user.getUserId());
        }else if ("SuperAdmin".equals(sysRole.getRoleKey()) && (repairTime!=null && !"".equals(repairTime))){
            list = repairRepTbService.queryRepairOrderList(params);
        }
        params.remove("repairTime");
        return getDataTable(list);
    }

    /**
     * 按时间统计报修信息输出日、周、月报表
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:list')")
    @GetMapping("/countrepairOrderByType")
    public AjaxResult countrepairOrderByType(@RequestParam Map<String, Object> params) {
        Map map = repairRepTbService.countrepairOrderByType(params);
        return AjaxResult.success(map);
    }


    /**
     * 查询【待修】报修单列表
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:list')")
    @GetMapping("/waitReplist")
    public TableDataInfo waitRepairlist(RepairRepTb repairRepTb) {
        startPage();
        // 获取用户信息
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        repairRepTb.setCurWork(1);
        repairRepTb.setRepairState(1);
        repairRepTb.setRepairDep(user.getDeptId());

        List<RepairRepTb> list = repairRepTbService.selectRepairPersonRepTbList(repairRepTb,null);
        return getDataTable(list);
    }

    /**
     * 维修人员接单
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:add')")
    @PostMapping("/receiveRep")
    public AjaxResult receiveRep(RepairRepTb repairRepTb) {
        /** 验证维修单是否已被接单*/
        RepairRepTb tb = repairRepTbService.selectRepairRepTbById(repairRepTb.getRepairId());
        if(tb.getRepairState()==1){
            // 获取用户信息
            SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
            repairRepTb.setRepairDep(user.getDeptId());
            /** 更新维修单状态*/
            repairRepTb.setRepairState(2);
            repairRepTbService.updateRepairRepTb(repairRepTb);
            /** 维修单更新 */
            RepairApply apply = applyService.selectRepairApplyById(repairRepTb.getApplyId());
            /** 维修过程信息记录*/
            RepairProcess process = new RepairProcess();
            process.initProcess(apply,repairRepTb,user.getUserName(),user.getUserId());
            processService.insertRepairProcess(process);
            return AjaxResult.success("接单成功！");
        }
        return AjaxResult.error("很抱歉，该维修单已被其他维修人员接单！");
    }


    /*
     * 转单{
     *     1. 维修专员【跨部门转单】
     *     2. 维修人员【转维修人员】
     *     3. 维修人员【转回部门内部单】
     * }
     */

    /**
     * 1. 维修专员【跨部门转单】
     *
     * @param repairRepTb 报修单
     * @param applyDep    所转单的部门id
     * @param repairType  维修类型
     * @return AjaxResult
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:transferToDep')")
    @GetMapping("/transferFromDep")
    public AjaxResult transferFromDep(RepairRepTb repairRepTb, long applyDep, Long repairType) {
        // 获取用户信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        RepairApply apply = applyService.selectRepairApplyById(repairRepTb.getApplyId());
        SysUser sysUser = loginUser.getUser();
        // 新的维修单
        RepairRepTb newRepair = new RepairRepTb();
        newRepair.initRepairApply(apply, RepairCodeGen.genApplyNo(), applyDep);
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
     * 2. 维修人员【转维修人员】
     *
     * @param repairRepTb 报修单
     * @param tUser       转给的维修人员
     * @return AjaxResult
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:transferToPer')")
    @GetMapping("/transferRepair")
    public AjaxResult transferRepair(RepairRepTb repairRepTb,
                                     String reptransfeMessage,
                                     SysUser tUser) {
        //获取申请单信息
        RepairApply apply = applyService.selectRepairApplyById(repairRepTb.getApplyId());
//        SysUser sysUser = sysUserService.selectUserById(tUser.getUserId());
        // 原报修单运转状态改变
        repairRepTb.setCurWork(0);
        repairRepTbService.updateRepairRepTb(repairRepTb);
        // 新的维修单
        RepairRepTb newRepair = new RepairRepTb();
        newRepair.initRepairApply(apply, RepairCodeGen.genApplyNo(), tUser.getDeptId());
        repairRepTbService.insertRepairRepTb(newRepair);
        // 维修单过程信息保存
        RepairProcess process = new RepairProcess();
        process.initProcess(apply, newRepair, tUser.getUserName(), tUser.getUserId());
        processService.insertRepairProcess(process);
        // 转单记录保存
        RepairReptransfer transferRep = new RepairReptransfer();
        transferRep.initTransfer(repairRepTb, newRepair, apply.getApplyId(), tUser.getUserId(), 1);
        transferRep.setReptransfeToUser(tUser.getUserId());
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
    @PreAuthorize("@ss.hasPermi('repair:tb:transferToShare')")
    @GetMapping("/transferToDep")
    public AjaxResult transferDep(RepairRepTb repairRepTb, String transferName, Long repairUserid) {

        // 获取用户信息——角色
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        RepairApply apply = applyService.selectRepairApplyById(repairRepTb.getApplyId());
        SysUser sysUser = sysUserService.selectUserById(repairUserid);
        // 新的维修单
        RepairRepTb newRepair = new RepairRepTb();
        newRepair.initRepairApply(apply, RepairCodeGen.genApplyNo(), sysUser.getDeptId());
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
        rt.setReptToNo(repairRepTb.getRepairNo());
        List<RepairReptransfer> repairReptransfers = transferService.selectRepairReptransferList(rt);
        if (repairReptransfers.size() > 0 && repairReptransfers.get(0) != null) {
            transferRep.setParentId(repairReptransfers.get(0).getReptransferId());
        }
        transferService.insertRepairReptransfer(transferRep);
        return AjaxResult.success();
    }

    /**
     * 接单
     *
     * @param repTb           维修单
     * @return AjaxResult
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:edit')")
    @Log(title = "接单", businessType = BusinessType.UPDATE)
    @RequestMapping("/receive")
    public AjaxResult getRepair(@RequestBody RepairRepTb repTb) {
        System.out.println(repTb);
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
     * 维修专员派单
     *
     * @param repTb      维修单
     * @param repairName 维修人员姓名
     * @param repairNo   维修人员工号
     * @return
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:edit')")
    @Log(title = "派单", businessType = BusinessType.UPDATE)
    @RequestMapping("/arrange")
    public AjaxResult SendRepair(RepairRepTb repTb, String repairName, Long repairNo) {

        //更新维修单状态
        repTb.setRepairState(2);
        repairRepTbService.updateRepairRepTb(repTb);

        RepairApply apply = applyService.selectRepairApplyById(repTb.getApplyId());

        //生成维修过程信息
        RepairProcess process = new RepairProcess();
        process.initProcess(apply, repTb, repairName, repairNo);
        processService.insertRepairProcess(process);
        return AjaxResult.success();
    }


    /**
     * 获取报修单详细信息
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:query')")
    @GetMapping(value = "/{repairId}")
    public AjaxResult getInfo(@PathVariable("repairId") Long repairId) {
        return AjaxResult.success(repairRepTbService.selectRepairRepTbById(repairId));
    }

    /**
     * 新增报修单
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:add')")
    @Log(title = "报修单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairRepTb repairRepTb) {
        return toAjax(repairRepTbService.insertRepairRepTb(repairRepTb));
    }

    /**
     * 修改报修单
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:edit')")
    @Log(title = "报修单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairRepTb repairRepTb) {
        return toAjax(repairRepTbService.updateRepairRepTb(repairRepTb));
    }

    /**
     * 删除报修单
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:remove')")
    @Log(title = "报修单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{repairIds}")
    public AjaxResult remove(@PathVariable Long[] repairIds) {
        return toAjax(repairRepTbService.deleteRepairRepTbByIds(repairIds));
    }

    /**
     * 导出报修单列表
     */
    @PreAuthorize("@ss.hasPermi('repair:tb:export')")
    @Log(title = "报修单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairRepTb repairRepTb) {
        List<RepairRepTb> list = repairRepTbService.selectRepairPersonRepTbList(repairRepTb,null);
        ExcelUtil<RepairRepTb> util = new ExcelUtil<RepairRepTb>(RepairRepTb.class);
        return util.exportExcel(list, "报修单数据");
    }
}
