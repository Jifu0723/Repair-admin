package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import com.gxuwz.wyrepair.common.core.domain.model.LoginUser;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.ServletUtils;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.domain.RepairReptransfer;
import com.gxuwz.wyrepair.framework.web.service.TokenService;
import com.gxuwz.wyrepair.service.IRepairReptransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 转单记录Controller
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Api(tags = "报修转单记录模块")
@RestController
@RequestMapping("/api/repair/reptransfer")
public class ApiRepairReptransferController extends BaseController
{
    @Autowired
    private IRepairReptransferService repairReptransferService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询转单记录列表
     */
    @ApiOperation("查询转单记录列表")
    @PreAuthorize("@ss.hasPermi('repair:reptransfer:list')")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam Map<String, Object> params)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        params.put("reptransfeUser",sysUser.getUserId());
        List<Map<String,Object>> list = repairReptransferService.selectReptransferList(params);
        return getDataTable(list);
    }

    /**
     * 导出转单记录列表
     */
    @ApiOperation("导出转单记录列表")
    @PreAuthorize("@ss.hasPermi('repair:reptransfer:export')")
    @Log(title = "转单记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairReptransfer repairReptransfer)
    {
        List<RepairReptransfer> list = repairReptransferService.selectRepairReptransferList(repairReptransfer);
        ExcelUtil<RepairReptransfer> util = new ExcelUtil<RepairReptransfer>(RepairReptransfer.class);
        return util.exportExcel(list, "转单记录数据");
    }

    /**
     * 获取转单记录详细信息
     */
    @ApiOperation("获取转单记录详细信息")
    @ApiImplicitParam(name = "reptransferId", value = "转单记录D", required = true, dataType = "int", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:reptransfer:query')")
    @GetMapping(value = "/{reptransferId}")
    public AjaxResult getInfo(@PathVariable("reptransferId") Long reptransferId)
    {
        return AjaxResult.success(repairReptransferService.selectRepairReptransferById(reptransferId));
    }

    /**
     * 新增转单记录
     */
    @ApiOperation("新增转单记录")
    @ApiImplicitParam(name = "RepairReptransfer", value = "新增转单记录", dataType = "RepairReptransfer")
    @PreAuthorize("@ss.hasPermi('repair:reptransfer:add')")
    @Log(title = "转单记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairReptransfer repairReptransfer)
    {
        return toAjax(repairReptransferService.insertRepairReptransfer(repairReptransfer));
    }

    /**
     * 修改转单记录
     */
    @ApiOperation("修改转单记录")
    @PreAuthorize("@ss.hasPermi('repair:reptransfer:edit')")
    @Log(title = "转单记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairReptransfer repairReptransfer)
    {
        return toAjax(repairReptransferService.updateRepairReptransfer(repairReptransfer));
    }

    /**
     * 删除转单记录
     */
    @ApiOperation("删除转单记录")
    @ApiImplicitParam(name = "reptransferIds", value = "转单记录ID", required = true, dataType = "int,int[]", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:reptransfer:remove')")
    @Log(title = "转单记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reptransferIds}")
    public AjaxResult remove(@PathVariable Long[] reptransferIds)
    {
        return toAjax(repairReptransferService.deleteRepairReptransferByIds(reptransferIds));
    }
}
