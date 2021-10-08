package com.gxuwz.wyrepair.service;

import com.gxuwz.wyrepair.domain.RepairReptype;

import java.util.List;

/**
 * 维修类型Service接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface IRepairReptypeService 
{
    /**
     * 查询维修类型
     * 
     * @param repairTypeId 维修类型ID
     * @return 维修类型
     */
    public RepairReptype selectRepairReptypeById(Long repairTypeId);

    /**
     * 查询维修类型列表
     * 
     * @param repairReptype 维修类型
     * @return 维修类型集合
     */
    public List<RepairReptype> selectRepairReptypeList(RepairReptype repairReptype);

    /**
     * 新增维修类型
     * 
     * @param repairReptype 维修类型
     * @return 结果
     */
    public int insertRepairReptype(RepairReptype repairReptype);

    /**
     * 修改维修类型
     * 
     * @param repairReptype 维修类型
     * @return 结果
     */
    public int updateRepairReptype(RepairReptype repairReptype);

    /**
     * 批量删除维修类型
     * 
     * @param repairTypeIds 需要删除的维修类型ID
     * @return 结果
     */
    public int deleteRepairReptypeByIds(Long[] repairTypeIds);

    /**
     * 删除维修类型信息
     * 
     * @param repairTypeId 维修类型ID
     * @return 结果
     */
    public int deleteRepairReptypeById(Long repairTypeId);
    /**
     * 查询维修类型列表
     *
     * @param repairReptype 维修类型
     * @return 维修类型集合
     */
    List<RepairReptype> selectRepairRepTypeList(RepairReptype repairReptype);
}
