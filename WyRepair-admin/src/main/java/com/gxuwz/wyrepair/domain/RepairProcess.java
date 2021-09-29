package com.gxuwz.wyrepair.domain;

import com.gxuwz.wyrepair.common.annotation.Excel;
import com.gxuwz.wyrepair.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 维修过程描述对象 repair_process
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@ApiModel("维修过程描述实体")
public class RepairProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long processId;

    /** 报修单id */
    @ApiModelProperty("报修单id")
    @Excel(name = "报修单id")
    private Long repaireId;

    /** 报修单编号 */
    @ApiModelProperty("报修单编号")
    @Excel(name = "报修单编号")
    private String repaireNo;

    /** 申请单id */
    @ApiModelProperty("申请单id")
    @Excel(name = "申请单id")
    private Long applyId;

    /** 申请单编号 */
    @ApiModelProperty("申请单编号")
    @Excel(name = "申请单编号")
    private String applyNo;

    /** 维修人员姓名 */
    @ApiModelProperty("维修人员姓名")
    @Excel(name = "维修人员姓名")
    private String repaireName;

    /** 维修人员工号 */
    @ApiModelProperty("维修人员工号")
    @Excel(name = "维修人员工号")
    private Long repaireUserid;

    /** 维修人员所属部门 */
    @ApiModelProperty("维修人员所属部门")
    @Excel(name = "维修人员所属部门")
    private Long repaireDep;

    /** 工作量（100%计算:0.1-1） */
    @ApiModelProperty("工作量（100%计算:0.1-1）")
    @Excel(name = "工作量", readConverterExp = "100%计算:0.1-1")
    private Long workFinish;

    /** 维修过程描述 */
    @ApiModelProperty("维修过程描述")
    @Excel(name = "维修过程描述")
    private String processDescribe;

    /** 逻辑删除（0存在、1删除） */
    @Excel(name = "逻辑删除", readConverterExp = "0=存在、1删除")
    private Integer processIsDelete;

    /**
     * 初始化维修过程信息
     * @param apply 申请单
     * @param repair 维修单
     * @param userName 维修人员姓名
     */
    public void initProcess(RepairApply apply, RepairRepTb repair, String userName,Long userid){
        this.applyId = apply.getApplyId();//申请单的报修自增主键
        this.applyNo = apply.getApplyNo();//申请单的报修编号
        this.repaireDep = repair.getRepairDep();//报修单的初次报修所属部门id
        this.repaireId = repair.getRepairId();//报修单的自增主键
        this.repaireNo = repair.getRepairNo();//报修单的报修编号
        this.repaireName = userName;//维修人员姓名
        this.repaireUserid = userid;//维修人员工号
    }



    public Long getRepaireUserid() {
        return repaireUserid;
    }

    public void setRepaireUserid(Long repaireUserid) {
        this.repaireUserid = repaireUserid;
    }

    public void setProcessId(Long processId)
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }
    public void setRepaireId(Long repaireId) 
    {
        this.repaireId = repaireId;
    }

    public Long getRepaireId() 
    {
        return repaireId;
    }
    public void setRepaireNo(String repaireNo) 
    {
        this.repaireNo = repaireNo;
    }

    public String getRepaireNo() 
    {
        return repaireNo;
    }
    public void setApplyId(Long applyId) 
    {
        this.applyId = applyId;
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
    public void setRepaireName(String repaireName) 
    {
        this.repaireName = repaireName;
    }

    public String getRepaireName() 
    {
        return repaireName;
    }
    public void setRepaireDep(Long repaireDep) 
    {
        this.repaireDep = repaireDep;
    }

    public Long getRepaireDep() 
    {
        return repaireDep;
    }
    public void setWorkFinish(Long workFinish) 
    {
        this.workFinish = workFinish;
    }

    public Long getWorkFinish() 
    {
        return workFinish;
    }
    public void setProcessDescribe(String processDescribe) 
    {
        this.processDescribe = processDescribe;
    }

    public String getProcessDescribe() 
    {
        return processDescribe;
    }
    public void setProcessIsDelete(Integer processIsDelete) 
    {
        this.processIsDelete = processIsDelete;
    }

    public Integer getProcessIsDelete() 
    {
        return processIsDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("processId", getProcessId())
            .append("repaireId", getRepaireId())
            .append("repaireNo", getRepaireNo())
            .append("applyId", getApplyId())
            .append("applyNo", getApplyNo())
            .append("repaireName", getRepaireName())
            .append("repaireDep", getRepaireDep())
            .append("workFinish", getWorkFinish())
            .append("processDescribe", getProcessDescribe())
            .append("createTime", getCreateTime())
            .append("processIsDelete", getProcessIsDelete())
            .toString();
    }
}
