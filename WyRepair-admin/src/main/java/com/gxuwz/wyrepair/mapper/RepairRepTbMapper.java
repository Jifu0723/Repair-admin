package com.gxuwz.wyrepair.mapper;

import com.gxuwz.wyrepair.domain.RepairRepTb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报修单Mapper接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface RepairRepTbMapper 
{
    /**
     * 查询报修单
     * 
     * @param repairId 报修单ID
     * @return 报修单
     */
    public RepairRepTb selectRepairRepTbById(Long repairId);

    /**
     * 查询报修单列表
     * 
     * @param repairRepTb 报修单
     * @param repairUserid 报修单所属维修人员
     * @return 报修单集合
     */
    public List<RepairRepTb> selectRepairRepTbList(@Param("repTb") RepairRepTb repairRepTb,@Param("repairUserid") Long repairUserid);

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
     * 删除报修单
     * 
     * @param repairId 报修单ID
     * @return 结果
     */
    public int deleteRepairRepTbById(Long repairId);

    /**
     * 批量删除报修单
     * 
     * @param repairIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepairRepTbByIds(Long[] repairIds);

    /**
     * 查询除【维修人员】外的其他人员的报修单列表
     *
     * @param repairRepTb 报修单
     * @param applyUserId 报修单所属维修人员
     * @return 报修单集合
     */
    List<RepairRepTb> selectRepairTbListOfOthers(@Param("repTb")RepairRepTb repairRepTb,@Param("applyUserId") Long applyUserId);
}
