package com.gxuwz.wyrepair.mapper;

import com.gxuwz.wyrepair.domain.RepairAppImg;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请单图片Mapper接口
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-23
 */
public interface RepairAppImgMapper 
{
    /**
     * 查询申请单图片
     * 
     * @param appimgId 申请单图片ID
     * @return 申请单图片
     */
    public RepairAppImg selectRepairAppImgById(Long appimgId);

    /**
     * 查询申请单图片
     *
     * @param appId 申请单图片ID
     * @return 申请单图片
     */
    public List<RepairAppImg> selectRepairAppById(Long appId);

    /**
     * 查询申请单图片
     *
     * @param appId 申请单图片ID
     * @return 申请单图片
     */
    public RepairAppImg selectRepairAppId(Long appId);

    /**
     * 查询申请单图片列表
     * 
     * @param repairAppImg 申请单图片
     * @return 申请单图片集合
     */
    public List<RepairAppImg> selectRepairAppImgList(RepairAppImg repairAppImg);

    /**
     * 新增申请单图片
     * 
     * @param repairAppImg 申请单图片
     * @return 结果
     */
    public int insertRepairAppImg(RepairAppImg repairAppImg);

    /**
     * 修改申请单图片
     * 
     * @param repairAppImg 申请单图片
     * @return 结果
     */
    public int updateRepairAppImg(RepairAppImg repairAppImg);

    /**
     * 删除申请单图片
     * 
     * @param appimgId 申请单图片ID
     * @return 结果
     */
    public int deleteRepairAppImgById(Long appimgId);

    /**
     * 批量删除申请单图片
     * 
     * @param appimgIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepairAppImgByIds(Long[] appimgIds);

    /**
     * 批量插入图片
     * @param applyImgList 图片对象集合
     * @return
     */
    int insertRepairAppImgBatch(@Param("imgList") ArrayList<RepairAppImg> applyImgList);
}
