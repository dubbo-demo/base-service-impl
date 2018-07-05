package com.way.base.role.dao;

import com.myph.common.rom.IBaseMapper;
import com.myph.role.dto.SysPositionRoleDto;
import com.myph.role.entity.SysPositionRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @ClassName: SysPositionRoleDao
 * @Description: 岗位角色关联DAO(这里用一句话描述这个类的作用)
 * @author 罗荣
 * @date 2016年8月29日 下午5:10:07
 *
 */
public interface SysPositionRoleDao extends IBaseMapper {
    /**
     * 
     * @名称 deleteByPrimaryKey
     * @描述 删除(这里用一句话描述这个方法的作用)
     * @返回类型 int
     * @日期 2016年8月29日 下午5:28:49
     * @创建人 罗荣
     * @更新人 罗荣
     *
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 
     * @名称 insert
     * @描述 插入(这里用一句话描述这个方法的作用)
     * @返回类型 int
     * @日期 2016年8月29日 下午5:29:00
     * @创建人 罗荣
     * @更新人 罗荣
     *
     */
    int insert(SysPositionRole record);

    /**
     * 
     * @名称 insertSelective
     * @描述 判空插入(这里用一句话描述这个方法的作用)
     * @返回类型 int
     * @日期 2016年8月29日 下午5:29:05
     * @创建人 罗荣
     * @更新人 罗荣
     *
     */
    int insertSelective(SysPositionRole record);

    /**
     * 
     * @名称 selectByPrimaryKey
     * @描述 获取实体(这里用一句话描述这个方法的作用)
     * @返回类型 SysPositionRole
     * @日期 2016年8月29日 下午5:29:09
     * @创建人 罗荣
     * @更新人 罗荣
     *
     */
    SysPositionRole selectByPrimaryKey(Long id);

    /**
     * 
     * @名称 updateByPrimaryKeySelective
     * @描述 判断空更新(这里用一句话描述这个方法的作用)
     * @返回类型 int
     * @日期 2016年8月29日 下午5:29:16
     * @创建人 罗荣
     * @更新人 罗荣
     *
     */
    int updateByPrimaryKeySelective(SysPositionRole record);

    /**
     * 
     * @名称 updateByPrimaryKey
     * @描述 更新(这里用一句话描述这个方法的作用)
     * @返回类型 int
     * @日期 2016年8月29日 下午5:29:21
     * @创建人 罗荣
     * @更新人 罗荣
     *
     */
    int updateByPrimaryKey(SysPositionRole record);

    /**
     * 
     * @名称 saveList
     * @描述 批量删除(这里用一句话描述这个方法的作用)
     * @返回类型 Integer
     * @日期 2016年8月29日 下午5:29:28
     * @创建人 罗荣
     * @更新人 罗荣
     *
     */
    Integer saveList(List<SysPositionRole> temp);

    /**
     * 
     * @名称 deleteByRoleId
     * @描述 通过角色删除(这里用一句话描述这个方法的作用)
     * @返回类型 Integer
     * @日期 2016年8月29日 下午5:27:50
     * @创建人 罗荣
     * @更新人 罗荣
     *
     */
    Integer deleteByRoleId(Long roleId);
    /**
     * 
     * @名称 isExist 
     * @描述 判断是否已经存在了(这里用一句话描述这个方法的作用) 
     * @返回类型 Integer     
     * @日期 2016年8月31日 下午7:25:13
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    Integer isExist(@Param(value = "roleId") Long roleId, @Param(value = "positionId") Long positionId);
    /**
     * 
     * @名称 deleteByIds 
     * @描述 批量删除(这里用一句话描述这个方法的作用) 
     * @返回类型 Integer     
     * @日期 2016年8月31日 下午8:00:23
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    Integer deleteByIds(@Param(value = "roleId") Long roleId, @Param(value = "ids") Long[] ids);

    Integer deleteByPositionId(@Param(value = "positionId") long positionId);

    List<SysPositionRoleDto> getPositionRoleByPositionId(@Param(value = "positionId") long positionId);

    List<Long> selectRoleIds(@Param(value = "positionId") Long positionId, @Param(value = "roleType") Integer roleType);

    Integer delPositionRoleByPositionId(@Param(value = "positionId") long positionId);
    
    
    
    
    
}