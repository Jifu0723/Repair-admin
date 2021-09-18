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
import com.gxuwz.wyrepair.domain.RepeairParts;
import com.gxuwz.wyrepair.service.IRepeairPartsService;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;

/**
 * 配件信息Controller
 * 
 * @author ruoyi
 * @date 2021-09-17
 */
@RestController
@RequestMapping("/repair/parts")
public class RepeairPartsController extends BaseController
{
    @Autowired
    private IRepeairPartsService repeairPartsService;

    /**
     * 查询配件信息列表
     */
    @PreAuthorize("@ss.hasPermi('repair:parts:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepeairParts repeairParts)
    {
        startPage();
        List<RepeairParts> list = repeairPartsService.selectRepeairPartsList(repeairParts);
        return getDataTable(list);
    }

    /**
     * 导出配件信息列表
     */
    @PreAuthorize("@ss.hasPermi('repair:parts:export')")
    @Log(title = "配件信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepeairParts repeairParts)
    {
        List<RepeairParts> list = repeairPartsService.selectRepeairPartsList(repeairParts);
        ExcelUtil<RepeairParts> util = new ExcelUtil<RepeairParts>(RepeairParts.class);
        return util.exportExcel(list, "配件信息数据");
    }

    /**
     * 获取配件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('repair:parts:query')")
    @GetMapping(value = "/{partsId}")
    public AjaxResult getInfo(@PathVariable("partsId") Long partsId)
    {
        return AjaxResult.success(repeairPartsService.selectRepeairPartsByPartsId(partsId));
    }

    /**
     * 新增配件信息
     */
    @PreAuthorize("@ss.hasPermi('repair:parts:add')")
    @Log(title = "配件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepeairParts repeairParts)
    {
        return toAjax(repeairPartsService.insertRepeairParts(repeairParts));
    }

    /**
     * 修改配件信息
     */
    @PreAuthorize("@ss.hasPermi('repair:parts:edit')")
    @Log(title = "配件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepeairParts repeairParts)
    {
        return toAjax(repeairPartsService.updateRepeairParts(repeairParts));
    }

    /**
     * 删除配件信息
     */
    @PreAuthorize("@ss.hasPermi('repair:parts:remove')")
    @Log(title = "配件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{partsIds}")
    public AjaxResult remove(@PathVariable Long[] partsIds)
    {
        return toAjax(repeairPartsService.deleteRepeairPartsByPartsIds(partsIds));
    }
}
