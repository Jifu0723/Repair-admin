package com.gxuwz.wyrepair.service.impl;

import com.gxuwz.wyrepair.common.core.domain.AjaxResult;
import com.gxuwz.wyrepair.common.core.domain.entity.SysUser;
import com.gxuwz.wyrepair.common.utils.ServletUtils;
import com.gxuwz.wyrepair.framework.web.service.TokenService;
import com.gxuwz.wyrepair.domain.RepairApply;
import com.gxuwz.wyrepair.domain.RepairProcess;
import com.gxuwz.wyrepair.domain.RepairRepTb;
import com.gxuwz.wyrepair.domain.RepairReptype;
import com.gxuwz.wyrepair.mapper.RepairApplyMapper;
import com.gxuwz.wyrepair.service.*;
import com.gxuwz.wyrepair.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 申请单Service业务层处理
 *
 * @author LMTeamTop-2018
 * @date 2021-05-10
 */
@Service
public class RepairApplyServiceImpl implements IRepairApplyService
{
    @Autowired
    private RepairApplyMapper repairApplyMapper;
    @Autowired
    private IRepairRepTbService repTbService;
    @Autowired
    private IRepairRepImgService repImgService;
    @Autowired
    private IRepairProcessService processService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private IRepairReptypeService reptypeService;
    @Autowired
    private TokenService tokenService;

    /**
     * 角色【维修人员】
     */
    private static final Long ROLE_REPAIR = 110L;

    /**
     * 查询申请单
     *
     * @param applyId 申请单ID
     * @return 申请单
     */
    @Override
    public RepairApply selectRepairApplyById(Long applyId)
    {
        return repairApplyMapper.selectRepairApplyById(applyId);
    }

    /**
     * 查询申请单列表
     *
     * @param repairApply 申请单
     * @return 申请单
     */
    @Override
    public List<RepairApply> selectRepairApplyList(RepairApply repairApply)
    {
        return repairApplyMapper.selectRepairApplyList(repairApply);
    }

    /**
     * 新增申请单
     *
     * @param repairApply 申请单
     * @param isOrder -1不是自己接单，1维修人员自己接单
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult insertRepairApply(RepairApply repairApply,int isOrder)  {
        // 获取用户信息
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        Long roleName = user.getRoles().get(0).getRoleId();
        // 获取部门id
        RepairReptype reptype = reptypeService.selectRepairReptypeById(repairApply.getApplyType());
        // 设置申请单基础信息
        repairApply.initBaseApply(user,reptype.getRepairDep());
        // 申请单保存
        repairApplyMapper.insertRepairApply(repairApply);
        // 申请单id保存
        Long applyId = repairApply.getApplyId();
        // 生成报修单,设置生成报修单编号
        RepairRepTb repTb = new RepairRepTb();
        repTb.initRepairApply(repairApply,repairApply.getApplyNo(),reptype.getRepairDep());
        if (repairApply.getApplyExpectType() == 1 ||  repairApply.getApplyExpectType() == 2)
        {
            repTb.setRepairMoney(15);//换新、换配件
            System.out.println(56565);
        }
        if (repairApply.getApplyExpectType() == 3 )
        {
            System.out.println(7878);
            repTb.setRepairMoney(25);//维修
        }
        // 维修人员报修自动接单
        if (ROLE_REPAIR.equals(roleName) && isOrder >0) {
            repTb.setRepairState(2);
            RepairProcess process = new RepairProcess();
            repTbService.insertRepairRepTb(repTb);
            //维修过程信息保存
            process.initProcess(repairApply,repTb,user.getUserName(),user.getUserId());
            processService.insertRepairProcess(process);
        }else{
            System.out.println(99995);
            repTbService.insertRepairRepTb(repTb);
        }
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("applyId",applyId);
        ajaxResult.put("repairId",repTb.getRepairId());
        return ajaxResult;
    }

    /**
     * 修改申请单
     *
     * @param repairApply 申请单
     * @return 结果
     */
    @Override
    public int updateRepairApply(RepairApply repairApply)
    {
        return repairApplyMapper.updateRepairApply(repairApply);
    }

    /**
     * 批量删除申请单
     *
     * @param applyIds 需要删除的申请单ID
     * @return 结果
     */
    @Override
    public int deleteRepairApplyByIds(Long[] applyIds)
    {
        return repairApplyMapper.deleteRepairApplyByIds(applyIds);
    }

    /**
     * 删除申请单信息
     *
     * @param applyId 申请单ID
     * @return 结果
     */
    @Override
    public int deleteRepairApplyById(Long applyId)
    {
        return repairApplyMapper.deleteRepairApplyById(applyId);
    }
}
