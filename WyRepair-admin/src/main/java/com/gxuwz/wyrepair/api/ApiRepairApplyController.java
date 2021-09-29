package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.framework.web.service.TokenService;
import com.gxuwz.wyrepair.domain.RepairApply;
import com.gxuwz.wyrepair.service.IRepairApplyService;
import com.gxuwz.wyrepair.service.IRepairProcessService;
import com.gxuwz.wyrepair.service.IRepairRepImgService;
import com.gxuwz.wyrepair.service.IRepairRepTbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "报修申请单模块")
@RestController
@RequestMapping("/api/repair/apply")
public class ApiRepairApplyController extends BaseController {
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
    @ApiOperation("查询申请表list")
    @PreAuthorize("@ss.hasPermi('repair:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairApply repairApply) {
        startPage();
        List<RepairApply> list = repairApplyService.selectRepairApplyList(repairApply);
        return getDataTable(list);
    }

    /**
     * 新增报修申请单
     */
    @ApiOperation("报修申请单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "repairApply", value = "报修申请信息", dataType = "RepairApply"),
            @ApiImplicitParam(name = "isOrder", value = "维修人员是否自己接单标识", dataType = "long"),
    })
    @PreAuthorize("@ss.hasPermi('repair:apply:add')")
    @Log(title = "报修申请单", businessType = BusinessType.INSERT)
    @PostMapping("/{isOrder}")
    public AjaxResult add(@RequestBody RepairApply repairApply,@PathVariable int isOrder) {
        // 申请报修原件保存
        System.out.println(1111);
        return repairApplyService.insertRepairApply(repairApply, isOrder);

    }

    /**
     * 导出申请单列表
     */
    @ApiOperation("导出申请单列表")
    @PreAuthorize("@ss.hasPermi('repair:apply:export')")
    @Log(title = "申请单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairApply repairApply) {
        List<RepairApply> list = repairApplyService.selectRepairApplyList(repairApply);
        ExcelUtil<RepairApply> util = new ExcelUtil<RepairApply>(RepairApply.class);
        return util.exportExcel(list, "申请单数据");
    }

    /**
     * 获取申请单详细信息
     */
    @ApiOperation("获取申请单详细信息")
    @PreAuthorize("@ss.hasPermi('repair:apply:query')")
    @GetMapping(value = "/{applyId}")
    public AjaxResult getInfo(@PathVariable("applyId") Long applyId) {
        return AjaxResult.success(repairApplyService.selectRepairApplyById(applyId));
    }


    /**
     * 修改申请单
     */
    @ApiOperation("修改申请单")
    @PreAuthorize("@ss.hasPermi('repair:apply:edit')")
    @Log(title = "申请单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairApply repairApply) {
        return toAjax(repairApplyService.updateRepairApply(repairApply));
    }

    /**
     * 删除申请单
     */
    @ApiOperation("删除申请单")
    @ApiImplicitParam(name = "applyIds", value = "申请单id", required = true, dataType = "int,int[]", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:apply:remove')")
    @Log(title = "申请单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{applyIds}")
    public AjaxResult remove(@PathVariable Long[] applyIds) {
        return toAjax(repairApplyService.deleteRepairApplyByIds(applyIds));
    }
}
