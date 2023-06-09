package com.gxuwz.wyrepair.mapper;

import com.gxuwz.wyrepair.domain.RepairRepTb;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 报修单Mapper接口
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
public interface RepairRepTbMapper
{
    /**
     * 查询报修单
     *
     * @param repairId 报修单ID
     * @return 报修单
     */
    public RepairRepTb selectRepairRepTbById(Long repairId);

    /**
     * 查询报修单列表
     *
     * @param repairRepTb 报修单
     * @return 报修单集合
     */
    public List<RepairRepTb> selectRepairRepTbList(RepairRepTb repairRepTb);

    /**
     * 查询报修单列表（维修人员查看）
     *
     * @param repairRepTb 报修单
     * @param repairUserid 报修单所属维修人员
     * @return 报修单集合
     */
    public List<RepairRepTb> selectRepairPersonRepTbList(@Param("repTb") RepairRepTb repairRepTb,@Param("repairUserid") Long repairUserid);

    /**
     * 新增报修单
     *
     * @param repairRepTb 报修单
     * @return 结果
     */
    public int insertRepairRepTb(RepairRepTb repairRepTb);

    /**
     * 修改报修单
     *
     * @param repairRepTb 报修单
     * @return 结果
     */
    public int updateRepairRepTb(RepairRepTb repairRepTb);

    /**
     * 删除报修单
     *
     * @param repairId 报修单ID
     * @return 结果
     */
    public int deleteRepairRepTbById(Long repairId);

    /**
     * 批量删除报修单
     *
     * @param repairIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepairRepTbByIds(Long[] repairIds);

    /**
     * 查询完成的维修单列表（工资结算移动端）
     *
     * @param params 维修单列表
     * @return 维修单列表集合
     */
    List<Map<String, Object>>  selectRepairTotalList( Map<String, Object> params);

    /**
     * 查询完成的维修单列表（工资结算后台）
     *
     * @param params 维修单列表
     * @return 维修单列表集合
     */
    Map MonthMoneyTotal(Map<String, Object> params);

    /**
     * 查询完成的维修单列表
     *
     * @param params 维修单列表
     * @return 维修单列表集合
     */
    List<Map<String, Object>> RepairTotalList(Map<String, Object> params);

    /**
     * 获取用户信息（姓名、手机号码、所在二级学院等）
     */
    Map UserInformation(Map<String, Object> params);

    /**
     * 查询月酬金表数据
     *
     * @param params 维修单列表
     * @return 维修单列表集合
     */
    List<Map<String, Object>> selectRepairWorkTotal(Map<String, Object> params);

    /**
     * 查询除【维修人员】外的其他人员的报修单列表
     *
     * @param repairRepTb 报修单
     * @param applyUserId 报修单所属维修人员
     * @return 报修单集合
     */
    List<RepairRepTb> selectRepairTbListOfOthers(@Param("repTb")RepairRepTb repairRepTb,@Param("applyUserId") Long applyUserId);
    /**
     * 后勤部门管理员按二级学院id、报修时间、报修类型、维修人员姓名、设备维修后状态、报修人姓名统计报修信息输出日、周、月
     * 报表(查询)
     */
    Map CountCollegeByRepairOrder(Map<String, Object> params);
    /**
     * 后勤部门管理员按报修时间、报修类型、维修人员姓名、设备维修后状态、报修人姓名统计报修信息输出日、周、月
     * 列表(查询)
     */
    List<RepairRepTb> queryRepairOrderList(Map<String, Object> params);
    /**
     * 后勤部门管理员按报修时间、报修类型、维修人员姓名、设备维修后状态、报修人姓名统计报修信息输出日、周、月
     * 报表(查询)
     */
    Map countrepairTimeByrepairType(Map<String, Object> params);

    /**
     * 维修专员按报修时间、报修类型、维修人员姓名、设备维修后状态、报修人姓名统计报修信息输出日、周、月
     * 列表(查询)
     */
    List<RepairRepTb> queryRepairAdminOrderList(Map<String, Object> params);

    /**
     * 维修专员按报修时间、报修类型、维修人员姓名、设备维修后状态、报修人姓名统计报修信息输出日、周、月
     * 报表(查询)
     */
    Map countrepairAdminTimeByrepairType(Map<String, Object> params);

    /**
     * 查询报修单和报修类型的名称（连表查询）
     *
     * @param repairId 报修单ID
     * @return 报修单
     */
    RepairRepTb selectRepairId(Long repairId);
}
