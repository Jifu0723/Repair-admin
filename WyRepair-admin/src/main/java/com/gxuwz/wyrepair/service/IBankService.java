package com.gxuwz.wyrepair.service;


import com.gxuwz.wyrepair.domain.Bank;

/**
 * 银行卡信息Service接口
 * 
 * @author ruoyi
 * @date 2021-11-16
 */
public interface IBankService 
{

    /**
     * 新增银行卡信息
     * 
     * @param bank 银行卡信息
     * @return 结果
     */
    public int insertBank(Bank bank);
    /**
     * 获取用户和银行卡信息
     * @return
     */
    Bank selectUserBankList(Long userId);
}
