package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.domain.RepairAppImg;
import com.gxuwz.wyrepair.mapper.RepairAppImgMapper;
import com.gxuwz.wyrepair.service.IRepairAppImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请单图片Service业务层处理
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-23
 */
@Service
public class RepairAppImgServiceImpl implements IRepairAppImgService 
{
    @Autowired
    private RepairAppImgMapper repairAppImgMapper;

    /**
     * 查询申请单图片
     * 
     * @param appimgId 申请单图片ID
     * @return 申请单图片
     */
    @Override
    public RepairAppImg selectRepairAppImgById(Long appimgId)
    {
        return repairAppImgMapper.selectRepairAppImgById(appimgId);
    }

    /**
     * 查询申请单图片列表
     * 
     * @param repairAppImg 申请单图片
     * @return 申请单图片
     */
    @Override
    public List<RepairAppImg> selectRepairAppImgList(RepairAppImg repairAppImg)
    {
        return repairAppImgMapper.selectRepairAppImgList(repairAppImg);
    }

    /**
     * 新增申请单图片
     * 
     * @param repairAppImg 申请单图片
     * @return 结果
     */
    @Override
    public int insertRepairAppImg(RepairAppImg repairAppImg)
    {
        return repairAppImgMapper.insertRepairAppImg(repairAppImg);
    }

    /**
     * 修改申请单图片
     * 
     * @param repairAppImg 申请单图片
     * @return 结果
     */
    @Override
    public int updateRepairAppImg(RepairAppImg repairAppImg)
    {
        return repairAppImgMapper.updateRepairAppImg(repairAppImg);
    }

    /**
     * 批量删除申请单图片
     * 
     * @param appimgIds 需要删除的申请单图片ID
     * @return 结果
     */
    @Override
    public int deleteRepairAppImgByIds(Long[] appimgIds)
    {
        return repairAppImgMapper.deleteRepairAppImgByIds(appimgIds);
    }

    /**
     * 删除申请单图片信息
     * 
     * @param appimgId 申请单图片ID
     * @return 结果
     */
    @Override
    public int deleteRepairAppImgById(Long appimgId)
    {
        return repairAppImgMapper.deleteRepairAppImgById(appimgId);
    }

    /**
     * 批量插入图片
     * @param applyImgList 图片对象集合
     * @return
     */
    @Override
    public int insertRepairAppImgBatch(ArrayList<RepairAppImg> applyImgList) {
        return repairAppImgMapper.insertRepairAppImgBatch(applyImgList);
    }
}
