package com.gxuwz.wyrepair.mapper;

import com.gxuwz.wyrepair.domain.RepairApply;

import java.util.List;

/**
 * 申请单Mapper接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface RepairApplyMapper 
{
    /**
     * 查询申请单
     * 
     * @param applyId 申请单ID
     * @return 申请单
     */
    public RepairApply selectRepairApplyById(Long applyId);

    /**
     * 查询申请单列表
     * 
     * @param repairApply 申请单
     * @return 申请单集合
     */
    public List<RepairApply> selectRepairApplyList(RepairApply repairApply);

    /**
     * 新增申请单
     * 
     * @param repairApply 申请单
     * @return 结果
     */
    public int insertRepairApply(RepairApply repairApply);

    /**
     * 修改申请单
     * 
     * @param repairApply 申请单
     * @return 结果
     */
    public int updateRepairApply(RepairApply repairApply);

    /**
     * 删除申请单
     * 
     * @param applyId 申请单ID
     * @return 结果
     */
    public int deleteRepairApplyById(Long applyId);

    /**
     * 批量删除申请单
     * 
     * @param applyIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepairApplyByIds(Long[] applyIds);
}
