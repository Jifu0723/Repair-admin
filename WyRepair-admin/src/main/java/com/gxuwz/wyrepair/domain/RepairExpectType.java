package com.gxuwz.wyrepair.domain;

import com.gxuwz.wyrepair.common.annotation.Excel;
import com.gxuwz.wyrepair.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 期望维修类型对象 repair_expect_type
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@ApiModel("期望维修类型实体")
public class RepairExpectType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long repexpectTypeId;

    /** 期望维修形式(换新、换配件、维修) */
    @ApiModelProperty("期望维修形式")
    @Excel(name = "期望维修形式(换新、换配件、维修)")
    private String repexpectType;

    public void setRepexpectTypeId(Long repexpectTypeId) 
    {
        this.repexpectTypeId = repexpectTypeId;
    }

    public Long getRepexpectTypeId() 
    {
        return repexpectTypeId;
    }
    public void setRepexpectType(String repexpectType) 
    {
        this.repexpectType = repexpectType;
    }

    public String getRepexpectType() 
    {
        return repexpectType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("repexpectTypeId", getRepexpectTypeId())
            .append("repexpectType", getRepexpectType())
            .toString();
    }
}
