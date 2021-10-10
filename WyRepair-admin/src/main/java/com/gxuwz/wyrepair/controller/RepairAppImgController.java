package com.gxuwz.wyrepair.controller;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.config.RuoYiConfig;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.file.FileUploadUtils;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.domain.RepairAppImg;
import com.gxuwz.wyrepair.domain.RepairRepImg;
import com.gxuwz.wyrepair.service.IRepairAppImgService;
import com.gxuwz.wyrepair.service.IRepairRepImgService;
import com.gxuwz.wyrepair.util.CommonFileUtils;
import com.gxuwz.wyrepair.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 申请单图片Controller
 *
 * @author LMTeamTop-2018
 * @date 2021-05-23
 */
@RestController
@RequestMapping("/repair/appimg")
public class RepairAppImgController extends BaseController {
    @Autowired
    private IRepairAppImgService repairAppImgService;
    @Autowired
    private IRepairRepImgService repairRepImgService;

    /**
     * 查询申请单图片列表
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairAppImg repairAppImg) {
        startPage();
        List<RepairAppImg> list = repairAppImgService.selectRepairAppImgList(repairAppImg);
        return getDataTable(list);
    }

    /**downloadRepairImg
     * 下载报修图片
     */
    @GetMapping("/downloadRepairImg")
    public void downloadRepairImg(RepairAppImg repairAppImg, HttpServletResponse response) throws IOException {

        RepairAppImg repairAppImg1 = repairAppImgService.selectRepairAppImgById(repairAppImg.getAppimgId());
        String appimgPath = repairAppImg1.getAppimgPath();
        String appimgName = repairAppImg1.getAppimgName();
        CommonFileUtils.downloadUtil(response,RuoYiConfig.getProfile()+appimgPath+appimgName,appimgName);

    }

    /**
     * 导出申请单图片列表
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:export')")
    @Log(title = "申请单图片", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairAppImg repairAppImg) {
        List<RepairAppImg> list = repairAppImgService.selectRepairAppImgList(repairAppImg);
        ExcelUtil<RepairAppImg> util = new ExcelUtil<RepairAppImg>(RepairAppImg.class);
        return util.exportExcel(list, "申请单图片数据");
    }

    /**
     * 获取申请单图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:query')")
    @GetMapping(value = "/{appimgId}")
    public AjaxResult getInfo(@PathVariable("appimgId") Long appimgId) {
        return AjaxResult.success(repairAppImgService.selectRepairAppImgById(appimgId));
    }

    /**
     * 申请单图片【单个】新增
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:add')")
    @Log(title = "申请单图片", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(RepairAppImg repairAppImg, RepairRepImg repImg, MultipartFile imgFile) {
        String uploadPath = null;
        if (imgFile == null) return AjaxResult.error();
        try {
            uploadPath = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (uploadPath == null || uploadPath.length() == 0) return AjaxResult.error();

        String filePath = uploadPath.substring(8, 27); //文件路径
        String fileName = uploadPath.substring(27); //文件全名称

        repairAppImg.setAppimgPath(filePath);
        repairAppImg.setAppimgName(fileName);

        /** 维修单图片信息保存*/
        repImg.setRepimgPath(filePath);
        repImg.setRepimgName(fileName);
        repairRepImgService.insertRepairRepImg(repImg);

        return toAjax(repairAppImgService.insertRepairAppImg(repairAppImg));
    }

    /**
     * 修改申请单图片
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:edit')")
    @Log(title = "申请单图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairAppImg repairAppImg) {
        return toAjax(repairAppImgService.updateRepairAppImg(repairAppImg));
    }

    /**
     * 删除申请单图片
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:remove')")
    @Log(title = "申请单图片", businessType = BusinessType.DELETE)
    @DeleteMapping("/{appimgIds}")
    public AjaxResult remove(@PathVariable Long[] appimgIds) {
        return toAjax(repairAppImgService.deleteRepairAppImgByIds(appimgIds));
    }

}
