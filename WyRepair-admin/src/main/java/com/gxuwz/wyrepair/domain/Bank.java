package com.gxuwz.wyrepair.domain;

import com.gxuwz.wyrepair.common.core.domain.entity.SysDept;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gxuwz.wyrepair.common.annotation.Excel;
import com.gxuwz.wyrepair.common.core.domain.BaseEntity;

/**
 * 银行卡信息对象 bank
 *
 * @author ruoyi
 * @date 2021-11-16
 */
public class Bank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long bankId;

    /** 开户银行名称 */
    @Excel(name = "开户银行名称")
    private String bankName;

    /** 银行账号 */
    @Excel(name = "银行账号")
    private String bankAccount;

    /** 银行卡使用者名称 */
    @Excel(name = "银行卡使用者名称")
    private String bankUser;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String cardno;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 用户信息
     */
    private SysUser user;

    private SysDept dept;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public void setBankId(Long bankId)
    {
        this.bankId = bankId;
    }

    public Long getBankId()
    {
        return bankId;
    }
    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBankName()
    {
        return bankName;
    }
    public void setBankAccount(String bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount()
    {
        return bankAccount;
    }
    public void setBankUser(String bankUser)
    {
        this.bankUser = bankUser;
    }

    public String getBankUser()
    {
        return bankUser;
    }
    public void setCardno(String cardno)
    {
        this.cardno = cardno;
    }

    public String getCardno()
    {
        return cardno;
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
        return "Bank{" +
                "bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", bankUser='" + bankUser + '\'' +
                ", cardno='" + cardno + '\'' +
                ", userId=" + userId +
                ", user=" + user +
                ", dept=" + dept +
                '}';
    }
}
