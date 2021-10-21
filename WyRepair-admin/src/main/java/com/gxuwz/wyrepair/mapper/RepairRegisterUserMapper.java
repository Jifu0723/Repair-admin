package com.gxuwz.wyrepair.mapper;


import com.gxuwz.wyrepair.domain.RepairRegisterUser;

/**
 * 用户信息Mapper接口
 *
 * @author ruoyi
 * @date 2021-10-20
 */
public interface RepairRegisterUserMapper
{

    /**
     * 注册用户信息
     *
     * @param repairRegisterUser 用户信息
     * @return 结果
     */
    public int insertSysUser(RepairRegisterUser repairRegisterUser);


}
