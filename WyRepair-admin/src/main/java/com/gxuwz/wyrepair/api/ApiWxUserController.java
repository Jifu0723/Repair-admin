package com.gxuwz.wyrepair.api;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.domain.entity.SysRole;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import com.gxuwz.wyrepair.common.core.domain.model.LoginUser;
import com.gxuwz.wyrepair.common.utils.SecurityUtils;
import com.gxuwz.wyrepair.framework.web.service.TokenService;
import com.gxuwz.wyrepair.system.service.ISysUserService;
import com.gxuwz.wyrepair.util.WxMsgUtil;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * 微信授权
 */
@RestController
@RequestMapping("api/repair/wx")
public class ApiWxUserController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysUserService userService;

    /**
     * 获取微信用户登录ID
     *
     * @param
     * @return 结果
     */
    @RequestMapping("/getOpenId")
    public AjaxResult getOpenId(@RequestBody Map<String, Object> map){
        //构造参数,通过appid,secret,js_code换取openid和session_key
        map.put("appid", WxMsgUtil.APP_ID);
        map.put("secret", WxMsgUtil.SECURITY);
        // 固定参数
        map.put("grant_type", "authorization_code");
        //请求微信开发平台判断用户
        String result = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", map);
        JSONObject res = JSON.parseObject(result);
        String openId = res.getString("openid");
        if (StringUtils.isNullOrEmpty(openId)) {
            return AjaxResult.error("请求失败:" + res.get("errcode") + ":" + res.get("errmsg"));
        }else {
            AjaxResult ajax = AjaxResult.success();
            ajax.put("openid", openId);
            System.out.println(openId);
            return ajax;
        }
    }

    /**
     * 更新微信用户登录ID
     *
     * @param
     * @return 结果
     */

    @PostMapping("WxId")
    public AjaxResult WxId(@RequestBody Map<String, Object> map)
    {
        System.out.println(map.get("openid").toString());
        SysUser user = new SysUser();

        LoginUser loginUser = SecurityUtils.getLoginUser();
        System.out.println(loginUser.getUserId());
        SysUser sysUser = sysUserService.selectUserById(loginUser.getUserId());
        SysRole sysRole = sysUser.getRoles().get(0);
        System.out.println(sysRole.getRoleKey());

        if ( "repair".equals(sysRole.getRoleKey())) {
            user.setUserId(loginUser.getUserId());
            user.setWxid(map.get("openid").toString());//获取微信用户ID
            userService.updateUserInformation(user);
            return AjaxResult.success();
        }

        else {
            return AjaxResult.error();
        }

    }

    /**
     * 更新微信用户授权标识ID
     *
     * @param
     * @return 结果
     */

    @PostMapping("GrantId")
    public AjaxResult GrantId()
    {
        SysUser user = new SysUser();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = sysUserService.selectUserById(loginUser.getUserId());
        System.out.println(sysUser);
        if (sysUser.getGrantid() == 0) {
            user.setUserId(loginUser.getUserId());
            user.setGrantid(1L);
            userService.updateUserInformation(user);
            return AjaxResult.success();
        }
        else {
            return AjaxResult.error();
        }

    }

}
