package com.gxuwz.wyrepair.service;

import com.gxuwz.wyrepair.domain.RepairRegisterUser;

public interface IRepairRegisterUserService {

    /**
     * 注册用户信息
     *
     * @param repairRegisterUser 用户信息
     * @return 结果
     */
    public int insertSysUser(RepairRegisterUser repairRegisterUser);

}
