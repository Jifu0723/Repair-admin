package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.domain.RepairRepcomment;
import com.gxuwz.wyrepair.mapper.RepairRepcommentMapper;
import com.gxuwz.wyrepair.service.IRepairRepcommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报修单评论Service业务层处理
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Service
public class RepairRepcommentServiceImpl implements IRepairRepcommentService 
{
    @Autowired
    private RepairRepcommentMapper repairRepcommentMapper;

    /**
     * 查询报修单评论
     * 
     * @param repcommentId 报修单评论ID
     * @return 报修单评论
     */
    @Override
    public RepairRepcomment selectRepairRepcommentById(Long repcommentId)
    {
        return repairRepcommentMapper.selectRepairRepcommentById(repcommentId);
    }

    /**
     * 查询报修单评论列表
     * 
     * @param repairRepcomment 报修单评论
     * @return 报修单评论
     */
    @Override
    public List<RepairRepcomment> selectRepairRepcommentList(RepairRepcomment repairRepcomment)
    {
        return repairRepcommentMapper.selectRepairRepcommentList(repairRepcomment);
    }

    /**
     * 新增报修单评论
     * 
     * @param repairRepcomment 报修单评论
     * @return 结果
     */
    @Override
    public int insertRepairRepcomment(RepairRepcomment repairRepcomment)
    {
        return repairRepcommentMapper.insertRepairRepcomment(repairRepcomment);
    }

    /**
     * 修改报修单评论
     * 
     * @param repairRepcomment 报修单评论
     * @return 结果
     */
    @Override
    public int updateRepairRepcomment(RepairRepcomment repairRepcomment)
    {
        return repairRepcommentMapper.updateRepairRepcomment(repairRepcomment);
    }

    /**
     * 批量删除报修单评论
     * 
     * @param repcommentIds 需要删除的报修单评论ID
     * @return 结果
     */
    @Override
    public int deleteRepairRepcommentByIds(Long[] repcommentIds)
    {
        return repairRepcommentMapper.deleteRepairRepcommentByIds(repcommentIds);
    }

    /**
     * 删除报修单评论信息
     * 
     * @param repcommentId 报修单评论ID
     * @return 结果
     */
    @Override
    public int deleteRepairRepcommentById(Long repcommentId)
    {
        return repairRepcommentMapper.deleteRepairRepcommentById(repcommentId);
    }
}
