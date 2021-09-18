package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.domain.RepairRepTb;
import com.gxuwz.wyrepair.domain.RepairRepcomment;
import com.gxuwz.wyrepair.service.IRepairRepTbService;
import com.gxuwz.wyrepair.service.IRepairRepcommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报修单评论Controller
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Api(tags = "报修评论模块")
@RestController
@RequestMapping("/api/repair/repcomment")
public class ApiRepairRepcommentController extends BaseController {
    @Autowired
    private IRepairRepcommentService repairRepcommentService;
    @Autowired
    private IRepairRepTbService repTbService;

    /**
     * 查询报修单评论列表
     */
    @ApiOperation("查询报修单评论列表")
    @PreAuthorize("@ss.hasPermi('repair:repcomment:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairRepcomment repairRepcomment) {
        startPage();
        List<RepairRepcomment> list = repairRepcommentService.selectRepairRepcommentList(repairRepcomment);
        return getDataTable(list);
    }

    /**
     * 导出报修单评论列表
     */
    @ApiOperation("导出报修单评论列表")
    @PreAuthorize("@ss.hasPermi('repair:repcomment:export')")
    @Log(title = "报修单评论", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairRepcomment repairRepcomment) {
        List<RepairRepcomment> list = repairRepcommentService.selectRepairRepcommentList(repairRepcomment);
        ExcelUtil<RepairRepcomment> util = new ExcelUtil<RepairRepcomment>(RepairRepcomment.class);
        return util.exportExcel(list, "报修单评论数据");
    }

    /**
     * 获取报修单评论详细信息
     */
    @ApiOperation("获取报修单评论详细信息")
    @ApiImplicitParam(name = "repcommentId", value = "报修单评论ID", required = true, dataType = "int", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:repcomment:query')")
    @GetMapping(value = "/{repcommentId}")
    public AjaxResult getInfo(@PathVariable("repcommentId") Long repcommentId) {
        return AjaxResult.success(repairRepcommentService.selectRepairRepcommentById(repcommentId));
    }

    /**
     * 新增报修单评论
     */
    @ApiOperation("新增报修单评论")
    @ApiImplicitParam(name = "RepairRepcomment", value = "新增报修单评论", dataType = "RepairRepcomment")
    @PreAuthorize("@ss.hasPermi('repair:repcomment:add')")
    @Log(title = "报修单评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairRepcomment repairRepcomment) {
        // 更新报修信息
        RepairRepTb repTb = new RepairRepTb();
        repTb.setRepairId(repairRepcomment.getRepcId());
        repTb.setRepairComment(1);
        repTbService.updateRepairRepTb(repTb);
        return toAjax(repairRepcommentService.insertRepairRepcomment(repairRepcomment));
    }

    /**
     * 修改报修单评论
     */
    @ApiOperation("修改报修单评论")
    @ApiImplicitParam(name = "RepairRepcomment", value = "修改报修单评论", dataType = "RepairRepcomment")
    @PreAuthorize("@ss.hasPermi('repair:repcomment:edit')")
    @Log(title = "报修单评论", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairRepcomment repairRepcomment) {
        return toAjax(repairRepcommentService.updateRepairRepcomment(repairRepcomment));
    }

    /**
     * 删除报修单评论
     */
    @ApiOperation("删除报修单评论")
    @ApiImplicitParam(name = "repcommentIds", value = "报修单评论ID", required = true, dataType = "int,int[]", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:repcomment:remove')")
    @Log(title = "报修单评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/{repcommentIds}")
    public AjaxResult remove(@PathVariable Long[] repcommentIds) {
        return toAjax(repairRepcommentService.deleteRepairRepcommentByIds(repcommentIds));
    }


}
