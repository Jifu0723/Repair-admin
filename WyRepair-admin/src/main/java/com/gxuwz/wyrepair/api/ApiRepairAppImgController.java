package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.config.RuoYiConfig;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.StringUtils;
import com.gxuwz.wyrepair.common.utils.file.FileUploadUtils;
import com.gxuwz.wyrepair.common.utils.poi.ExcelUtil;
import com.gxuwz.wyrepair.domain.RepairAppImg;
import com.gxuwz.wyrepair.service.IRepairAppImgService;
import com.gxuwz.wyrepair.service.IRepairRepImgService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 申请单图片Controller
 *
 * @author LMTeamTop-2018
 * @date 2021-05-23
 */
@Api(tags = "申请单拍照图片模块")
@RestController
@RequestMapping("/api/repair/appimg")
public class ApiRepairAppImgController extends BaseController
{
    @Autowired
    private IRepairAppImgService repairAppImgService;
    @Autowired
    private IRepairRepImgService repairRepImgService;
    /**
     * 查询申请单图片列表
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairAppImg repairAppImg)
    {
        startPage();
        List<RepairAppImg> list = repairAppImgService.selectRepairAppImgList(repairAppImg);
        return getDataTable(list);
    }

    /**
     * 导出申请单图片列表
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:export')")
    @Log(title = "申请单图片", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepairAppImg repairAppImg)
    {
        List<RepairAppImg> list = repairAppImgService.selectRepairAppImgList(repairAppImg);
        ExcelUtil<RepairAppImg> util = new ExcelUtil<RepairAppImg>(RepairAppImg.class);
        return util.exportExcel(list, "申请单图片数据");
    }

    /**
     * 获取申请单图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:query')")
    @GetMapping(value = "/{appimgId}")
    public AjaxResult getInfo(@PathVariable("appimgId") Long appimgId)
    {
        return AjaxResult.success(repairAppImgService.selectRepairAppImgById(appimgId));
    }

    /**
     * 申请单图片【单个】新增
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:add')")
    @Log(title = "申请单图片", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(RepairAppImg repairAppImg, MultipartFile imgFile) throws IOException {
        System.out.println(imgFile);
        String uploadPath = null;
        if (imgFile == null) {
            return AjaxResult.error();
        }
        try {
            uploadPath = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (uploadPath == null || uploadPath.length() == 0) {
            return AjaxResult.error();
        }
        StringBuilder dir = new StringBuilder("/");//文件路径
        String fileName = "";//文件全名称
        List<String> paths = StringUtils.str2List(uploadPath.substring(1), "/",false,true);
        System.out.println(uploadPath);
        paths.remove(0);
        for (int i = 0; i < paths.size(); i++) {
            if(i==paths.size()-1){
                fileName += paths.get(i);
            }else{
                dir.append(paths.get(i));
                dir.append("/");
            }
        }
        repairAppImg.setAppimgPath(dir.toString());
        repairAppImg.setAppimgName(fileName);
        int i = repairAppImgService.insertRepairAppImg(repairAppImg);
        String imgPath = RuoYiConfig.getProfile() + dir + fileName;
        try {
            // 图片压缩
            Thumbnails.of(imgPath)
                    .scale(1f).
                    outputQuality(0.5f)
                    .toFile(imgPath);
        }catch(IOException exception) {
            exception.printStackTrace();
            System.out.println(" 图片压缩报错......");
        }

        return toAjax(i);
    }

    /**
     * 修改申请单图片
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:edit')")
    @Log(title = "申请单图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairAppImg repairAppImg)
    {
        return toAjax(repairAppImgService.updateRepairAppImg(repairAppImg));
    }

    /**
     * 删除申请单图片
     */
    @PreAuthorize("@ss.hasPermi('repair:appimg:remove')")
    @Log(title = "申请单图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{appimgIds}")
    public AjaxResult remove(@PathVariable Long[] appimgIds)
    {
        return toAjax(repairAppImgService.deleteRepairAppImgByIds(appimgIds));
    }

    public static void commpressPicCycle(String desPath , long desFileSize,
                                         double accuracy) throws IOException{
        File imgFile = new File(desPath);
        long fileSize = imgFile.length();
       //判断大小,如果小于500k,不压缩,如果大于等于500k,压缩
        if(fileSize <= desFileSize * 500){
            return;
        }
        //计算宽高
        BufferedImage bim = ImageIO.read(imgFile);
        int imgWidth = bim.getWidth();
        int imgHeight = bim.getHeight();
        int desWidth = new BigDecimal(imgWidth).multiply( new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(imgHeight).multiply( new BigDecimal(accuracy)).intValue();
        Thumbnails.of(desPath).size(desWidth, desHeight).outputQuality(accuracy).toFile(desPath);
         //如果不满足要求,递归直至满足小于1M的要求
        commpressPicCycle(desPath, desFileSize, accuracy);
    }
}
