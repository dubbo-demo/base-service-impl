package com.way.base.permission.service;

import com.way.base.permission.dao.PermissionDao;
import com.way.base.permission.dto.PermissionDto;
import com.way.base.permission.entity.Permission;
import com.way.common.result.ServiceResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: PermissionServiceImpl
 * @Description: 权限管理服务(这里用一句话描述这个类的作用)
 * @author 罗荣
 * @date 2016年8月30日 上午10:37:48
 *
 */
@Service("permissionServiceImpl")
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	/**
	 * 获取菜单拥有的权限
	 */
	@Override
	public ServiceResult<List<PermissionDto>> getPermissionByMenuId(Long id) {
		List<Permission> permissionList = permissionDao.getPermissionByMenuId(id);
		List<PermissionDto> dtoList = new ArrayList<PermissionDto>();
		PermissionDto dto;
		for (Permission permission : permissionList) {
			dto = new PermissionDto();
			BeanUtils.copyProperties(permission, dto);
			dtoList.add(dto);
		}

		return ServiceResult.newSuccess(dtoList);
	}

	/**
	 * 方法名： delete 描述： 删除权限
	 */
	@Override
	public ServiceResult<String> delete(Long id) {
		permissionDao.remove(id);
		return ServiceResult.newSuccess();

	}

	/**
	 * 方法名： get 描述： 根据权限id获取权限
	 */
	@Override
	public ServiceResult<PermissionDto> get(Long id) {
		Permission permission = permissionDao.getEntity(id);
		PermissionDto dto = new PermissionDto();
		if (null != permission) {
			BeanUtils.copyProperties(permission, dto);
		}
		return ServiceResult.newSuccess(dto);

	}

	/**
	 * 方法名： edit 描述： 添加修改权限
	 */
	@Override
	public ServiceResult<String> edit(PermissionDto permissionDto) {
		Permission permission = new Permission();
		BeanUtils.copyProperties(permissionDto, permission);
		permissionDao.update(permission);
		return ServiceResult.newSuccess();

	}

	@Override
	public ServiceResult<List<Long>> getPermissionsByRoleId(List<String> roles) {
		if (CollectionUtils.isEmpty(roles)) {
			return ServiceResult.newSuccess();
		}
		List<Long> permissions = permissionDao.getPermissionsByRoleId(roles);
		return ServiceResult.newSuccess(permissions);
	}

	@Override
	public ServiceResult<List<PermissionDto>> getPermissionByPerId(List<Long> permissions) {
		if (CollectionUtils.isEmpty(permissions)) {
			return ServiceResult.newSuccess();
		}
		List<Permission> permissionCodes = permissionDao.getPermissionByPerId(permissions);
		if (CollectionUtils.isEmpty(permissionCodes)) {
			return ServiceResult.newSuccessMessage("查询数据为空");
		}
		List<PermissionDto> list = new ArrayList<>();
		PermissionDto permissionDto = null;
		for (Permission permission : permissionCodes) {
			permissionDto = new PermissionDto();
			BeanUtils.copyProperties(permission, permissionDto);
			list.add(permissionDto);
		}
		return ServiceResult.newSuccess(list);
	}

	@Override
	public ServiceResult<List<Long>> getMenuIdsByPerId(List<Long> permissions) {
		if (CollectionUtils.isEmpty(permissions)) {
			return ServiceResult.newSuccess();
		}
		List<Long> menuIds = permissionDao.getMenuIdsByPerId(permissions);
		return ServiceResult.newSuccess(menuIds);
	}

	/*
	 * (非 Javadoc) <p>Title: insert</p> <p>Description: </p>
	 * 
	 * @param dto
	 * 
	 * @return
	 * 
	 * @see
	 * com.myph.permission.service.PermissionService#insert(com.myph.permission.
	 * dto.PermissionDto)
	 */
	@Override
	public ServiceResult<Long> insert(PermissionDto dto) {
		Permission permission = new Permission();
		BeanUtils.copyProperties(dto, permission);
		permissionDao.add(permission);
		return ServiceResult.newSuccess();
	}

	/*
	 * (非 Javadoc) <p>Title: deleteByMenuId</p> <p>Description: </p>
	 * 
	 * @param menuId
	 * 
	 * @return
	 * 
	 * @see
	 * com.myph.permission.service.PermissionService#deleteByMenuId(java.lang.
	 * Long)
	 */
	@Override
	public ServiceResult<Long> deleteByMenuId(Long menuId) {
		permissionDao.deleteByMenuId(menuId);
		return ServiceResult.newSuccess();
	}
}
