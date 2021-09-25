package com.gxuwz.wyrepair.mapper;

import com.gxuwz.wyrepair.domain.RepeairParts;

import java.util.List;

/**
 * 配件信息Mapper接口
 * 
 * @author ruoyi
 * @date 2021-09-17
 */
public interface RepeairPartsMapper 
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
     * 删除配件信息
     * 
     * @param partsId 配件信息主键
     * @return 结果
     */
    public int deleteRepeairPartsByPartsId(Long partsId);

    /**
     * 批量删除配件信息
     * 
     * @param partsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepeairPartsByPartsIds(Long[] partsIds);
}
