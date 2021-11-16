package com.gxuwz.wyrepair.mapper;

import java.util.List;
import java.util.Map;

import com.gxuwz.wyrepair.domain.Bank;

/**
 * 银行卡信息Mapper接口
 * 
 * @author ruoyi
 * @date 2021-11-16
 */
public interface BankMapper 
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
     */
    Bank selectUserBankList(Long userId);

}
