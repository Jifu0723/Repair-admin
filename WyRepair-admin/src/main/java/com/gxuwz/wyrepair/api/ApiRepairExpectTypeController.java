package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.domain.RepairExpectType;
import com.gxuwz.wyrepair.service.IRepairExpectTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 期望维修类型Controller
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Api(tags = "报修期待维修类型模块")
@RestController
@RequestMapping("/api/repair/type")
public class ApiRepairExpectTypeController extends BaseController
{
    @Autowired
    private IRepairExpectTypeService repairExpectTypeService;

    /**
     * 查询期望维修类型列表
     */
    @ApiOperation("获取期望维修类型")
    @PreAuthorize("@ss.hasPermi('repair:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairExpectType repairExpectType)
    {
        startPage();
        List<RepairExpectType> list = repairExpectTypeService.selectRepairExpectTypeList(repairExpectType);
        return getDataTable(list);
    }

    /**
     * 导出期望维修类型列表
     */
    @ApiOperation("导出期望维修类型列表")
    @PreAuthorize("@ss.hasPermi('repair:type:export')")
    @Log(title = "期望维修类型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairExpectType repairExpectType)
    {
        List<RepairExpectType> list = repairExpectTypeService.selectRepairExpectTypeList(repairExpectType);
        ExcelUtil<RepairExpectType> util = new ExcelUtil<RepairExpectType>(RepairExpectType.class);
        return util.exportExcel(list, "期望维修类型数据");
    }

    /**
     * 获取期望维修类型详细信息
     */
    @ApiOperation("获取期望维修类型")
    @ApiImplicitParam(name = "repexpectTypeId", value = "ID", required = true, dataType = "int", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:type:query')")
    @GetMapping(value = "/{repexpectTypeId}")
    public AjaxResult getInfo(@PathVariable("repexpectTypeId") Long repexpectTypeId)
    {
        return AjaxResult.success(repairExpectTypeService.selectRepairExpectTypeById(repexpectTypeId));
    }

    /**
     * 新增期望维修类型
     */
    @ApiOperation("新增期望维修类型")
    @ApiImplicitParam(name = "RepairExpectType", value = "新增期望维修类型信息", dataType = "RepairExpectType")
    @Log(title = "期望维修类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairExpectType repairExpectType)
    {
        return toAjax(repairExpectTypeService.insertRepairExpectType(repairExpectType));
    }

    /**
     * 修改期望维修类型
     */
    @ApiOperation("修改期望维修类型")
    @ApiImplicitParam(name = "RepairExpectType", value = "修改期望维修类型", dataType = "RepairExpectType")
    @PreAuthorize("@ss.hasPermi('repair:type:edit')")
    @Log(title = "期望维修类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairExpectType repairExpectType)
    {
        return toAjax(repairExpectTypeService.updateRepairExpectType(repairExpectType));
    }

    /**
     * 删除期望维修类型
     */
    @ApiOperation("删除期望维修类型")
    @ApiImplicitParam(name = "repexpectTypeIds", value = "期望维修类型", required = true, dataType = "int，int[]", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:type:remove')")
    @Log(title = "期望维修类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{repexpectTypeIds}")
    public AjaxResult remove(@PathVariable Long[] repexpectTypeIds)
    {
        return toAjax(repairExpectTypeService.deleteRepairExpectTypeByIds(repexpectTypeIds));
    }
}
