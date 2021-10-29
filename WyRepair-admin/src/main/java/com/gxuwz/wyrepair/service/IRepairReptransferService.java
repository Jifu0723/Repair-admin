package com.gxuwz.wyrepair.service;

import com.gxuwz.wyrepair.domain.RepairReptransfer;

import java.util.List;
import java.util.Map;

/**
 * 转单记录Service接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface IRepairReptransferService 
{
    /**
     * 查询转单记录
     * 
     * @param reptransferId 转单记录ID
     * @return 转单记录
     */
    public  Map<String,Object> selectRepairReptransferById(Long reptransferId);

    /**
     * 查询转单记录列表
     * 
     * @param repairReptransfer 转单记录
     * @return 转单记录集合
     */
    public List<RepairReptransfer> selectRepairReptransferList(RepairReptransfer repairReptransfer);

    /**
     * 查询转单记录列表(维修单转去人的信息包括部门、姓名等)
     *
     * @param params 转单记录
     * @return 转单记录集合
     */
    List<Map<String,Object>>  selectReptransferList(Map<String, Object> params);

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
     * 批量删除转单记录
     * 
     * @param reptransferIds 需要删除的转单记录ID
     * @return 结果
     */
    public int deleteRepairReptransferByIds(Long[] reptransferIds);

    /**
     * 删除转单记录信息
     * 
     * @param reptransferId 转单记录ID
     * @return 结果
     */
    public int deleteRepairReptransferById(Long reptransferId);
}
