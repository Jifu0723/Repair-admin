package com.gxuwz.wyrepair.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.domain.RepairWorker;
import com.gxuwz.wyrepair.service.IRepairWorkerService;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;

/**
 * 维修工Controller
 * 
 * @author ruoyi
 * @date 2021-09-27
 */
@RestController
@RequestMapping("/system/worker")
public class RepairWorkerController extends BaseController
{
    @Autowired
    private IRepairWorkerService repairWorkerService;

    /**
     * 查询维修工列表
     */
    @PreAuthorize("@ss.hasPermi('system:worker:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairWorker repairWorker)
    {
        startPage();
        List<RepairWorker> list = repairWorkerService.selectRepairWorkerList(repairWorker);
        return getDataTable(list);
    }

    /**
     * 导出维修工列表
     */
    @PreAuthorize("@ss.hasPermi('system:worker:export')")
    @Log(title = "维修工", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairWorker repairWorker)
    {
        List<RepairWorker> list = repairWorkerService.selectRepairWorkerList(repairWorker);
        ExcelUtil<RepairWorker> util = new ExcelUtil<RepairWorker>(RepairWorker.class);
        return util.exportExcel(list, "维修工数据");
    }

    /**
     * 获取维修工详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:worker:query')")
    @GetMapping(value = "/{repairWorkerId}")
    public AjaxResult getInfo(@PathVariable("repairWorkerId") Long repairWorkerId)
    {
        return AjaxResult.success(repairWorkerService.selectRepairWorkerByRepairWorkerId(repairWorkerId));
    }

    /**
     * 新增维修工
     */
    @PreAuthorize("@ss.hasPermi('system:worker:add')")
    @Log(title = "维修工", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairWorker repairWorker)
    {
        return toAjax(repairWorkerService.insertRepairWorker(repairWorker));
    }

    /**
     * 修改维修工
     */
    @PreAuthorize("@ss.hasPermi('system:worker:edit')")
    @Log(title = "维修工", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairWorker repairWorker)
    {
        return toAjax(repairWorkerService.updateRepairWorker(repairWorker));
    }

    /**
     * 删除维修工
     */
    @PreAuthorize("@ss.hasPermi('system:worker:remove')")
    @Log(title = "维修工", businessType = BusinessType.DELETE)
	@DeleteMapping("/{repairWorkerIds}")
    public AjaxResult remove(@PathVariable Long[] repairWorkerIds)
    {
        return toAjax(repairWorkerService.deleteRepairWorkerByRepairWorkerIds(repairWorkerIds));
    }
}
