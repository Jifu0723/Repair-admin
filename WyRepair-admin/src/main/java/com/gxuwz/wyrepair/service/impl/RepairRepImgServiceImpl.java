package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.domain.RepairRepImg;
import com.gxuwz.wyrepair.mapper.RepairRepImgMapper;
import com.gxuwz.wyrepair.service.IRepairRepImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 报修单图片Service业务层处理
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Service
public class RepairRepImgServiceImpl implements IRepairRepImgService 
{
    @Autowired
    private RepairRepImgMapper repairRepImgMapper;

    /**
     * 查询报修单图片
     * 
     * @param repimgId 报修单图片ID
     * @return 报修单图片
     */
    @Override
    public RepairRepImg selectRepairRepImgById(Long repimgId)
    {
        return repairRepImgMapper.selectRepairRepImgById(repimgId);
    }

    /**
     * 查询报修单图片列表
     * 
     * @param repairRepImg 报修单图片
     * @return 报修单图片
     */
    @Override
    public List<RepairRepImg> selectRepairRepImgList(RepairRepImg repairRepImg)
    {
        return repairRepImgMapper.selectRepairRepImgList(repairRepImg);
    }

    /**
     * 新增报修单图片
     * 
     * @param repairRepImg 报修单图片
     * @return 结果
     */
    @Override
    public int insertRepairRepImg(RepairRepImg repairRepImg)
    {
        return repairRepImgMapper.insertRepairRepImg(repairRepImg);
    }

    /**
     * 修改报修单图片
     * 
     * @param repairRepImg 报修单图片
     * @return 结果
     */
    @Override
    public int updateRepairRepImg(RepairRepImg repairRepImg)
    {
        return repairRepImgMapper.updateRepairRepImg(repairRepImg);
    }

    /**
     * 批量删除报修单图片
     * 
     * @param repimgIds 需要删除的报修单图片ID
     * @return 结果
     */
    @Override
    public int deleteRepairRepImgByIds(Long[] repimgIds)
    {
        return repairRepImgMapper.deleteRepairRepImgByIds(repimgIds);
    }

    /**
     * 删除报修单图片信息
     * 
     * @param repimgId 报修单图片ID
     * @return 结果
     */
    @Override
    public int deleteRepairRepImgById(Long repimgId)
    {
        return repairRepImgMapper.deleteRepairRepImgById(repimgId);
    }

    @Override
    public int insertRepairRepImgBatch(ArrayList<RepairRepImg> piclist) {
        return repairRepImgMapper.insertRepairRepImgBatch(piclist);
    }
}
