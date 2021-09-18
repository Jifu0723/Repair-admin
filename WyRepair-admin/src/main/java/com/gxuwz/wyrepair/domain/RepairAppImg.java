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
 * 申请单图片对象 repair_app_img
 * 
 * @author LMTeamTop-2018
 * @date 2021-05-23
 */
@ApiModel("申请单图片实体")
public class RepairAppImg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long appimgId;

    /** 申请单主键 */
    @ApiModelProperty("申请单主键")
    @Excel(name = "申请单主键")
    private Long appId;

    /** 图片名称 */
    @ApiModelProperty("图片名称")
    @Excel(name = "图片名称")
    private String appimgName;

    /** 图片相对路径 */
    @ApiModelProperty("图片相对路径")
    @Excel(name = "图片相对路径")
    private String appimgPath;

    /** 图片上传时间 */
    @ApiModelProperty("图片上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "图片上传时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date appimgCreateTime;

    /** 逻辑删除标识（0存在，1删除） */
    @Excel(name = "逻辑删除标识", readConverterExp = "0=存在，1删除")
    private Integer appimgIsDeleted;

    public void setAppimgId(Long appimgId) 
    {
        this.appimgId = appimgId;
    }

    public Long getAppimgId() 
    {
        return appimgId;
    }
    public void setAppId(Long appId) 
    {
        this.appId = appId;
    }

    public Long getAppId() 
    {
        return appId;
    }
    public void setAppimgName(String appimgName) 
    {
        this.appimgName = appimgName;
    }

    public String getAppimgName() 
    {
        return appimgName;
    }
    public void setAppimgPath(String appimgPath) 
    {
        this.appimgPath = appimgPath;
    }

    public String getAppimgPath() 
    {
        return appimgPath;
    }
    public void setAppimgCreateTime(Date appimgCreateTime) 
    {
        this.appimgCreateTime = appimgCreateTime;
    }

    public Date getAppimgCreateTime() 
    {
        return appimgCreateTime;
    }
    public void setAppimgIsDeleted(Integer appimgIsDeleted) 
    {
        this.appimgIsDeleted = appimgIsDeleted;
    }

    public Integer getAppimgIsDeleted() 
    {
        return appimgIsDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("appimgId", getAppimgId())
            .append("appId", getAppId())
            .append("appimgName", getAppimgName())
            .append("appimgPath", getAppimgPath())
            .append("appimgCreateTime", getAppimgCreateTime())
            .append("appimgIsDeleted", getAppimgIsDeleted())
            .toString();
    }
}
