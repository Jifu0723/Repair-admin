package com.gxuwz.wyrepair.web.controller.system;

import java.util.List;
import java.util.Set;

import com.gxuwz.wyrepair.common.core.domain.entity.SysDept;
import com.gxuwz.wyrepair.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gxuwz.wyrepair.common.constant.Constants;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.domain.entity.SysMenu;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import com.gxuwz.wyrepair.common.core.domain.model.LoginBody;
import com.gxuwz.wyrepair.common.utils.SecurityUtils;
import com.gxuwz.wyrepair.framework.web.service.SysLoginService;
import com.gxuwz.wyrepair.framework.web.service.SysPermissionService;
import com.gxuwz.wyrepair.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {

        SysUser user = SecurityUtils.getLoginUser().getUser();
        AjaxResult ajax = AjaxResult.success();
        //管理员登录
        if ("admin".equals(user.getUserName()))
        {
            // 角色集合
            Set<String> roles = permissionService.getRolePermission(user);
            // 权限集合
            Set<String> permissions = permissionService.getMenuPermission(user);
            ajax.put("user", user);
            ajax.put("roles", roles);
            ajax.put("permissions", permissions);
        }
        //其他人员登录
        if (!"admin".equals(user.getUserName()))
        {
            SysDept sysDept = deptService.selectDeptById(user.getDeptId());
            String deptName = sysDept.getDeptName();
            ajax.put("deptName", deptName);
            // 角色集合
            Set<String> roles = permissionService.getRolePermission(user);
            // 权限集合
            Set<String> permissions = permissionService.getMenuPermission(user);
            ajax.put("user", user);
            ajax.put("roles", roles);
            ajax.put("permissions", permissions);
        }
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
