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
 * 报修单图片对象 repair_rep_img
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@ApiModel("报修单图片实体")
public class RepairRepImg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long repimgId;

    /** 报修主键id */
    @ApiModelProperty("报修主键id")
    @Excel(name = "报修主键id")
    private Long repiId;

    /** 图片名称 */
    @ApiModelProperty("图片名称")
    @Excel(name = "图片名称")
    private String repimgName;

    /** 图片相对路径 */
    @ApiModelProperty("图片相对路径")
    @Excel(name = "图片相对路径")
    private String repimgPath;

    /** 图片上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "图片上传时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date repimgCreateTime;

    /** 逻辑删除标识（0存在，1删除） */
    @Excel(name = "逻辑删除标识", readConverterExp = "0=存在，1删除")
    private Integer repimgIsDeleted;

    public void setRepimgId(Long repimgId) 
    {
        this.repimgId = repimgId;
    }

    public Long getRepimgId() 
    {
        return repimgId;
    }
    public void setRepiId(Long repiId) 
    {
        this.repiId = repiId;
    }

    public Long getRepiId() 
    {
        return repiId;
    }
    public void setRepimgPath(String repimgPath) 
    {
        this.repimgPath = repimgPath;
    }

    public String getRepimgPath() 
    {
        return repimgPath;
    }

    public void setRepimgCreateTime(Date repimgCreateTime) 
    {
        this.repimgCreateTime = repimgCreateTime;
    }

    public Date getRepimgCreateTime() 
    {
        return repimgCreateTime;
    }
    public void setRepimgIsDeleted(Integer repimgIsDeleted) 
    {
        this.repimgIsDeleted = repimgIsDeleted;
    }

    public Integer getRepimgIsDeleted() 
    {
        return repimgIsDeleted;
    }

    public String getRepimgName() {
        return repimgName;
    }

    public void setRepimgName(String repimgName) {
        this.repimgName = repimgName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("repimgId", getRepimgId())
            .append("repiId", getRepiId())
            .append("repimgPath", getRepimgPath())
            .append("repimgCreateTime", getRepimgCreateTime())
            .append("repimgIsDeleted", getRepimgIsDeleted())
            .toString();
    }
}
