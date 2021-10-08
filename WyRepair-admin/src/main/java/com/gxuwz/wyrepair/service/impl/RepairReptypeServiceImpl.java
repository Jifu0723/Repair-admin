package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.domain.RepairReptype;
import com.gxuwz.wyrepair.mapper.RepairReptypeMapper;
import com.gxuwz.wyrepair.service.IRepairReptypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维修类型Service业务层处理
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Service
public class RepairReptypeServiceImpl implements IRepairReptypeService 
{
    @Autowired
    private RepairReptypeMapper repairReptypeMapper;

    /**
     * 查询维修类型
     * 
     * @param repairTypeId 维修类型ID
     * @return 维修类型
     */
    @Override
    public RepairReptype selectRepairReptypeById(Long repairTypeId)
    {
        return repairReptypeMapper.selectRepairReptypeById(repairTypeId);
    }

    /**
     * 查询维修类型列表
     * 
     * @param repairReptype 维修类型
     * @return 维修类型
     */
    @Override
    public List<RepairReptype> selectRepairReptypeList(RepairReptype repairReptype)
    {
        return repairReptypeMapper.selectRepairReptypeList(repairReptype);
    }

    /**
     * 查询维修类型列表
     *
     * @param repairReptype 维修类型
     * @return 维修类型
     */
    @Override
    public List<RepairReptype> selectRepairRepTypeList(RepairReptype repairReptype)
    {
        return repairReptypeMapper.selectRepairRepTypeList(repairReptype);
    }

    /**
     * 新增维修类型
     * 
     * @param repairReptype 维修类型
     * @return 结果
     */
    @Override
    public int insertRepairReptype(RepairReptype repairReptype)
    {
        return repairReptypeMapper.insertRepairReptype(repairReptype);
    }

    /**
     * 修改维修类型
     * 
     * @param repairReptype 维修类型
     * @return 结果
     */
    @Override
    public int updateRepairReptype(RepairReptype repairReptype)
    {
        return repairReptypeMapper.updateRepairReptype(repairReptype);
    }

    /**
     * 批量删除维修类型
     * 
     * @param repairTypeIds 需要删除的维修类型ID
     * @return 结果
     */
    @Override
    public int deleteRepairReptypeByIds(Long[] repairTypeIds)
    {
        return repairReptypeMapper.deleteRepairReptypeByIds(repairTypeIds);
    }

    /**
     * 删除维修类型信息
     * 
     * @param repairTypeId 维修类型ID
     * @return 结果
     */
    @Override
    public int deleteRepairReptypeById(Long repairTypeId)
    {
        return repairReptypeMapper.deleteRepairReptypeById(repairTypeId);
    }
}
