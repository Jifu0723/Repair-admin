package com.gxuwz.wyrepair.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxuwz.wyrepair.common.annotation.Excel;
import com.gxuwz.wyrepair.common.core.domain.BaseEntity;
import com.gxuwz.wyrepair.common.core.domain.entity.SysDept;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 转单记录对象 repair_reptransfer
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@ApiModel("转单记录实体")
public class RepairReptransfer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long reptransferId;

    /**
     * 所属报修单id
     */
    @ApiModelProperty("所属报修单id")
    @Excel(name = "所属报修单id")
    private Long reptId;

    /**
     * 所属报修单编号
     */
    @ApiModelProperty("所属报修单编号")
    @Excel(name = "所属报修单编号")
    private String reptNo;

    /**
     * 所转to报修单id
     */
    @ApiModelProperty("所转to报修单id")
    @Excel(name = "所转to报修单id")
    private Long reptToId;

    /**
     * 所转to报修单编号
     */
    @ApiModelProperty("所转to报修单编号")
    @Excel(name = "所转to报修单编号")
    private String reptToNo;

    /**
     * 申请单id
     */
    @ApiModelProperty("申请单id")
    @Excel(name = "申请单id")
    private Long applyId;

    /**
     * 转单留言维修情况
     */
    @ApiModelProperty("转单留言维修情况")
    @Excel(name = "转单留言维修情况")
    private String reptransfeMessage;

    /**
     * 发起转单用户id
     */
    @ApiModelProperty("发起转单用户id")
    @Excel(name = "发起转单用户id")
    private Long reptransfeUser;

    /**
     * 所转用户id
     */
    @ApiModelProperty("所转用户id")
    @Excel(name = "所转用户id")
    private Long reptransfeToUser;

    /**
     * 所转部门id
     */
    @ApiModelProperty("所转部门id")
    @Excel(name = "所转部门id")
    private Long reptToDept;

    /**
     * 转单用户类型（0维修专员、1维修人员）
     */
    @ApiModelProperty("转单用户类型（0维修专员、1维修人员）")
    @Excel(name = "转单用户类型", readConverterExp = "0=维修专员、1维修人员")
    private Integer reptransfeUserType;

    /**
     * 上一次转单记录id
     */
    @ApiModelProperty("上一次转单记录id")
    @Excel(name = "上一次转单记录id")
    private Long parentId;

    /**
     * 转单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd ")
    @Excel(name = "转单时间", width = 30, dateFormat = "yyyy-MM-dd ")
    private Date reptransfeTime;

    /**
     * 逻辑删除（0存在、1删除）
     */
    @Excel(name = "逻辑删除", readConverterExp = "0=存在、1删除")
    private Long reptransfeIsDelete;

    /**
     * 所转部门
     */
    private SysDept dept;

    /**
     * 所转用户
     */
    private SysUser user;

    /**
     * 申请单
     */
    private RepairApply apply;

    /**
     * 初始化信息
     *
     * @param oldRepair         原维修单id
     * @param newRepair         当前维修单id
     * @param applyId           申请单id
     * @param oUserId           原用户id
     */
    public void initTransfer(RepairRepTb oldRepair,
                             RepairRepTb newRepair,
                             Long applyId,
                             Long oUserId,
                             int reptransfeUserType) {
        this.reptId = oldRepair.getRepairId();//所属报修单id
        this.reptNo = oldRepair.getRepairNo();//所属报修单编号
        this.reptToId = newRepair.getRepairId();//所转to报修单id(新转的报修单id)
        this.reptToNo = newRepair.getRepairNo();//所转to报修单编号(新转的报修单编号)
        this.applyId = applyId;//申请单id
        this.reptransfeUser = oUserId;//发起转单用户id
        this.reptransfeUserType = reptransfeUserType;//转单用户类型（0维修专员、1维修人员）

    }


    public void setReptransferId(Long reptransferId) {
        this.reptransferId = reptransferId;
    }

    public Long getReptransferId() {
        return reptransferId;
    }

    public void setReptId(Long reptId) {
        this.reptId = reptId;
    }

    public Long getReptId() {
        return reptId;
    }

    public void setReptNo(String reptNo) {
        this.reptNo = reptNo;
    }

    public String getReptNo() {
        return reptNo;
    }

    public void setReptToId(Long reptToId) {
        this.reptToId = reptToId;
    }

    public Long getReptToId() {
        return reptToId;
    }

    public void setReptToNo(String reptToNo) {
        this.reptToNo = reptToNo;
    }

    public String getReptToNo() {
        return reptToNo;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setReptransfeMessage(String reptransfeMessage) {
        this.reptransfeMessage = reptransfeMessage;
    }

    public String getReptransfeMessage() {
        return reptransfeMessage;
    }

    public void setReptransfeUser(Long reptransfeUser) {
        this.reptransfeUser = reptransfeUser;
    }

    public Long getReptransfeUser() {
        return reptransfeUser;
    }

    public void setReptransfeToUser(Long reptransfeToUser) {
        this.reptransfeToUser = reptransfeToUser;
    }

    public Long getReptransfeToUser() {
        return reptransfeToUser;
    }

    public void setReptransfeUserType(Integer reptransfeUserType) {
        this.reptransfeUserType = reptransfeUserType;
    }

    public Integer getReptransfeUserType() {
        return reptransfeUserType;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setReptransfeTime(Date reptransfeTime) {
        this.reptransfeTime = reptransfeTime;
    }

    public Date getReptransfeTime() {
        return reptransfeTime;
    }

    public RepairApply getApply() {
        return apply;
    }

    public void setApply(RepairApply apply) {
        this.apply = apply;
    }

    public void setReptransfeIsDelete(Long reptransfeIsDelete) {
        this.reptransfeIsDelete = reptransfeIsDelete;
    }

    public Long getReptransfeIsDelete() {
        return reptransfeIsDelete;
    }

    public Long getReptToDept() {
        return reptToDept;
    }

    public void setReptToDept(Long reptToDept) {
        this.reptToDept = reptToDept;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("reptransferId", getReptransferId())
                .append("reptId", getReptId())
                .append("reptNo", getReptNo())
                .append("reptToId", getReptToId())
                .append("reptToNo", getReptToNo())
                .append("applyId", getApplyId())
                .append("reptransfeMessage", getReptransfeMessage())
                .append("reptransfeUser", getReptransfeUser())
                .append("reptransfeToUser", getReptransfeToUser())
                .append("reptransfeUserType", getReptransfeUserType())
                .append("parentId", getParentId())
                .append("reptransfeTime", getReptransfeTime())
                .append("reptransfeIsDelete", getReptransfeIsDelete())
                .append("reptToDept", getReptToDept())
                .append("apply", getApply())
                .toString();
    }
}
