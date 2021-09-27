package com.gxuwz.wyrepair.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gxuwz.wyrepair.common.annotation.Excel;
import com.gxuwz.wyrepair.common.core.domain.BaseEntity;

/**
 * 维修工对象 repair_worker
 * 
 * @author ruoyi
 * @date 2021-09-26
 */
public class RepairWorker extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long repairWorkerId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    public void setRepairWorkerId(Long repairWorkerId) 
    {
        this.repairWorkerId = repairWorkerId;
    }

    public Long getRepairWorkerId() 
    {
        return repairWorkerId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("repairWorkerId", getRepairWorkerId())
            .append("userId", getUserId())
            .toString();
    }
}
