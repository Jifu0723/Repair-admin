package com.gxuwz.wyrepair.service;

import com.gxuwz.wyrepair.domain.RepairUserRole;

public interface IRepairUserRoleService {

    /**
     * 新增用户和角色关联表
     *
     * @param repairUserrole 用户和角色信息
     * @return 结果
     */
    public int insertUserRole(RepairUserRole repairUserrole);
}
