package com.gxuwz.wyrepair.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxuwz.wyrepair.common.annotation.Excel;
import com.gxuwz.wyrepair.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 报修单对象 repair_rep_tb
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@ApiModel("报修单实体")
public class RepairRepTb extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 报修单自增主键
     */
    private Long repairId;

    /**
     * 报修单编号
     */
    @ApiModelProperty("报修单编号")
    @Excel(name = "报修单编号")
    private String repairNo;

    /**
     * 报修地点
     */
    @ApiModelProperty("报修地点")
    @Excel(name = "报修地点")
    private String repairAddress;

    /**
     * 报修内容
     */
    @ApiModelProperty("报修内容")
    @Excel(name = "报修内容")
    private String repairContent;

    /**
     * 维修单单价
     */
    @ApiModelProperty("维修单单价")
    @Excel(name = "维修单单价")
    private Integer repairMoney;

    /**
     * 维修类型：网络设备、水电类、家具类、办公室设备
     */
    @ApiModelProperty("维修类型")
    @Excel(name = "维修类型：网络设备、水电类、家具类、办公室设备")
    private Long repairType;

    /**
     * 期望维修形式：换新、换配件、维修
     */
    @ApiModelProperty("维修类型")
    @Excel(name = "期望维修形式：换新、换配件、维修")
    private Long repairExpectType;

    /**
     * 期望维修时间
     */
    @ApiModelProperty("期望维修时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "期望维修时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date repairExpectTime;

    /**
     * 报修状态（1待接单、2维修中、3转单中、4已接单、5已完成单）
     */
    @ApiModelProperty("报修状态（1待接单、2已接单、3转单中、4已完成单）")
    @Excel(name = "报修状态", readConverterExp = "1=待接单、22已接单、3转单中、4已完成单 ")
    private Integer repairState;

    /**
     * 维修后设备状态描述(0待修、1已修复、2已换新、3未修复、4无法修复)
     */
    @ApiModelProperty("维修后设备状态描述(0待修、1已修复、2已换新、3未修复、4无法修复) ")
    @Excel(name = "维修后设备状态描述(0待修、1已修复、2已换新、3未修复、4无法修复)")
    private Integer repairedState;

    /**
     * 是否销单（0未销单，1已销单）
     */
    @ApiModelProperty("是否销单（0未销单，1已销单）")
    @Excel(name = "是否销单", readConverterExp = "0=未销单，1已销单")
    private Integer repairDestory;

    /**
     * 是否转单（0未转单、1已转单）
     */
    @ApiModelProperty("是否转单（0未转单、1已转单）")
    @Excel(name = "是否转单", readConverterExp = "0=未转单、1已转单")
    private Integer repairTransfer;

    /**
     * 是否评论（0未评论、1已评论）
     */
    @ApiModelProperty("是否评论（0未评论、1已评论）")
    @Excel(name = "是否转单", readConverterExp = "0未评论、1已评论")
    private Integer repairComment;

    /**
     * 是否为当前运转单（0不是、1是）【用于转单所生成新的维修单与旧的维修单区别】
     */
    @ApiModelProperty("是否为当前运转单（0不是、1是）【用于转单所生成新的维修单与旧的维修单区别】")
    @Excel(name = "是否为当前运转单", readConverterExp = "0=不是、1是")
    private Integer curWork;

    /**
     * 初次报修所属部门id
     */
    @ApiModelProperty("初次报修所属部门id")
    @Excel(name = "初次报修所属部门id")
    private Long repairDep;

    /**
     * 申请表id
     */
    @ApiModelProperty("申请表id")
    @Excel(name = "申请表id")
    private Long applyId;

    /**
     * 销单时间
     */
    @ApiModelProperty("销单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "销单时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date repairDestoryTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date repairCreateTime;

    /**
     * 逻辑删除状态（0存在，1删除）
     */
    @Excel(name = "逻辑删除状态", readConverterExp = "0=存在，1删除")
    private Integer repairIsDelete;


    /** 报修年份 */
    @Excel(name = "报修年份")
    private Integer repairYear;

    /** 报修月份 */
    @Excel(name = "报修月份")
    private Integer repairMonth;

    /** 报修日份 */
    @Excel(name = "报修日份")
    private Integer repairDay;

    /** 报修者姓名 */
    @Excel(name = "报修者姓名")
    private String repairName;

    /** 维修人员姓名 */
    @Excel(name = "维修人员姓名")
    private String repaireName;


    public Integer getRepairYear() {
        return repairYear;
    }

    public void setRepairYear(Integer repairYear) {
        this.repairYear = repairYear;
    }

    public Integer getRepairMonth() {
        return repairMonth;
    }

    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }

    public void setRepairMonth(Integer repairMonth) {
        this.repairMonth = repairMonth;
    }

    public Integer getRepairDay() {
        return repairDay;
    }

    public void setRepairDay(Integer repairDay) {
        this.repairDay = repairDay;
    }

    public String getRepaireName() {
        return repaireName;
    }

    public void setRepaireName(String repaireName) {
        this.repaireName = repaireName;
    }

    /**
     * 维修类型
     */
    private RepairReptype repType;

    /**
     * 申请单
     */
    private RepairApply apply;

    /**
     * 报修图片
     */
    private List<RepairRepImg> repImgList;

    /**
     * 报修图片
     */
    private List<RepairAppImg> appImgList;

    /**
     * 维修过程
     */
    private List<RepairProcess> processList;

    /**
     * 维修单评论
     */
    private RepairRepcomment repcomment;

    /**
     * 初始化信息
     * @param apply 申请单
     * @param repairNo 编号
     * @param depID 部门id
     */
    public void initRepairApply(RepairApply apply, String repairNo, Long depID) {
        this.repairNo = repairNo;//新的报修编号
        this.applyId = apply.getApplyId();//申请单编号
        this.repairDep = depID;//初次报修所属部门id
        this.repairAddress = apply.getApplyAddress();//申请单报修地点
        this.repairContent = apply.getApplyContent();//申请单报修内容
        this.repairExpectTime = apply.getApplyExpectTime();//申请单期望维修时间
        this.repairType = apply.getApplyType();//申请单维修类型
        this.repairExpectType = apply.getApplyExpectType();//申请单期望维修形式
    }

    public Integer getRepairMoney() {
        return repairMoney;
    }

    public void setRepairMoney(Integer repairMoney) {
        this.repairMoney = repairMoney;
    }

    public Long getRepairExpectType() {
        return repairExpectType;
    }

    public void setRepairExpectType(Long repairExpectType) {
        this.repairExpectType = repairExpectType;
    }

    public RepairRepcomment getRepcomment() {
        return repcomment;
    }

    public RepairReptype getRepType() {
        return repType;
    }

    public void setRepType(RepairReptype repType) {
        this.repType = repType;
    }

    public void setRepcomment(RepairRepcomment repcomment) {
        this.repcomment = repcomment;
    }

    public RepairApply getApply() {
        return apply;
    }

    public void setApply(RepairApply apply) {
        this.apply = apply;
    }

    public List<RepairProcess> getProcessList() {
        return processList;
    }

    public void setProcessList(List<RepairProcess> processList) {
        this.processList = processList;
    }

    public List<RepairRepImg> getRepImgList() {
        return repImgList;
    }

    public void setRepImgList(List<RepairRepImg> repImgList) {
        this.repImgList = repImgList;
    }

    public Integer getCurWork() {
        return curWork;
    }

    public void setCurWork(Integer curWork) {
        this.curWork = curWork;
    }

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
    }

    public Long getRepairId() {
        return repairId;
    }

    public void setRepairNo(String repairNo) {
        this.repairNo = repairNo;
    }

    public String getRepairNo() {
        return repairNo;
    }

    public void setRepairAddress(String repairAddress) {
        this.repairAddress = repairAddress;
    }

    public String getRepairAddress() {
        return repairAddress;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairType(Long repairType) {
        this.repairType = repairType;
    }

    public Long getRepairType() {
        return repairType;
    }

    public void setRepairExpectTime(Date repairExpectTime) {
        this.repairExpectTime = repairExpectTime;
    }

    public Date getRepairExpectTime() {
        return repairExpectTime;
    }

    public Integer setRepairState(Integer repairState) {
        this.repairState = repairState;
        return repairState;
    }

    public Integer getRepairState() {
        return repairState;
    }

    public void setRepairedState(Integer repairedState) {
        this.repairedState = repairedState;
    }

    public Integer getRepairedState() {
        return repairedState;
    }

    public void setRepairDestory(Integer repairDestory) {
        this.repairDestory = repairDestory;
    }

    public Integer getRepairDestory() {
        return repairDestory;
    }

    public void setRepairTransfer(Integer repairTransfer) {
        this.repairTransfer = repairTransfer;
    }

    public Integer getRepairTransfer() {
        return repairTransfer;
    }

    public void setRepairDep(Long repairDep) {
        this.repairDep = repairDep;
    }

    public Long getRepairDep() {
        return repairDep;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setRepairDestoryTime(Date repairDestoryTime) {
        this.repairDestoryTime = repairDestoryTime;
    }

    public Date getRepairDestoryTime() {
        return repairDestoryTime;
    }

    public void setRepairCreateTime(Date repairCreateTime) {
        this.repairCreateTime = repairCreateTime;
    }

    public Date getRepairCreateTime() {
        return repairCreateTime;
    }

    public void setRepairIsDelete(Integer repairIsDelete) {
        this.repairIsDelete = repairIsDelete;
    }

    public Integer getRepairIsDelete() {
        return repairIsDelete;
    }

    public Integer getRepairComment() {
        return repairComment;
    }

    public void setRepairComment(Integer repairComment) {
        this.repairComment = repairComment;
    }

    public List<RepairAppImg> getAppImgList() {
        return appImgList;
    }

    public void setAppImgList(List<RepairAppImg> appImgList) {
        this.appImgList = appImgList;
    }

    @Override
    public String toString() {
        return "RepairRepTb{" +
                "repairId=" + repairId +
                ", repairNo='" + repairNo + '\'' +
                ", repairAddress='" + repairAddress + '\'' +
                ", repairContent='" + repairContent + '\'' +
                ", repairMoney=" + repairMoney +
                ", repairType=" + repairType +
                ", repairExpectType=" + repairExpectType +
                ", repairExpectTime=" + repairExpectTime +
                ", repairState=" + repairState +
                ", repairedState=" + repairedState +
                ", repairDestory=" + repairDestory +
                ", repairTransfer=" + repairTransfer +
                ", repairComment=" + repairComment +
                ", curWork=" + curWork +
                ", repairDep=" + repairDep +
                ", applyId=" + applyId +
                ", repairDestoryTime=" + repairDestoryTime +
                ", repairCreateTime=" + repairCreateTime +
                ", repairIsDelete=" + repairIsDelete +
                ", repairYear=" + repairYear +
                ", repairMonth=" + repairMonth +
                ", repairDay=" + repairDay +
                ", repairName='" + repairName + '\'' +
                ", repaireName='" + repaireName + '\'' +
                ", repType=" + repType +
                ", apply=" + apply +
                ", repImgList=" + repImgList +
                ", appImgList=" + appImgList +
                ", processList=" + processList +
                ", repcomment=" + repcomment +
                '}';
    }
}
