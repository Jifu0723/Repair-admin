package com.gxuwz.wyrepair.api;

import com.gxuwz.wyrepair.common.annotation.Log;
import com.gxuwz.wyrepair.common.core.controller.BaseController;
import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import com.gxuwz.wyrepair.common.core.page.TableDataInfo;
import com.gxuwz.wyrepair.common.enums.BusinessType;
import com.gxuwz.wyrepair.common.utils.ServletUtils;
import com.gxuwz.wyrepair.domain.Bank;
import com.gxuwz.wyrepair.framework.web.service.TokenService;
import com.gxuwz.wyrepair.service.IBankService;
import com.gxuwz.wyrepair.system.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bank")
public class ApiBankController extends BaseController {
    @Autowired
    private IBankService bankService;
    @Autowired
    private ISysUserService userService;

    /**
     * 获取用户和银行卡信息
     */
    @ApiOperation("获取用户和银行卡信息")
    @GetMapping("/getuserbank")
    public AjaxResult getuserbank(Long userId) {
        Bank bank = bankService.selectUserBankList(userId);
        return AjaxResult.success(bank);
    }

    /**
     * 新增银行卡信息
     */
    @Log(title = "银行卡信息", businessType = BusinessType.INSERT)
    @PostMapping("adduserbank")
    public AjaxResult adduserbank(@RequestBody Map<String, Object> params)
    {
        int i = 0;
        int j = 0;
        Bank bank = new Bank();
        long userId =  Long.parseLong(String.valueOf((params.get("userId").toString())));//获取前端输入的用户ID
        bank.setUserId(userId);
        bank.setBankName(params.get("bankName").toString());//获取前端输入的开户银行名称
        bank.setBankAccount(params.get("bankAccount").toString());//获取前端输入的银行账号
        bank.setBankUser(params.get("bankUser").toString());//获取前端输入的银行卡使用者名称
        bank.setCardno(params.get("cardno").toString());//获取前端输入的身份证号码
        SysUser user = new SysUser();
        i = bankService.insertBank(bank);
        if (i>0)
        {
            user.setUserId(userId);
            user.setState(1L);
            j = userService.updateUserInformation(user);
        }
        if ((i>0  &&  j>0)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error();
        }
    }

}
