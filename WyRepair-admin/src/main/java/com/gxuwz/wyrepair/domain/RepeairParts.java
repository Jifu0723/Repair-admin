package com.gxuwz.wyrepair.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gxuwz.wyrepair.common.annotation.Excel;
import com.gxuwz.wyrepair.common.core.domain.BaseEntity;

/**
 * 配件信息对象 repeair_parts
 * 
 * @author ruoyi
 * @date 2021-09-25
 */
public class RepeairParts extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long partsId;

    /** 配件购买人 */
    @Excel(name = "配件购买人")
    private String partsPerson;

    /** 配件代码 */
    @Excel(name = "配件代码")
    private String partsNo;

    /** 配件名称 */
    @Excel(name = "配件名称")
    private String partsName;

    /** 配件数量 */
    @Excel(name = "配件数量")
    private Long partsNumbers;

    /** 配件单价 */
    @Excel(name = "配件单价")
    private Long partsPrice;

    /** 配件购买数量 */
    @Excel(name = "配件购买数量")
    private Long partsPurchase;

    /** 配件购买说明 */
    @Excel(name = "配件购买说明")
    private String partsPurchaseExplain;

    /** 配件购买时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "配件购买时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date partsTime;

    /** 配件总价格 */
    @Excel(name = "配件总价格")
    private Long partsTotalPrice;

    /** 配件剩余数量 */
    @Excel(name = "配件剩余数量")
    private Long partsNumber;

    /** 配件使用率状态 */
    @Excel(name = "配件使用率状态")
    private Long partsState;

    /** 配件品种 */
    @Excel(name = "配件品种")
    private String partsVariety;

    /** 配件生产机构 */
    @Excel(name = "配件生产机构")
    private String partsMechanism;

    /** 配件售后服务热线 */
    @Excel(name = "配件售后服务热线")
    private String partsPhone;

    /** 配件登记机关 */
    @Excel(name = "配件登记机关")
    private String partsOffice;

    /** 配件维保单位地址 */
    @Excel(name = "配件维保单位地址")
    private String partsAddress;

    public void setPartsId(Long partsId) 
    {
        this.partsId = partsId;
    }

    public Long getPartsId() 
    {
        return partsId;
    }
    public void setPartsPerson(String partsPerson) 
    {
        this.partsPerson = partsPerson;
    }

    public String getPartsPerson() 
    {
        return partsPerson;
    }
    public void setPartsNo(String partsNo) 
    {
        this.partsNo = partsNo;
    }

    public String getPartsNo() 
    {
        return partsNo;
    }
    public void setPartsName(String partsName) 
    {
        this.partsName = partsName;
    }

    public String getPartsName() 
    {
        return partsName;
    }
    public void setPartsNumbers(Long partsNumbers) 
    {
        this.partsNumbers = partsNumbers;
    }

    public Long getPartsNumbers() 
    {
        return partsNumbers;
    }
    public void setPartsPrice(Long partsPrice) 
    {
        this.partsPrice = partsPrice;
    }

    public Long getPartsPrice() 
    {
        return partsPrice;
    }
    public void setPartsPurchase(Long partsPurchase) 
    {
        this.partsPurchase = partsPurchase;
    }

    public Long getPartsPurchase() 
    {
        return partsPurchase;
    }
    public void setPartsPurchaseExplain(String partsPurchaseExplain) 
    {
        this.partsPurchaseExplain = partsPurchaseExplain;
    }

    public String getPartsPurchaseExplain() 
    {
        return partsPurchaseExplain;
    }
    public void setPartsTime(Date partsTime) 
    {
        this.partsTime = partsTime;
    }

    public Date getPartsTime() 
    {
        return partsTime;
    }
    public void setPartsTotalPrice(Long partsTotalPrice) 
    {
        this.partsTotalPrice = partsTotalPrice;
    }

    public Long getPartsTotalPrice() 
    {
        return partsTotalPrice;
    }
    public void setPartsNumber(Long partsNumber) 
    {
        this.partsNumber = partsNumber;
    }

    public Long getPartsNumber() 
    {
        return partsNumber;
    }
    public void setPartsState(Long partsState) 
    {
        this.partsState = partsState;
    }

    public Long getPartsState() 
    {
        return partsState;
    }
    public void setPartsVariety(String partsVariety) 
    {
        this.partsVariety = partsVariety;
    }

    public String getPartsVariety() 
    {
        return partsVariety;
    }
    public void setPartsMechanism(String partsMechanism) 
    {
        this.partsMechanism = partsMechanism;
    }

    public String getPartsMechanism() 
    {
        return partsMechanism;
    }
    public void setPartsPhone(String partsPhone) 
    {
        this.partsPhone = partsPhone;
    }

    public String getPartsPhone() 
    {
        return partsPhone;
    }
    public void setPartsOffice(String partsOffice) 
    {
        this.partsOffice = partsOffice;
    }

    public String getPartsOffice() 
    {
        return partsOffice;
    }
    public void setPartsAddress(String partsAddress) 
    {
        this.partsAddress = partsAddress;
    }

    public String getPartsAddress() 
    {
        return partsAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("partsId", getPartsId())
            .append("partsPerson", getPartsPerson())
            .append("partsNo", getPartsNo())
            .append("partsName", getPartsName())
            .append("partsNumbers", getPartsNumbers())
            .append("partsPrice", getPartsPrice())
            .append("partsPurchase", getPartsPurchase())
            .append("partsPurchaseExplain", getPartsPurchaseExplain())
            .append("partsTime", getPartsTime())
            .append("partsTotalPrice", getPartsTotalPrice())
            .append("partsNumber", getPartsNumber())
            .append("partsState", getPartsState())
            .append("partsVariety", getPartsVariety())
            .append("partsMechanism", getPartsMechanism())
            .append("partsPhone", getPartsPhone())
            .append("partsOffice", getPartsOffice())
            .append("partsAddress", getPartsAddress())
            .toString();
    }
}
