package com.gxuwz.wyrepair.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxuwz.wyrepair.common.annotation.Excel;
import com.gxuwz.wyrepair.common.core.domain.BaseEntity;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import com.gxuwz.wyrepair.util.RepairCodeGen;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 申请单对象 repair_apply
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@ApiModel("申请单实体")
public class RepairApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long applyId;

    /** 申请单编号 */
    @ApiModelProperty("申请单编号")
    @Excel(name = "申请单编号")
    private String applyNo;

    /** 报修地点 */
    @ApiModelProperty("报修地点")
    @Excel(name = "报修地点")
    private String applyAddress;

    /** 报修内容 */
    @ApiModelProperty("报修内容")
    @Excel(name = "报修内容")
    private String applyContent;

    /** 维修类型：网络设备、水电类、家具类、办公室设备 */
    @ApiModelProperty("维修类型")
    @Excel(name = "维修类型：网络设备、水电类、家具类、办公室设备")
    private Long applyType;


    /** 期望维修时间 */
    @ApiModelProperty("期望维修时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "期望维修时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyExpectTime;

    /** 初次报修所属部门id */
    @ApiModelProperty("初次报修所属部门id")
    @Excel(name = "初次报修所属部门id")
    private Long applyDep;

    /** 报修用户姓名 */
    @ApiModelProperty("报修用户姓名")
    @Excel(name = "报修用户姓名")
    private String applyUser;

    /** 申请用户角色 */
    @ApiModelProperty("申请用户角色")
    @Excel(name = "申请用户角色")
    private String applyRole;

    /** 申请用户 */
    @ApiModelProperty("申请用户")
    @Excel(name = "申请用户ID")
    private Long applyUserid;

    /** 申请单创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请单创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyCreateTime;

    /** 逻辑删除状态（0存在，1删除） */
    @Excel(name = "逻辑删除状态", readConverterExp = "0=存在，1删除")
    private Long applyIsDelete;
    /** 申请图片*/
    private List<RepairAppImg> appImgList;

    /**
     * 维修过程
     */
    private List<RepairProcess> processList;

    /**
     * 构建申请单基本信息
     * @param user
     */
    public void initBaseApply(SysUser user){
       this.applyNo = RepairCodeGen.genApplyNo();
       this.applyUserid = user.getUserId();
       this.applyUser = user.getUserName();
       this.applyDep = user.getDeptId();
       this.applyRole = user.getRoles().get(0).getRoleName();
    }
    /**
     * 构建申请单基本信息
     * @param user
     */
    public void initBaseApply(SysUser user,Long repTypeDepId){
       this.applyNo = RepairCodeGen.genApplyNo();
       this.applyUserid = user.getUserId();
       this.applyUser = user.getUserName();
       this.applyDep = repTypeDepId;
       this.applyRole = user.getRoles().get(0).getRoleName();
    }

    public List<RepairProcess> getProcessList() {
        return processList;
    }

    public void setProcessList(List<RepairProcess> processList) {
        this.processList = processList;
    }

    public List<RepairAppImg> getRepImgList() {
        return appImgList;
    }

    public void setRepImgList(List<RepairAppImg> repImgList) {
        this.appImgList = repImgList;
    }

    public void setApplyId(Long applyId)
    {
        this.applyId = applyId;
    }

    public Long getApplyUserid() {
        return applyUserid;
    }

    public void setApplyUserid(Long applyUserid) {
        this.applyUserid = applyUserid;
    }

    public Long getApplyId()
    {
        return applyId;
    }
    public void setApplyNo(String applyNo) 
    {
        this.applyNo = applyNo;
    }

    public String getApplyNo() 
    {
        return applyNo;
    }
    public void setApplyAddress(String applyAddress) 
    {
        this.applyAddress = applyAddress;
    }

    public String getApplyAddress() 
    {
        return applyAddress;
    }
    public void setApplyContent(String applyContent) 
    {
        this.applyContent = applyContent;
    }

    public String getApplyContent() 
    {
        return applyContent;
    }
    public void setApplyType(Long applyType)
    {
        this.applyType = applyType;
    }

    public Long getApplyType()
    {
        return applyType;
    }
    public void setApplyExpectTime(Date applyExpectTime)
    {
        this.applyExpectTime = applyExpectTime;
    }

    public Date getApplyExpectTime() 
    {
        return applyExpectTime;
    }
    public void setApplyDep(Long applyDep) 
    {
        this.applyDep = applyDep;
    }

    public Long getApplyDep() 
    {
        return applyDep;
    }
    public void setApplyUser(String applyUser) 
    {
        this.applyUser = applyUser;
    }

    public String getApplyUser() 
    {
        return applyUser;
    }
    public void setApplyRole(String applyRole) 
    {
        this.applyRole = applyRole;
    }

    public String getApplyRole() 
    {
        return applyRole;
    }
    public void setApplyCreateTime(Date applyCreateTime) 
    {
        this.applyCreateTime = applyCreateTime;
    }

    public Date getApplyCreateTime() 
    {
        return applyCreateTime;
    }
    public void setApplyIsDelete(Long applyIsDelete) 
    {
        this.applyIsDelete = applyIsDelete;
    }

    public Long getApplyIsDelete() 
    {
        return applyIsDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("applyId", getApplyId())
            .append("applyNo", getApplyNo())
            .append("applyAddress", getApplyAddress())
            .append("applyContent", getApplyContent())
            .append("applyType", getApplyType())
            .append("applyExpectTime", getApplyExpectTime())
            .append("applyDep", getApplyDep())
            .append("applyUser", getApplyUser())
            .append("applyRole", getApplyRole())
            .append("applyCreateTime", getApplyCreateTime())
            .append("applyIsDelete", getApplyIsDelete())
            .toString();
    }
}
