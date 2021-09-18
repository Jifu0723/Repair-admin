package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.common.utils.DateUtils;
import com.gxuwz.wyrepair.domain.RepairProcess;
import com.gxuwz.wyrepair.mapper.RepairProcessMapper;
import com.gxuwz.wyrepair.service.IRepairProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维修过程描述Service业务层处理
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Service
public class RepairProcessServiceImpl implements IRepairProcessService 
{
    @Autowired
    private RepairProcessMapper repairProcessMapper;

    /**
     * 查询维修过程描述
     * 
     * @param processId 维修过程描述ID
     * @return 维修过程描述
     */
    @Override
    public RepairProcess selectRepairProcessById(Long processId)
    {
        return repairProcessMapper.selectRepairProcessById(processId);
    }

    /**
     * 查询维修过程描述列表
     * 
     * @param repairProcess 维修过程描述
     * @return 维修过程描述
     */
    @Override
    public List<RepairProcess> selectRepairProcessList(RepairProcess repairProcess)
    {
        return repairProcessMapper.selectRepairProcessList(repairProcess);
    }

    /**
     * 新增维修过程描述
     * 
     * @param repairProcess 维修过程描述
     * @return 结果
     */
    @Override
    public int insertRepairProcess(RepairProcess repairProcess)
    {
        repairProcess.setCreateTime(DateUtils.getNowDate());
        return repairProcessMapper.insertRepairProcess(repairProcess);
    }

    /**
     * 修改维修过程描述
     * 
     * @param repairProcess 维修过程描述
     * @return 结果
     */
    @Override
    public int updateRepairProcess(RepairProcess repairProcess)
    {
        return repairProcessMapper.updateRepairProcess(repairProcess);
    }

    /**
     * 批量删除维修过程描述
     * 
     * @param processIds 需要删除的维修过程描述ID
     * @return 结果
     */
    @Override
    public int deleteRepairProcessByIds(Long[] processIds)
    {
        return repairProcessMapper.deleteRepairProcessByIds(processIds);
    }

    /**
     * 删除维修过程描述信息
     * 
     * @param processId 维修过程描述ID
     * @return 结果
     */
    @Override
    public int deleteRepairProcessById(Long processId)
    {
        return repairProcessMapper.deleteRepairProcessById(processId);
    }
}
