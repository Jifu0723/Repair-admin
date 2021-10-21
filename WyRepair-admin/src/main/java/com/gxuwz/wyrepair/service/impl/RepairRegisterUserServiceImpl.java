package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.domain.RepairRegisterUser;
import com.gxuwz.wyrepair.mapper.RepairRegisterUserMapper;
import com.gxuwz.wyrepair.service.IRepairRegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairRegisterUserServiceImpl implements IRepairRegisterUserService {

    @Autowired
    private RepairRegisterUserMapper repairRegisterUserMapper;


    /**
     * 新增用户信息
     *
     * @param repairRegisterUser 用户信息
     * @return 结果
     */
    public int insertSysUser(RepairRegisterUser repairRegisterUser)
    {
        return repairRegisterUserMapper.insertSysUser(repairRegisterUser);
    }

}
