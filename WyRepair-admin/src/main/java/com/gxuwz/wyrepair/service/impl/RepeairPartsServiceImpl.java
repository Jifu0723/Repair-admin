package com.gxuwz.wyrepair.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gxuwz.wyrepair.mapper.RepeairPartsMapper;
import com.gxuwz.wyrepair.domain.RepeairParts;
import com.gxuwz.wyrepair.service.IRepeairPartsService;

/**
 * 配件信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-09-17
 */
@Service
public class RepeairPartsServiceImpl implements IRepeairPartsService 
{
    @Autowired
    private RepeairPartsMapper repeairPartsMapper;

    /**
     * 查询配件信息
     * 
     * @param partsId 配件信息主键
     * @return 配件信息
     */
    @Override
    public RepeairParts selectRepeairPartsByPartsId(Long partsId)
    {
        return repeairPartsMapper.selectRepeairPartsByPartsId(partsId);
    }

    /**
     * 查询配件信息列表
     * 
     * @param repeairParts 配件信息
     * @return 配件信息
     */
    @Override
    public List<RepeairParts> selectRepeairPartsList(RepeairParts repeairParts)
    {
        return repeairPartsMapper.selectRepeairPartsList(repeairParts);
    }

    /**
     * 新增配件信息
     * 
     * @param repeairParts 配件信息
     * @return 结果
     */
    @Override
    public int insertRepeairParts(RepeairParts repeairParts)
    {
        return repeairPartsMapper.insertRepeairParts(repeairParts);
    }

    /**
     * 修改配件信息
     * 
     * @param repeairParts 配件信息
     * @return 结果
     */
    @Override
    public int updateRepeairParts(RepeairParts repeairParts)
    {
        return repeairPartsMapper.updateRepeairParts(repeairParts);
    }

    /**
     * 批量删除配件信息
     * 
     * @param partsIds 需要删除的配件信息主键
     * @return 结果
     */
    @Override
    public int deleteRepeairPartsByPartsIds(Long[] partsIds)
    {
        return repeairPartsMapper.deleteRepeairPartsByPartsIds(partsIds);
    }

    /**
     * 删除配件信息信息
     * 
     * @param partsId 配件信息主键
     * @return 结果
     */
    @Override
    public int deleteRepeairPartsByPartsId(Long partsId)
    {
        return repeairPartsMapper.deleteRepeairPartsByPartsId(partsId);
    }
}
