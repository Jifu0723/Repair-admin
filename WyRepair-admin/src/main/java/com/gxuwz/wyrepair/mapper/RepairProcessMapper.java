package com.gxuwz.wyrepair.mapper;

import com.gxuwz.wyrepair.domain.RepairProcess;

import java.util.List;

/**
 * 维修过程描述Mapper接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface RepairProcessMapper 
{
    /**
     * 查询维修过程描述
     * 
     * @param processId 维修过程描述ID
     * @return 维修过程描述
     */
    public RepairProcess selectRepairProcessById(Long processId);

    /**
     * 查询维修过程描述列表
     * 
     * @param repairProcess 维修过程描述
     * @return 维修过程描述集合
     */
    public List<RepairProcess> selectRepairProcessList(RepairProcess repairProcess);

    /**
     * 新增维修过程描述
     * 
     * @param repairProcess 维修过程描述
     * @return 结果
     */
    public int insertRepairProcess(RepairProcess repairProcess);

    /**
     * 修改维修过程描述
     * 
     * @param repairProcess 维修过程描述
     * @return 结果
     */
    public int updateRepairProcess(RepairProcess repairProcess);

    /**
     * 删除维修过程描述
     * 
     * @param processId 维修过程描述ID
     * @return 结果
     */
    public int deleteRepairProcessById(Long processId);

    /**
     * 批量删除维修过程描述
     * 
     * @param processIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepairProcessByIds(Long[] processIds);
}
