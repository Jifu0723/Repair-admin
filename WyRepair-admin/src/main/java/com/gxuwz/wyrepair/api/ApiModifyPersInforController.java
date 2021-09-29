package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.utils.ServletUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import com.gxuwz.wyrepair.common.core.domain.model.LoginUser;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.SecurityUtils;
import com.gxuwz.wyrepair.framework.web.service.TokenService;
import com.gxuwz.wyrepair.system.service.ISysUserService;

import java.util.Map;

@Api(tags = "修改个人信息模块")
@RestController
@RequestMapping("/api/repair/modifypersinfor")
public class ApiModifyPersInforController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("modifypersinfor")
    public AjaxResult updateProfile(@RequestBody Map<String, Object> params)
    {
        SysUser user = new SysUser();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        user.setUserId(sysUser.getUserId());//获取userId
        user.setUserName(params.get("userName").toString());//获取前端输入的用户账号
        user.setNickName(params.get("nickName").toString());//获取前端输入的用户姓名
        user.setPhonenumber(params.get("phonenumber").toString());//获取前端输入的手机号码
        user.setEmail(params.get("email").toString());//获取前端输入的邮箱
        user.setSex(params.get("sex").toString());//获取前端输入的性别
        long DeptId =  Long.parseLong(String.valueOf(params.get("deptName")));//获取前端输入的院系名称
        user.setDeptId(DeptId);
        if (userService.updateUserProfile(user) > 0)
        {
            // 更新缓存用户信息
            sysUser.setNickName(user.getNickName());
            sysUser.setPhonenumber(user.getPhonenumber());
            sysUser.setEmail(user.getEmail());
            sysUser.setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
        }
        String newPassword = params.get("newPassword").toString();
        String oldPassword = params.get("oldPassword").toString();
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0)
        {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改个人信息异常，请联系管理员");
    }
}
