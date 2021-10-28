package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.constant.UserConstants;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.domain.entity.SysRole;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.SecurityUtils;
import com.gxuwz.wyrepair.common.utils.StringUtils;
import com.gxuwz.wyrepair.domain.RepairRegisterUser;
import com.gxuwz.wyrepair.domain.RepairUserRole;
import com.gxuwz.wyrepair.domain.RepairWorker;
import com.gxuwz.wyrepair.service.IRepairRegisterUserService;
import com.gxuwz.wyrepair.service.IRepairUserRoleService;
import com.gxuwz.wyrepair.service.IRepairWorkerService;
import com.gxuwz.wyrepair.system.service.ISysRoleService;
import com.gxuwz.wyrepair.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 注册用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/repair/register")
public class ApiRepairRegisterUserController {

    @Autowired
    private IRepairRegisterUserService repairRegisterUserService;
    @Autowired
    private IRepairWorkerService repairWorkerService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private IRepairUserRoleService repairUserRoleService;
    @Autowired
    private ISysUserService userService;


    /**
     * 注册新用户
     */
    @Log(title = "用户信息管理", businessType = BusinessType.INSERT)
    @PostMapping("register")
    public AjaxResult toRegister(@RequestBody Map<String, Object> params,@Validated @RequestBody SysUser user)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        RepairRegisterUser repairRegisterUser = new RepairRegisterUser();
        RepairUserRole repairUserRole = new RepairUserRole();
        repairRegisterUser.setUserName( params.get("userName").toString());//获取前端输入的用户账号
        repairRegisterUser.setPhonenumber( params.get("phonenumber").toString());//获取前端输入的用户姓名
        repairRegisterUser.setEmail( params.get("email").toString());//获取前端输入的用户邮箱
        repairRegisterUser.setPassword(SecurityUtils.encryptPassword(params.get("passWord").toString()));//获取前端输入的用户密码
        repairRegisterUser.setNickName(params.get("nickName").toString());//获取前端输入的用户姓名
        Long roleId =  Long.parseLong(String.valueOf(params.get("roleName").toString()));//获取前端输入的用户姓名
        Long deptId =  Long.parseLong(String.valueOf(params.get("deptName")));//获取前端输入的院系名称

        repairRegisterUser.setDeptId(deptId);//插入二级学院id
        repairRegisterUser.setSex(params.get("sex").toString());//获取前端输入的用户性别

        j = repairRegisterUserService.insertSysUser(repairRegisterUser);
        repairUserRole.setRoleId(roleId);//插入角色id
        repairUserRole.setUserId(repairRegisterUser.getUserId());

        k = repairUserRoleService.insertUserRole(repairUserRole);
        SysRole roles = roleService.selectRoleKeyById(roleId);

        //设置维修工部门关联表
        if ("repair".equals(roles.getRoleKey()))
        {
            RepairWorker repairWorker = new RepairWorker();
            repairWorker.setUserId(repairRegisterUser.getUserId());
            i = repairWorkerService.insertRepairWorker(repairWorker);
        }
        if ((i>0  ||  (j>0 && k>0) )){
            return AjaxResult.success();
        }else {
            return AjaxResult.error();
        }
    }
}
