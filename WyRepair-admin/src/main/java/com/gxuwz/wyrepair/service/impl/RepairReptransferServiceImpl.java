package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.domain.RepairReptransfer;
import com.gxuwz.wyrepair.mapper.RepairReptransferMapper;
import com.gxuwz.wyrepair.service.IRepairReptransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 转单记录Service业务层处理
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Service
public class RepairReptransferServiceImpl implements IRepairReptransferService 
{
    @Autowired
    private RepairReptransferMapper repairReptransferMapper;

    /**
     * 查询转单记录
     * 
     * @param reptransferId 转单记录ID
     * @return 转单记录
     */
    @Override
    public Map<String, Object> selectRepairReptransferById(Long reptransferId)
    {
        return repairReptransferMapper.selectRepairReptransferById(reptransferId);
    }

    /**
     * 查询转单记录列表
     * 
     * @param repairReptransfer 转单记录
     * @return 转单记录
     */
    @Override
    public List<RepairReptransfer> selectRepairReptransferList(RepairReptransfer repairReptransfer)
    {
        return repairReptransferMapper.selectRepairReptransferList(repairReptransfer);
    }

    /**
     * 查询转单记录列表(维修单转去人的信息包括部门、姓名等)
     *
     * @param params 转单记录
     * @return 转单记录集合
     */
    @Override
    public List<Map<String, Object>> selectReptransferList(Map<String, Object> params) {
        return repairReptransferMapper.selectReptransferList(params);
    }

    /**
     * 新增转单记录
     * 
     * @param repairReptransfer 转单记录
     * @return 结果
     */
    @Override
    public int insertRepairReptransfer(RepairReptransfer repairReptransfer)
    {
        return repairReptransferMapper.insertRepairReptransfer(repairReptransfer);
    }

    /**
     * 修改转单记录
     * 
     * @param repairReptransfer 转单记录
     * @return 结果
     */
    @Override
    public int updateRepairReptransfer(RepairReptransfer repairReptransfer)
    {
        return repairReptransferMapper.updateRepairReptransfer(repairReptransfer);
    }

    /**
     * 批量删除转单记录
     * 
     * @param reptransferIds 需要删除的转单记录ID
     * @return 结果
     */
    @Override
    public int deleteRepairReptransferByIds(Long[] reptransferIds)
    {
        return repairReptransferMapper.deleteRepairReptransferByIds(reptransferIds);
    }

    /**
     * 删除转单记录信息
     * 
     * @param reptransferId 转单记录ID
     * @return 结果
     */
    @Override
    public int deleteRepairReptransferById(Long reptransferId)
    {
        return repairReptransferMapper.deleteRepairReptransferById(reptransferId);
    }
}
