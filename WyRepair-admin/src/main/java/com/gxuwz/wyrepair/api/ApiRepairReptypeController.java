package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.domain.RepairReptype;
import com.gxuwz.wyrepair.service.IRepairReptypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 维修类型Controller
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Api(tags = "报修维修类型模块")
@RestController
@RequestMapping("/api/repair/reptype")
public class ApiRepairReptypeController extends BaseController
{
    @Autowired
    private IRepairReptypeService repairReptypeService;

    /**
     * 查询维修类型列表
     */
    @ApiOperation("查询维修类型列表")
    @GetMapping("/list")
    public TableDataInfo list(RepairReptype repairReptype)
    {
        startPage();
        List<RepairReptype> list = repairReptypeService.selectRepairReptypeList(repairReptype);
        return getDataTable(list);
    }

    /**
     * 导出维修类型列表
     */
    @ApiOperation("导出维修类型列表")
    @PreAuthorize("@ss.hasPermi('repair:reptype:export')")
    @Log(title = "维修类型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairReptype repairReptype)
    {
        List<RepairReptype> list = repairReptypeService.selectRepairReptypeList(repairReptype);
        ExcelUtil<RepairReptype> util = new ExcelUtil<RepairReptype>(RepairReptype.class);
        return util.exportExcel(list, "维修类型数据");
    }

    /**
     * 获取维修类型详细信息
     */
    @ApiOperation("获取维修类型详细信息")
    @PreAuthorize("@ss.hasPermi('repair:reptype:query')")
    @GetMapping(value = "/{repairTypeId}")
    public AjaxResult getInfo(@PathVariable("repairTypeId") Long repairTypeId)
    {
        return AjaxResult.success(repairReptypeService.selectRepairReptypeById(repairTypeId));
    }

    /**
     * 新增维修类型
     */
    @ApiOperation("新增维修类型")
    @PreAuthorize("@ss.hasPermi('repair:reptype:add')")
    @Log(title = "维修类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairReptype repairReptype)
    {
        return toAjax(repairReptypeService.insertRepairReptype(repairReptype));
    }

    /**
     * 修改维修类型
     */
    @ApiOperation("修改维修类型")
    @PreAuthorize("@ss.hasPermi('repair:reptype:edit')")
    @Log(title = "维修类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairReptype repairReptype)
    {
        return toAjax(repairReptypeService.updateRepairReptype(repairReptype));
    }

    /**
     * 删除维修类型
     */
    @ApiOperation("删除维修类型")
    @PreAuthorize("@ss.hasPermi('repair:reptype:remove')")
    @Log(title = "维修类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/repairTypeIds")
    public AjaxResult remove(@RequestBody HashMap<String,String> repairTypeIds)
    {
        String repairTypeId = repairTypeIds.get("appimgIds");
        return toAjax(repairReptypeService.deleteRepairReptypeByIds(new Long[]{Long.valueOf(repairTypeId)}));
    }
}
