package com.gxuwz.wyrepair.mapper;


import com.gxuwz.wyrepair.domain.RepairRepImg;

import java.util.ArrayList;
import java.util.List;

/**
 * 报修单图片Mapper接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface RepairRepImgMapper 
{
    /**
     * 查询报修单图片
     * 
     * @param repimgId 报修单图片ID
     * @return 报修单图片
     */
    public RepairRepImg selectRepairRepImgById(Long repimgId);

    /**
     * 查询报修单图片列表
     * 
     * @param repairRepImg 报修单图片
     * @return 报修单图片集合
     */
    public List<RepairRepImg> selectRepairRepImgList(RepairRepImg repairRepImg);

    /**
     * 新增报修单图片
     * 
     * @param repairRepImg 报修单图片
     * @return 结果
     */
    public int insertRepairRepImg(RepairRepImg repairRepImg);

    /**
     * 修改报修单图片
     * 
     * @param repairRepImg 报修单图片
     * @return 结果
     */
    public int updateRepairRepImg(RepairRepImg repairRepImg);

    /**
     * 删除报修单图片
     * 
     * @param repimgId 报修单图片ID
     * @return 结果
     */
    public int deleteRepairRepImgById(Long repimgId);

    /**
     * 批量删除报修单图片
     * 
     * @param repimgIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepairRepImgByIds(Long[] repimgIds);

    /**
     * 批量保存报修图片
     * @param piclist
     * @return
     */
    int insertRepairRepImgBatch(ArrayList<RepairRepImg> piclist);
}
