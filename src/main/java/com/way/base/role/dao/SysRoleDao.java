package com.way.base.role.dao;

import com.way.base.role.dto.SysRoleDto;
import com.way.base.role.entity.SysRole;
import com.way.common.rom.IBaseMapper;
import com.way.common.rom.annotation.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: SysRoleDao 
 * @Description: 角色DAO(这里用一句话描述这个类的作用) 
 * @author 罗荣 
 * @date 2016年8月30日 上午10:38:13 
 *
 */
@Repository
public interface SysRoleDao extends IBaseMapper {
    /**
     * 
     * @名称 deleteByPrimaryKey 
     * @描述 TODO(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月30日 上午10:38:27
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int deleteByPrimaryKey(Long id);
    /**
     * 
     * @名称 insert 
     * @描述 TODO(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月30日 上午10:38:32
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int insert(SysRole record);
    /**
     * 
     * @名称 insertSelective 
     * @描述 TODO(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月30日 上午10:38:36
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int insertSelective(SysRole record);
    /**
     * 
     * @名称 selectByPrimaryKey 
     * @描述 TODO(这里用一句话描述这个方法的作用) 
     * @返回类型 SysRole     
     * @日期 2016年8月30日 上午10:38:41
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    SysRole selectByPrimaryKey(Long id);
    /**
     * 
     * @名称 selectByCode 
     * @描述 通过CODE获取记录(这里用一句话描述这个方法的作用) 
     * @返回类型 SysRole     
     * @日期 2016年8月31日 上午9:07:49
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    SysRole selectByCode(@Param(value = "roleCode") String roleCode, @Param(value = "roleOldCode") String roleOldCode);
    /**
     * 
     * @名称 selectByName 
     * @描述 通过角色名称获取记录(这里用一句话描述这个方法的作用) 
     * @返回类型 SysRole     
     * @日期 2016年8月31日 上午9:07:53
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    SysRole selectByName(@Param(value = "roleName") String roleName, @Param(value = "roleOldName") String roleOldName);
    /**
     * 
     * @名称 updateByPrimaryKeySelective 
     * @描述 TODO(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月30日 上午10:38:46
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int updateByPrimaryKeySelective(SysRole record);
    /**
     * 
     * @名称 updateByPrimaryKey 
     * @描述 TODO(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月30日 上午10:38:50
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int updateByPrimaryKey(SysRole record);
    /**
     * 
     * @名称 queryPageList 
     * @描述 TODO(这里用一句话描述这个方法的作用) 
     * @返回类型 List<SysRole>     
     * @日期 2016年8月30日 上午10:38:58
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    List<SysRole> queryPageList(@Param(value = "queryDto") SysRoleDto queryDto, @Param(value = "page") Pagination<SysRole> pagination);
    /**
     * 
     * @名称 getRolesByPositionId 
     * @描述 TODO(这里用一句话描述这个方法的作用) 
     * @返回类型 List<String>     
     * @日期 2016年8月30日 上午10:39:03
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    List<String> getRolesByPositionId(@Param(value = "positionId") long positionId);
    /**
     * 
     * @名称 updateStatus 
     * @描述 更新状态(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月30日 上午10:39:09
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int updateStatus(@Param(value = "id") Long id, @Param(value = "status") Integer status);
    /**
     * 
     * @名称 getPositionName 
     * @描述 通过角色ID获取岗位名称(这里用一句话描述这个方法的作用) 
     * @返回类型 List<String>     
     * @日期 2016年8月30日 上午10:39:13
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    List<String> getPositionName(@Param(value = "roleId") Long roleId);
    /**
     * 
     * @名称 getRoleSelectedPosition 
     * @描述 获取角色已经分配的岗位(这里用一句话描述这个方法的作用) 
     * @返回类型 List<Map<String,Object>>     
     * @日期 2016年8月30日 上午10:39:17
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    List<Map<String, Object>> getRoleSelectedPosition(@Param(value = "roleId") Long roleId);
    /**
     * 
     * @名称 getRoleSelectedPermission 
     * @描述 获取角色已经分配的权限(这里用一句话描述这个方法的作用) 
     * @返回类型 List<Map<String,Object>>     
     * @日期 2016年8月30日 上午10:39:21
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    List<Map<String, Object>> getRoleSelectedPermission(@Param(value = "roleId") Long roleId,
                                                        @Param(value = "menuId") Long menuId);
    
    Integer checkRoleByPositionId(Long id);
    List<SysRoleDto> selectRolesByType(@Param(value = "roleType") Integer roleType);
    List<String> getRoleNameByPositionId(@Param(value = "positionId") long id);

}