package com.gxuwz.wyrepair.domain;

import com.gxuwz.wyrepair.common.annotation.Excel;
import com.gxuwz.wyrepair.common.core.domain.BaseEntity;
import com.gxuwz.wyrepair.common.core.domain.entity.SysDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 维修类型对象 repair_reptype
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@ApiModel("维修类型实体")
public class RepairReptype extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long repairTypeId;

    /** 维修类型(网络设备、水电类、家具类、办公室设备) */
    @ApiModelProperty("维修类型")
    @Excel(name = "维修类型(网络设备、水电类、家具类、办公室设备)")
    private String repairType;

    /** 部门id */
    @ApiModelProperty("部门id")
    private Long repairDep;

    /** 部门实体 */
    private SysDept sysDept;

    public Long getRepairDep() {
        return repairDep;
    }

    public void setRepairDep(Long repairDep) {
        this.repairDep = repairDep;
    }

    public SysDept getSysDept() {
        return sysDept;
    }

    public void setSysDept(SysDept sysDept) {
        this.sysDept = sysDept;
    }

    public void setRepairTypeId(Long repairTypeId)
    {
        this.repairTypeId = repairTypeId;
    }

    public Long getRepairTypeId() 
    {
        return repairTypeId;
    }
    public void setRepairType(String repairType) 
    {
        this.repairType = repairType;
    }

    public String getRepairType() 
    {
        return repairType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("repairTypeId", getRepairTypeId())
            .append("repairType", getRepairType())
            .toString();
    }
}
