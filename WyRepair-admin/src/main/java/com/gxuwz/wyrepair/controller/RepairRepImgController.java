package com.gxuwz.wyrepair.controller;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.domain.RepairRepImg;
import com.gxuwz.wyrepair.service.IRepairRepImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报修单图片Controller
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@RestController
@RequestMapping("/repair/repimg")
public class RepairRepImgController extends BaseController {
    @Autowired
    private IRepairRepImgService repairRepImgService;

    /**
     * 查询报修单图片列表
     */
    @PreAuthorize("@ss.hasPermi('repair:repimg:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairRepImg repairRepImg) {
        startPage();
        List<RepairRepImg> list = repairRepImgService.selectRepairRepImgList(repairRepImg);
        return getDataTable(list);
    }

    /**
     * 导出报修单图片列表
     */
    @PreAuthorize("@ss.hasPermi('repair:repimg:export')")
    @Log(title = "报修单图片", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairRepImg repairRepImg) {
        List<RepairRepImg> list = repairRepImgService.selectRepairRepImgList(repairRepImg);
        ExcelUtil<RepairRepImg> util = new ExcelUtil<RepairRepImg>(RepairRepImg.class);
        return util.exportExcel(list, "报修单图片数据");
    }

    /**
     * 获取报修单图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('repair:repimg:query')")
    @GetMapping(value = "/{repimgId}")
    public AjaxResult getInfo(@PathVariable("repimgId") Long repimgId) {
        return AjaxResult.success(repairRepImgService.selectRepairRepImgById(repimgId));
    }

    /**
     * 修改报修单图片
     */

    @PreAuthorize("@ss.hasPermi('repair:repimg:edit')")
    @Log(title = "报修单图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairRepImg repairRepImg) {
        return toAjax(repairRepImgService.updateRepairRepImg(repairRepImg));
    }

    /**
     * 删除报修单图片
     */
    @PreAuthorize("@ss.hasPermi('repair:repimg:remove')")
    @Log(title = "报修单图片", businessType = BusinessType.DELETE)
    @DeleteMapping("/{repimgIds}")
    public AjaxResult remove(@PathVariable Long[] repimgIds) {
        return toAjax(repairRepImgService.deleteRepairRepImgByIds(repimgIds));
    }




}
