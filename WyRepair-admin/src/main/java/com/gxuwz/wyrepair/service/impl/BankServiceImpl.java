package com.gxuwz.wyrepair.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gxuwz.wyrepair.mapper.BankMapper;
import com.gxuwz.wyrepair.domain.Bank;
import com.gxuwz.wyrepair.service.IBankService;

/**
 * 银行卡信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-11-16
 */
@Service
public class BankServiceImpl implements IBankService 
{
    @Autowired
    private BankMapper bankMapper;

    /**
     * 新增银行卡信息
     * 
     * @param bank 银行卡信息
     * @return 结果
     */
    @Override
    public int insertBank(Bank bank)
    {
        return bankMapper.insertBank(bank);
    }

    @Override
    public Bank selectUserBankList(Long userId) {
        return bankMapper.selectUserBankList(userId);
    }

}
