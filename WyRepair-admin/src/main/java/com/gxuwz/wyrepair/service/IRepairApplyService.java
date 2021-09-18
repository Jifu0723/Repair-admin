package com.gxuwz.wyrepair.service;

import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.domain.RepairApply;

import java.util.List;

/**
 * 申请单Service接口
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface IRepairApplyService
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
    public AjaxResult insertRepairApply(RepairApply repairApply,int isOrder);

    /**
     * 修改申请单
     *
     * @param repairApply 申请单
     * @return 结果
     */
    public int updateRepairApply(RepairApply repairApply);

    /**
     * 批量删除申请单
     *
     * @param applyIds 需要删除的申请单ID
     * @return 结果
     */
    public int deleteRepairApplyByIds(Long[] applyIds);

    /**
     * 删除申请单信息
     *
     * @param applyId 申请单ID
     * @return 结果
     */
    public int deleteRepairApplyById(Long applyId);
}
