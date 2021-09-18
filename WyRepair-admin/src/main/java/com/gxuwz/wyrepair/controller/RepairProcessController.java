package com.gxuwz.wyrepair.controller;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.domain.RepairProcess;
import com.gxuwz.wyrepair.service.IRepairProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 维修过程描述Controller
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@RestController
@RequestMapping("/repair/process")
public class RepairProcessController extends BaseController
{
    @Autowired
    private IRepairProcessService repairProcessService;

    /**
     * 查询维修过程描述列表
     */
    @PreAuthorize("@ss.hasPermi('repair:process:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairProcess repairProcess)
    {
        startPage();
        List<RepairProcess> list = repairProcessService.selectRepairProcessList(repairProcess);
        return getDataTable(list);
    }

    /**
     * 导出维修过程描述列表
     */
    @PreAuthorize("@ss.hasPermi('repair:process:export')")
    @Log(title = "维修过程描述", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairProcess repairProcess)
    {
        List<RepairProcess> list = repairProcessService.selectRepairProcessList(repairProcess);
        ExcelUtil<RepairProcess> util = new ExcelUtil<RepairProcess>(RepairProcess.class);
        return util.exportExcel(list, "维修过程描述数据");
    }

    /**
     * 获取维修过程描述详细信息
     */
    @PreAuthorize("@ss.hasPermi('repair:process:query')")
    @GetMapping(value = "/{processId}")
    public AjaxResult getInfo(@PathVariable("processId") Long processId)
    {
        return AjaxResult.success(repairProcessService.selectRepairProcessById(processId));
    }

    /**
     * 新增维修过程描述
     */
   @PreAuthorize("@ss.hasPermi('repair:process:add')")
    @Log(title = "维修过程描述", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairProcess repairProcess)
    {
        return toAjax(repairProcessService.insertRepairProcess(repairProcess));
    }

    /**
     * 修改维修过程描述
     */
    @PreAuthorize("@ss.hasPermi('repair:process:edit')")
    @Log(title = "维修过程描述", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairProcess repairProcess)
    {
        return toAjax(repairProcessService.updateRepairProcess(repairProcess));
    }

    /**
     * 删除维修过程描述
     */
   @PreAuthorize("@ss.hasPermi('repair:process:remove')")
    @Log(title = "维修过程描述", businessType = BusinessType.DELETE)
	@DeleteMapping("/{processIds}")
    public AjaxResult remove(@PathVariable Long[] processIds)
    {
        return toAjax(repairProcessService.deleteRepairProcessByIds(processIds));
    }
}
