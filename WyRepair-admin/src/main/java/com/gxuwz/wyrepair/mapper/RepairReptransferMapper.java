package com.gxuwz.wyrepair.mapper;

import com.gxuwz.wyrepair.domain.RepairReptransfer;

import java.util.List;

/**
 * 转单记录Mapper接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface RepairReptransferMapper 
{
    /**
     * 查询转单记录
     * 
     * @param reptransferId 转单记录ID
     * @return 转单记录
     */
    public RepairReptransfer selectRepairReptransferById(Long reptransferId);

    /**
     * 查询转单记录列表
     * 
     * @param repairReptransfer 转单记录
     * @return 转单记录集合
     */
    public List<RepairReptransfer> selectRepairReptransferList(RepairReptransfer repairReptransfer);

    /**
     * 新增转单记录
     * 
     * @param repairReptransfer 转单记录
     * @return 结果
     */
    public int insertRepairReptransfer(RepairReptransfer repairReptransfer);

    /**
     * 修改转单记录
     * 
     * @param repairReptransfer 转单记录
     * @return 结果
     */
    public int updateRepairReptransfer(RepairReptransfer repairReptransfer);

    /**
     * 删除转单记录
     * 
     * @param reptransferId 转单记录ID
     * @return 结果
     */
    public int deleteRepairReptransferById(Long reptransferId);

    /**
     * 批量删除转单记录
     * 
     * @param reptransferIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepairReptransferByIds(Long[] reptransferIds);
}
