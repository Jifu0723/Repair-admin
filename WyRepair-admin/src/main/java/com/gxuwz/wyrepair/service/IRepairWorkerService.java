package com.gxuwz.wyrepair.service;

import java.util.List;
import com.gxuwz.wyrepair.domain.RepairWorker;

/**
 * 维修工Service接口
 * 
 * @author ruoyi
 * @date 2021-09-26
 */
public interface IRepairWorkerService 
{
    /**
     * 查询维修工
     * 
     * @param repairWorkerId 维修工主键
     * @return 维修工
     */
    public RepairWorker selectRepairWorkerByRepairWorkerId(Long repairWorkerId);

    /**
     * 查询维修工列表
     * 
     * @param repairWorker 维修工
     * @return 维修工集合
     */
    public List<RepairWorker> selectRepairWorkerList(RepairWorker repairWorker);

    /**
     * 新增维修工
     * 
     * @param repairWorker 维修工
     * @return 结果
     */
    public int insertRepairWorker(RepairWorker repairWorker);

    /**
     * 修改维修工
     * 
     * @param repairWorker 维修工
     * @return 结果
     */
    public int updateRepairWorker(RepairWorker repairWorker);

    /**
     * 批量删除维修工
     * 
     * @param repairWorkerIds 需要删除的维修工主键集合
     * @return 结果
     */
    public int deleteRepairWorkerByRepairWorkerIds(Long[] repairWorkerIds);

    /**
     * 删除维修工信息
     * 
     * @param repairWorkerId 维修工主键
     * @return 结果
     */
    public int deleteRepairWorkerByRepairWorkerId(Long repairWorkerId);
}
