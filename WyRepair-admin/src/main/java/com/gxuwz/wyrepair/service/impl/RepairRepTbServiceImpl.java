package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.domain.RepairRepTb;
import com.gxuwz.wyrepair.mapper.RepairRepTbMapper;
import com.gxuwz.wyrepair.service.IRepairRepTbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报修单Service业务层处理
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Service
public class RepairRepTbServiceImpl implements IRepairRepTbService 
{
    @Autowired
    private RepairRepTbMapper repairRepTbMapper;

    /**
     * 查询报修单
     * 
     * @param repairId 报修单ID
     * @return 报修单
     */
    @Override
    public RepairRepTb selectRepairRepTbById(Long repairId)
    {
        return repairRepTbMapper.selectRepairRepTbById(repairId);
    }

    /**
     * 查询报修单列表
     * 
     * @param repairRepTb 报修单
     * @param repairUserid 报修单所属维修人员
     * @return 报修单
     */
    @Override
    public List<RepairRepTb> selectRepairRepTbList(RepairRepTb repairRepTb,Long repairUserid)
    {
        return repairRepTbMapper.selectRepairRepTbList(repairRepTb,repairUserid);
    }

    /**
     * 查询除【维修人员】外的其他人员的报修单列表
     *
     * @param repairRepTb 报修单
     * @param applyId 报修单所属维修人员
     * @return 报修单集合
     */
    @Override
    public List<RepairRepTb> selectRepairTbListOfOthers(RepairRepTb repairRepTb, Long applyId) {
        return repairRepTbMapper.selectRepairTbListOfOthers(repairRepTb,applyId);
    }

    /**
     * 新增报修单
     * 
     * @param repairRepTb 报修单
     * @return 结果
     */
    @Override
    public int insertRepairRepTb(RepairRepTb repairRepTb)
    {
        return repairRepTbMapper.insertRepairRepTb(repairRepTb);
    }

    /**
     * 修改报修单
     * 
     * @param repairRepTb 报修单
     * @return 结果
     */
    @Override
    public int updateRepairRepTb(RepairRepTb repairRepTb)
    {
        return repairRepTbMapper.updateRepairRepTb(repairRepTb);
    }

    /**
     * 批量删除报修单
     * 
     * @param repairIds 需要删除的报修单ID
     * @return 结果
     */
    @Override
    public int deleteRepairRepTbByIds(Long[] repairIds)
    {
        return repairRepTbMapper.deleteRepairRepTbByIds(repairIds);
    }

    /**
     * 删除报修单信息
     * 
     * @param repairId 报修单ID
     * @return 结果
     */
    @Override
    public int deleteRepairRepTbById(Long repairId)
    {
        return repairRepTbMapper.deleteRepairRepTbById(repairId);
    }
}
