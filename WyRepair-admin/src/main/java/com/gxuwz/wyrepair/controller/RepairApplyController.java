package com.gxuwz.wyrepair.controller;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.domain.entity.SysRole;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.ServletUtils;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.domain.RepairRepTb;
import com.gxuwz.wyrepair.framework.web.service.TokenService;
import com.gxuwz.wyrepair.domain.RepairApply;
import com.gxuwz.wyrepair.service.IRepairApplyService;
import com.gxuwz.wyrepair.service.IRepairProcessService;
import com.gxuwz.wyrepair.service.IRepairRepImgService;
import com.gxuwz.wyrepair.service.IRepairRepTbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 申请单Controller
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@RestController
@RequestMapping("/repair/apply")
public class RepairApplyController extends BaseController {

    @Autowired
    private IRepairApplyService repairApplyService;
    @Autowired
    private IRepairRepTbService repTbService;
    @Autowired
    private IRepairRepImgService repImgService;
    @Autowired
    private IRepairProcessService processService;
    @Autowired
    private TokenService tokenService;


    /**
     * 查询申请单列表
     */
    @PreAuthorize("@ss.hasPermi('repair:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairApply repairApply) {
        startPage();
        // 获取用户信息
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        List<SysRole> roles = user.getRoles();
        SysRole sysRole = roles.get(0);
        if ("repairadmin".equals(sysRole.getRoleKey()))
        {
            repairApply.setApplyDep(user.getDeptId());
            List<RepairApply> list = repairApplyService.selectRepairApplyList(repairApply);
            return getDataTable(list);
        }
        else {
            List<RepairApply> list = repairApplyService.selectRepairApplyList(repairApply);
            return getDataTable(list);
        }
    }


    /**
     * 获取申请单详细信息
     */
    @PreAuthorize("@ss.hasPermi('repair:apply:query')")
    @GetMapping(value = "/{applyId}")
    public AjaxResult getInfo(@PathVariable("applyId") Long applyId) {
        return AjaxResult.success(repairApplyService.selectRepairApplyById(applyId));
    }

    /**
     * 新增报修申请单
     */
    @PreAuthorize("@ss.hasPermi('repair:apply:add')")
    @Log(title = "报修申请单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairApply repairApply,int isOrder){
        // 申请报修原件保存
        return repairApplyService.insertRepairApply(repairApply,isOrder);
    }

    /**
     * 修改申请单
     */

    @PreAuthorize("@ss.hasPermi('repair:apply:edit')")
    @Log(title = "申请单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairApply repairApply) {
        return toAjax(repairApplyService.updateRepairApply(repairApply));
    }

    /**
     * 删除申请单
     */
   @PreAuthorize("@ss.hasPermi('repair:apply:remove')")
    @Log(title = "申请单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{applyIds}")
    public AjaxResult remove(@PathVariable Long[] applyIds) {
        return toAjax(repairApplyService.deleteRepairApplyByIds(applyIds));
    }

    /**
     * 导出申请单列表
     */
    @PreAuthorize("@ss.hasPermi('repair:apply:export')")
    @Log(title = "申请单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairApply repairApply) {
        List<RepairApply> list = repairApplyService.selectRepairApplyList(repairApply);
        ExcelUtil<RepairApply> util = new ExcelUtil<RepairApply>(RepairApply.class);
        return util.exportExcel(list, "申请单数据");
    }
}

