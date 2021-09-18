package com.gxuwz.wyrepair.mapper;

import com.gxuwz.wyrepair.domain.RepairRepcomment;

import java.util.List;

/**
 * 报修单评论Mapper接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface RepairRepcommentMapper 
{
    /**
     * 查询报修单评论
     * 
     * @param repcommentId 报修单评论ID
     * @return 报修单评论
     */
    public RepairRepcomment selectRepairRepcommentById(Long repcommentId);

    /**
     * 查询报修单评论列表
     * 
     * @param repairRepcomment 报修单评论
     * @return 报修单评论集合
     */
    public List<RepairRepcomment> selectRepairRepcommentList(RepairRepcomment repairRepcomment);

    /**
     * 新增报修单评论
     * 
     * @param repairRepcomment 报修单评论
     * @return 结果
     */
    public int insertRepairRepcomment(RepairRepcomment repairRepcomment);

    /**
     * 修改报修单评论
     * 
     * @param repairRepcomment 报修单评论
     * @return 结果
     */
    public int updateRepairRepcomment(RepairRepcomment repairRepcomment);

    /**
     * 删除报修单评论
     * 
     * @param repcommentId 报修单评论ID
     * @return 结果
     */
    public int deleteRepairRepcommentById(Long repcommentId);

    /**
     * 批量删除报修单评论
     * 
     * @param repcommentIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepairRepcommentByIds(Long[] repcommentIds);
}
