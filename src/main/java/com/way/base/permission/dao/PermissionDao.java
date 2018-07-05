/** 
 * PurviewDao.java
 * create on 2011-1-14
 * Copyright 2011-2015 Todaysteel All Rights Reserved.
 */
package com.way.base.permission.dao;

import com.way.base.permission.entity.Permission;
import com.way.common.rom.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @ClassName: PermissionDao
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 罗荣
 * @date 2016年8月30日 上午10:34:58
 *
 */
@Repository
public interface PermissionDao extends IBaseMapper {
    /**
     * 
     * @名称 getPermissionByMenuId
     * @描述 通过菜单ID获取(这里用一句话描述这个方法的作用)
     * @返回类型 List<Permission>
     * @日期 2016年8月30日 上午10:32:40
     * @创建人 罗荣
     * @更新人 罗荣
     *
     */
    List<Permission> getPermissionByMenuId(@Param(value = "id") Long id);

    Integer remove(Long id);

    void add(Permission permission);

    Permission getEntity(Long id);

    Integer update(Permission permission);

    void deleteByMenuId(Long id);

    List<Long> getPermissionsByRoleId(@Param(value = "roles") List<String> roles);

    List<Permission> getPermissionByPerId(@Param(value = "permissionIds") List<Long> permissionIds);

    List<Long> getMenuIdsByPerId(@Param(value = "permissionIds") List<Long> permissionIds);

}
