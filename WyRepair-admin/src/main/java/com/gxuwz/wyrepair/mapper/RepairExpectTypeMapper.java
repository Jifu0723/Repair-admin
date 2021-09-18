package com.gxuwz.wyrepair.mapper;

import com.gxuwz.wyrepair.domain.RepairExpectType;

import java.util.List;

/**
 * 期望维修类型Mapper接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface RepairExpectTypeMapper 
{
    /**
     * 查询期望维修类型
     * 
     * @param repexpectTypeId 期望维修类型ID
     * @return 期望维修类型
     */
    public RepairExpectType selectRepairExpectTypeById(Long repexpectTypeId);

    /**
     * 查询期望维修类型列表
     * 
     * @param repairExpectType 期望维修类型
     * @return 期望维修类型集合
     */
    public List<RepairExpectType> selectRepairExpectTypeList(RepairExpectType repairExpectType);

    /**
     * 新增期望维修类型
     * 
     * @param repairExpectType 期望维修类型
     * @return 结果
     */
    public int insertRepairExpectType(RepairExpectType repairExpectType);

    /**
     * 修改期望维修类型
     * 
     * @param repairExpectType 期望维修类型
     * @return 结果
     */
    public int updateRepairExpectType(RepairExpectType repairExpectType);

    /**
     * 删除期望维修类型
     * 
     * @param repexpectTypeId 期望维修类型ID
     * @return 结果
     */
    public int deleteRepairExpectTypeById(Long repexpectTypeId);

    /**
     * 批量删除期望维修类型
     * 
     * @param repexpectTypeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepairExpectTypeByIds(Long[] repexpectTypeIds);
}
