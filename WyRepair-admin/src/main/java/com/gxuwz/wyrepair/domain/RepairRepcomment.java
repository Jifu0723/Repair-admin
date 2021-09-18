package com.gxuwz.wyrepair.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxuwz.wyrepair.common.annotation.Excel;
import com.gxuwz.wyrepair.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 报修单评论对象 repair_repcomment
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@ApiModel("报修单评论实体")
public class RepairRepcomment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long repcommentId;

    /** 报修单id */
    @ApiModelProperty("报修单id")
    @Excel(name = "报修单id")
    private Long repcId;

    /** 报修单编号 */
    @ApiModelProperty("报修单编号")
    @Excel(name = "报修单编号")
    private String repcNo;

    /** 申请表id */
    @ApiModelProperty("申请表id")
    @Excel(name = "申请表id")
    private Long applyId;

    /** 申请单编号 */
    @ApiModelProperty("申请单编号")
    @Excel(name = "申请单编号")
    private String applyNo;

    /** 维修类型：网络设备、水电类、家具类、办公室设备 */
    @ApiModelProperty("维修类型")
    @Excel(name = "维修类型：网络设备、水电类、家具类、办公室设备")
    private Integer repairType;

    /** 评论内容 */
    @ApiModelProperty("评论内容")
    @Excel(name = "评论内容")
    private String repcommentContent;

    /** 服务评星级 */
    @ApiModelProperty("服务评星级")
    @Excel(name = "服务评星级")
    private Integer repcommentService;

    /** 评论用户id */
    @ApiModelProperty("评论用户id")
    @Excel(name = "评论用户id")
    private Long repcommenUserid;

    /** 评论时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "评论时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date repcommenCrteateTime;

    /** 逻辑删除（0存在、1删除） */
    @Excel(name = "逻辑删除", readConverterExp = "0=存在、1删除")
    private Long repcIsDelete;

    public void setRepcommentId(Long repcommentId) 
    {
        this.repcommentId = repcommentId;
    }

    public Long getRepcommentId() 
    {
        return repcommentId;
    }
    public void setRepcId(Long repcId) 
    {
        this.repcId = repcId;
    }

    public Long getRepcId() 
    {
        return repcId;
    }
    public void setRepcNo(String repcNo) 
    {
        this.repcNo = repcNo;
    }

    public String getRepcNo() 
    {
        return repcNo;
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
    public void setRepairType(Integer repairType) 
    {
        this.repairType = repairType;
    }

    public Integer getRepairType() 
    {
        return repairType;
    }
    public void setRepcommentContent(String repcommentContent) 
    {
        this.repcommentContent = repcommentContent;
    }

    public String getRepcommentContent() 
    {
        return repcommentContent;
    }
    public void setRepcommentService(Integer repcommentService) 
    {
        this.repcommentService = repcommentService;
    }

    public Integer getRepcommentService() 
    {
        return repcommentService;
    }
    public void setRepcommenUserid(Long repcommenUserid) 
    {
        this.repcommenUserid = repcommenUserid;
    }

    public Long getRepcommenUserid() 
    {
        return repcommenUserid;
    }
    public void setRepcommenCrteateTime(Date repcommenCrteateTime) 
    {
        this.repcommenCrteateTime = repcommenCrteateTime;
    }

    public Date getRepcommenCrteateTime() 
    {
        return repcommenCrteateTime;
    }
    public void setRepcIsDelete(Long repcIsDelete) 
    {
        this.repcIsDelete = repcIsDelete;
    }

    public Long getRepcIsDelete() 
    {
        return repcIsDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("repcommentId", getRepcommentId())
            .append("repcId", getRepcId())
            .append("repcNo", getRepcNo())
            .append("applyId", getApplyId())
            .append("applyNo", getApplyNo())
            .append("repairType", getRepairType())
            .append("repcommentContent", getRepcommentContent())
            .append("repcommentService", getRepcommentService())
            .append("repcommenUserid", getRepcommenUserid())
            .append("repcommenCrteateTime", getRepcommenCrteateTime())
            .append("repcIsDelete", getRepcIsDelete())
            .toString();
    }
}
