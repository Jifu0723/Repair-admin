package com.gxuwz.wyrepair.service;

import java.util.List;
import com.gxuwz.wyrepair.domain.RepeairParts;

/**
 * 配件信息Service接口
 * 
 * @author ruoyi
 * @date 2021-09-17
 */
public interface IRepeairPartsService 
{
    /**
     * 查询配件信息
     * 
     * @param partsId 配件信息主键
     * @return 配件信息
     */
    public RepeairParts selectRepeairPartsByPartsId(Long partsId);

    /**
     * 查询配件信息列表
     * 
     * @param repeairParts 配件信息
     * @return 配件信息集合
     */
    public List<RepeairParts> selectRepeairPartsList(RepeairParts repeairParts);

    /**
     * 新增配件信息
     * 
     * @param repeairParts 配件信息
     * @return 结果
     */
    public int insertRepeairParts(RepeairParts repeairParts);

    /**
     * 修改配件信息
     * 
     * @param repeairParts 配件信息
     * @return 结果
     */
    public int updateRepeairParts(RepeairParts repeairParts);

    /**
     * 批量删除配件信息
     * 
     * @param partsIds 需要删除的配件信息主键集合
     * @return 结果
     */
    public int deleteRepeairPartsByPartsIds(Long[] partsIds);

    /**
     * 删除配件信息信息
     * 
     * @param partsId 配件信息主键
     * @return 结果
     */
    public int deleteRepeairPartsByPartsId(Long partsId);
}
