package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.config.RuoYiConfig;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.domain.RepairAppImg;
import com.gxuwz.wyrepair.domain.RepairRepImg;
import com.gxuwz.wyrepair.service.IRepairAppImgService;
import com.gxuwz.wyrepair.service.IRepairRepImgService;
import com.gxuwz.wyrepair.util.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 报修单图片Controller
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Api(tags = "报修单拍照图片模块")
@RestController
@RequestMapping("/api/repair/repimg")
public class ApiRepairRepImgController extends BaseController
{
    @Autowired
    private IRepairRepImgService repairRepImgService;
    @Autowired
    private IRepairAppImgService appImgService;

    /**
     * 查询报修单图片列表
     */
    @ApiOperation("查询报修单图片列表")
    @PreAuthorize("@ss.hasPermi('repair:repimg:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairRepImg repairRepImg)
    {
        startPage();
        List<RepairRepImg> list = repairRepImgService.selectRepairRepImgList(repairRepImg);
        return getDataTable(list);
    }

    /**
     * 导出报修单图片列表
     */
    @ApiOperation("导出报修单图片列表")
    @PreAuthorize("@ss.hasPermi('repair:repimg:export')")
    @Log(title = "报修单图片", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairRepImg repairRepImg)
    {
        List<RepairRepImg> list = repairRepImgService.selectRepairRepImgList(repairRepImg);
        ExcelUtil<RepairRepImg> util = new ExcelUtil<RepairRepImg>(RepairRepImg.class);
        return util.exportExcel(list, "报修单图片数据");
    }

    /**
     * 获取报修单图片详细信息
     */
    @ApiOperation("获取报修单图片详细信息")
    @ApiImplicitParam(name = "repimgId", value = "报修单图片ID", required = true, dataType = "int", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:repimg:query')")
    @GetMapping(value = "/{repimgId}")
    public AjaxResult getInfo(@PathVariable("repimgId") Long repimgId)
    {
        return AjaxResult.success(repairRepImgService.selectRepairRepImgById(repimgId));
    }

    /**
     * 新增报修单图片
     */
    @ApiOperation("新增报修单图片")
    @ApiImplicitParam(name = "RepairRepImg", value = "新增报修单图片", dataType = "RepairRepImg")
    @PreAuthorize("@ss.hasPermi('repair:repimg:add')")
    @Log(title = "报修单图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(RepairRepImg repairRepImg, MultipartFile imgFile)
    {
        return toAjax(repairRepImgService.insertRepairRepImg(repairRepImg));
    }

    /**
     * 修改报修单图片
     */
    @ApiOperation("修改报修单图片")
    @ApiImplicitParam(name = "RepairRepImg", value = "修改报修单图片", dataType = "RepairRepImg")
    @PreAuthorize("@ss.hasPermi('repair:repimg:edit')")
    @Log(title = "报修单图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairRepImg repairRepImg)
    {
        return toAjax(repairRepImgService.updateRepairRepImg(repairRepImg));
    }

    /**
     * 删除报修单图片
     */
    @ApiOperation("删除报修单图片")
    @ApiImplicitParam(name = "repimgIds", value = "报修单图片ID", required = true, dataType = "int,int[]", paramType = "path")
    @PreAuthorize("@ss.hasPermi('repair:repimg:remove')")
    @Log(title = "报修单图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{repimgIds}")
    public AjaxResult remove(@PathVariable Long[] repimgIds)
    {
        return toAjax(repairRepImgService.deleteRepairRepImgByIds(repimgIds));
    }

    /**
     * 图片显示接口
     *
     * @param imgId    图片id
     * @param response 响应体
     */
    @GetMapping("/loadImg/{imgId}")
    public ResponseEntity<byte[]> loadRepairImg(@PathVariable Long imgId, HttpServletResponse response) throws IOException {
        RepairAppImg repImg = appImgService.selectRepairAppImgById(imgId);
        // 文件路径
        String dir = repImg.getAppimgPath();
        // 文件全名称
        String fullFileName = repImg.getAppimgName();
        byte[] imageContent;
        InputStream in = new FileInputStream(RuoYiConfig.getProfile() + dir + fullFileName);
        imageContent = FileUtils.inputStream2Byte(in);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }
}
