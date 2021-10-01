package com.gxuwz.wyrepair.service;

import com.gxuwz.wyrepair.domain.RepairRepTb;

import java.util.List;

/**
 * 报修单Service接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface IRepairRepTbService 
{
    /**
     * 查询报修单
     * 
     * @param repairId 报修单ID
     * @return 报修单
     */
    public RepairRepTb selectRepairRepTbById(Long repairId);

    /**
     * 查询报修单列表（维修人员查看）
     * 
     * @param repairRepTb 报修单
     * @param repairUserid 报修单所属维修人员
     * @return 报修单集合
     */
    public List<RepairRepTb> selectRepairPersonRepTbList(RepairRepTb repairRepTb,Long repairUserid);


    /**
     * 查询除【维修人员】外的其他人员的报修单列表
     *
     * @param repairRepTb 报修单
     * @param applyId 报修单所属维修人员
     * @return 报修单集合
     */
    public List<RepairRepTb> selectRepairTbListOfOthers(RepairRepTb repairRepTb,Long applyId);

    /**
     * 新增报修单
     * 
     * @param repairRepTb 报修单
     * @return 结果
     */
    public int insertRepairRepTb(RepairRepTb repairRepTb);

    /**
     * 修改报修单
     * 
     * @param repairRepTb 报修单
     * @return 结果
     */
    public int updateRepairRepTb(RepairRepTb repairRepTb);

    /**
     * 批量删除报修单
     * 
     * @param repairIds 需要删除的报修单ID
     * @return 结果
     */
    public int deleteRepairRepTbByIds(Long[] repairIds);

    /**
     * 删除报修单信息
     * 
     * @param repairId 报修单ID
     * @return 结果
     */
    public int deleteRepairRepTbById(Long repairId);
}
