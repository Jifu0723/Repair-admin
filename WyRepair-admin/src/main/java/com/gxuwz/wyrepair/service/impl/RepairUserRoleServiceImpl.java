package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.domain.RepairUserRole;
import com.gxuwz.wyrepair.mapper.RepairUserRoleMapper;
import com.gxuwz.wyrepair.service.IRepairUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairUserRoleServiceImpl implements IRepairUserRoleService {

    @Autowired
    RepairUserRoleMapper repairUserroleMapper;

    /**
     * 新增用户和角色关联表
     *
     * @param repairUserrole 用户和角色信息
     * @return 结果
     */
    public int insertUserRole(RepairUserRole repairUserrole)
    {
        return repairUserroleMapper.insertUserRole(repairUserrole);
    }
}
