package com.gxuwz.wyrepair.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gxuwz.wyrepair.mapper.RepairWorkerMapper;
import com.gxuwz.wyrepair.domain.RepairWorker;
import com.gxuwz.wyrepair.service.IRepairWorkerService;

/**
 * 维修工Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-09-26
 */
@Service
public class RepairWorkerServiceImpl implements IRepairWorkerService 
{
    @Autowired
    private RepairWorkerMapper repairWorkerMapper;

    /**
     * 查询维修工
     * 
     * @param repairWorkerId 维修工主键
     * @return 维修工
     */
    @Override
    public RepairWorker selectRepairWorkerByRepairWorkerId(Long repairWorkerId)
    {
        return repairWorkerMapper.selectRepairWorkerByRepairWorkerId(repairWorkerId);
    }

    /**
     * 查询维修工列表
     * 
     * @param repairWorker 维修工
     * @return 维修工
     */
    @Override
    public List<RepairWorker> selectRepairWorkerList(RepairWorker repairWorker)
    {
        return repairWorkerMapper.selectRepairWorkerList(repairWorker);
    }

    /**
     * 新增维修工
     * 
     * @param repairWorker 维修工
     * @return 结果
     */
    @Override
    public int insertRepairWorker(RepairWorker repairWorker)
    {
        return repairWorkerMapper.insertRepairWorker(repairWorker);
    }

    /**
     * 修改维修工
     * 
     * @param repairWorker 维修工
     * @return 结果
     */
    @Override
    public int updateRepairWorker(RepairWorker repairWorker)
    {
        return repairWorkerMapper.updateRepairWorker(repairWorker);
    }

    /**
     * 批量删除维修工
     * 
     * @param repairWorkerIds 需要删除的维修工主键
     * @return 结果
     */
    @Override
    public int deleteRepairWorkerByRepairWorkerIds(Long[] repairWorkerIds)
    {
        return repairWorkerMapper.deleteRepairWorkerByRepairWorkerIds(repairWorkerIds);
    }

    /**
     * 删除维修工信息
     * 
     * @param repairWorkerId 维修工主键
     * @return 结果
     */
    @Override
    public int deleteRepairWorkerByRepairWorkerId(Long repairWorkerId)
    {
        return repairWorkerMapper.deleteRepairWorkerByRepairWorkerId(repairWorkerId);
    }
}
