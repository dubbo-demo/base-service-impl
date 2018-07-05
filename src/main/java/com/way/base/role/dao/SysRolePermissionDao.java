package com.way.base.role.dao;

import com.myph.common.rom.IBaseMapper;
import com.myph.role.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @ClassName: SysRolePermissionDao
 * @Description: 角色权限DAO(这里用一句话描述这个类的作用)
 * @author 罗荣
 * @date 2016年8月29日 下午5:32:09
 *
 */
public interface SysRolePermissionDao extends IBaseMapper {
    /**
     * 
     * @名称 deleteByPrimaryKey 
     * @描述 删除(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月29日 下午5:32:37
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int deleteByPrimaryKey(Long id);
    /**
     * 
     * @名称 deleteByRolePermission 
     * @描述 通过角色ID与权限ID删除(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月29日 下午5:32:41
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int deleteByRolePermission(@Param(value = "roleId") Long roleId, @Param(value = "permissionId") Long permissionId);
    /**
     * 
     * @名称 deleteByRoleId 
     * @描述 删除(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月29日 下午5:32:46
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int deleteByRoleId(Long roleId);
    /**
     * 
     * @名称 insert 
     * @描述 插入(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月29日 下午5:32:50
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int insert(SysRolePermission record);
    /**
     * 
     * @名称 insertSelective 
     * @描述 插入(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月29日 下午5:32:55
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int insertSelective(SysRolePermission record);
    /**
     * 
     * @名称 selectByPrimaryKey 
     * @描述 获取实体(这里用一句话描述这个方法的作用) 
     * @返回类型 SysRolePermission     
     * @日期 2016年8月29日 下午5:33:00
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    SysRolePermission selectByPrimaryKey(Long id);
    /**
     * 
     * @名称 selectByRoleId 
     * @描述 获取实体(这里用一句话描述这个方法的作用) 
     * @返回类型 List<SysRolePermission>     
     * @日期 2016年8月29日 下午5:33:07
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    List<SysRolePermission> selectByRoleId(Long selectByRoleId);
    /**
     * 
     * @名称 updateByPrimaryKeySelective 
     * @描述 更新(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月29日 下午5:33:12
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int updateByPrimaryKeySelective(SysRolePermission record);
    /**
     * 
     * @名称 updateByPrimaryKey 
     * @描述 更新(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月29日 下午5:33:17
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int updateByPrimaryKey(SysRolePermission record);
    /**
     * 
     * @名称 insertListSelective 
     * @描述 批量插入(这里用一句话描述这个方法的作用) 
     * @返回类型 Integer     
     * @日期 2016年8月29日 下午5:33:21
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    Integer insertListSelective(List<SysRolePermission> list);
}