package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.domain.RepairExpectType;
import com.gxuwz.wyrepair.mapper.RepairExpectTypeMapper;
import com.gxuwz.wyrepair.service.IRepairExpectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 期望维修类型Service业务层处理
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Service
public class RepairExpectTypeServiceImpl implements IRepairExpectTypeService 
{
    @Autowired
    private RepairExpectTypeMapper repairExpectTypeMapper;

    /**
     * 查询期望维修类型
     * 
     * @param repexpectTypeId 期望维修类型ID
     * @return 期望维修类型
     */
    @Override
    public RepairExpectType selectRepairExpectTypeById(Long repexpectTypeId)
    {
        return repairExpectTypeMapper.selectRepairExpectTypeById(repexpectTypeId);
    }

    /**
     * 查询期望维修类型列表
     * 
     * @param repairExpectType 期望维修类型
     * @return 期望维修类型
     */
    @Override
    public List<RepairExpectType> selectRepairExpectTypeList(RepairExpectType repairExpectType)
    {
        return repairExpectTypeMapper.selectRepairExpectTypeList(repairExpectType);
    }

    /**
     * 新增期望维修类型
     * 
     * @param repairExpectType 期望维修类型
     * @return 结果
     */
    @Override
    public int insertRepairExpectType(RepairExpectType repairExpectType)
    {
        return repairExpectTypeMapper.insertRepairExpectType(repairExpectType);
    }

    /**
     * 修改期望维修类型
     * 
     * @param repairExpectType 期望维修类型
     * @return 结果
     */
    @Override
    public int updateRepairExpectType(RepairExpectType repairExpectType)
    {
        return repairExpectTypeMapper.updateRepairExpectType(repairExpectType);
    }

    /**
     * 批量删除期望维修类型
     * 
     * @param repexpectTypeIds 需要删除的期望维修类型ID
     * @return 结果
     */
    @Override
    public int deleteRepairExpectTypeByIds(Long[] repexpectTypeIds)
    {
        return repairExpectTypeMapper.deleteRepairExpectTypeByIds(repexpectTypeIds);
    }

    /**
     * 删除期望维修类型信息
     * 
     * @param repexpectTypeId 期望维修类型ID
     * @return 结果
     */
    @Override
    public int deleteRepairExpectTypeById(Long repexpectTypeId)
    {
        return repairExpectTypeMapper.deleteRepairExpectTypeById(repexpectTypeId);
    }
}
