package com.way.base.role.service;

import com.way.base.role.dao.SysPositionRoleDao;
import com.way.base.role.dao.SysRoleDao;
import com.way.base.role.dao.SysRolePermissionDao;
import com.way.base.role.dto.*;
import com.way.base.role.entity.SysPositionRole;
import com.way.base.role.entity.SysRole;
import com.way.base.role.entity.SysRolePermission;
import com.way.common.log.WayLogger;
import com.way.common.result.ServiceResult;
import com.way.common.rom.annotation.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: SysRoleServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 罗荣
 * @date 2016年8月30日 下午2:18:09
 *
 */
@Service("sysRoleServiceImpl")
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysRolePermissionDao sysRolePermissionDao;

    @Autowired
    private SysPositionRoleDao sysPositionRoleDao;

    @Override
    public ServiceResult<List<String>> getRolesByPositionId(long positionId) {
        List<String> roles = sysRoleDao.getRolesByPositionId(positionId);
        return ServiceResult.newSuccess(roles);
    }

    @Override
    public ServiceResult<String> updateStatus(long id, Integer status) {
        int line = sysRoleDao.updateStatus(id, status);
        if (line <= 0) {
            WayLogger.error("更新角色状态没有成功！{},{}",id,status);
            return ServiceResult.newFailure("操作失败");
        }
        return ServiceResult.newSuccess();
    }

    @Override
    public ServiceResult<Pagination<SysRoleDto>> queryPageList(SysRoleDto queryDto, Integer index, Integer size) {
        Pagination<SysRole> param = new Pagination<SysRole>(index, size);
        List<SysRole> r = sysRoleDao.queryPageList(queryDto,param);
        List<SysRoleDto> list = new ArrayList<>();
        SysRoleDto e = null;
        for (SysRole sysRole : r) {
            e = new SysRoleDto();
            BeanUtils.copyProperties(sysRole, e);
            list.add(e);
        }
        Pagination<SysRoleDto> result = new Pagination<>(index, size);
        result.setResult(list);
        result.setTotal(param.getTotal());
        return ServiceResult.newSuccess(result);
    }

    @Override
    public ServiceResult<List<RolePermissionTreeDto>> getRolePermissions(long roleId) {
        List<SysRolePermission> list = sysRolePermissionDao.selectByRoleId(roleId);
        RolePermissionTreeDto dto = null;
        List<RolePermissionTreeDto> dtos = new ArrayList<>();
        for (SysRolePermission sysRolePermission : list) {
            dto = new RolePermissionTreeDto();
            dto.setName(sysRolePermission.getPermissionName());
            dto.setId(sysRolePermission.getId());
            dtos.add(dto);
        }
        return ServiceResult.newSuccess(dtos);
    }

    @Override
    public ServiceResult<SysRoleDto> getRole(long roleId) {
        SysRole entity = sysRoleDao.selectByPrimaryKey(roleId);
        SysRoleDto dto = new SysRoleDto();
        BeanUtils.copyProperties(entity, dto);
        return ServiceResult.newSuccess(dto);
    }

    @Override
    public ServiceResult<List<Map<String, Object>>> getRoleSelectedPosition(long roleId) {
        return ServiceResult.newSuccess(sysRoleDao.getRoleSelectedPosition(roleId));
    }

    @Override
    public ServiceResult<Long> save(SysRoleDto dto) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(dto, role);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        role.setState(1);
        sysRoleDao.insert(role);
        return ServiceResult.newSuccess(role.getId());
    }

    @Override
    public ServiceResult<Integer> update(SysRoleDto dto) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(dto, role);
        role.setUpdateDate(new Date());
        sysRoleDao.updateByPrimaryKeySelective(role);
        return ServiceResult.newSuccess();
    }

    @Override
    public ServiceResult<Integer> save(RolePositionDto dto) {

        List<SysPositionRole> temp = new ArrayList<>();
        Long[] ids = dto.getPositionIds();
        // 删除已经存在的
        sysPositionRoleDao.deleteByIds(dto.getRoleId(), ids);

        for (Long id : ids) {
            int count = sysPositionRoleDao.isExist(dto.getRoleId(), id);
            if (count > 0) {
                WayLogger.debug(
                        " sysPositionRoleDao.save 已存在角色岗位 输入参数 roleId【" + dto.getRoleId() + "】positionId【" + id + "】");
                continue;
            }
            SysPositionRole e = new SysPositionRole();
            e.setPositionId(id);
            e.setRoleId(dto.getRoleId());
            e.setCreateTime(new Date());
            e.setUpdateTime(new Date());
            e.setCreateUser(dto.getCreateUser());
            temp.add(e);
        }
        // 保存整个当前选中的。
        Integer count = 0;
        if (temp.size() > 0) {

            count = sysPositionRoleDao.saveList(temp);
        }
        return ServiceResult.newSuccess(count);
    }

    @Override
    public ServiceResult<String> delete(Long roleId) {
        // 删除岗位关联
        sysPositionRoleDao.deleteByRoleId(roleId);
        WayLogger.access("sysPositionRoleDao.deleteByRoleId 删除角色岗位关联成功！【ID:" + roleId + "】");
        // 删除权限关联
        sysRolePermissionDao.deleteByRoleId(roleId);
        WayLogger.access(" sysRolePermissionDao.deleteByRoleId 删除角色权限关联成功！【ID:" + roleId + "】");
        // 删除本身
        sysRoleDao.deleteByPrimaryKey(roleId);
        WayLogger.access(" sysRoleDao.deleteByPrimaryKey 删除角色成功！【ID:" + roleId + "】");

        return ServiceResult.newSuccess();
    }

    @Override
    public ServiceResult<Integer> update(RolePositionDto dto) {
        List<SysPositionRole> temp = new ArrayList<>();

        // 删除原有的岗位角色关联
        sysPositionRoleDao.deleteByRoleId(dto.getRoleId());
        WayLogger.access(" sysPositionRoleDao.deleteByRoleId 删除角色岗位关联成功！【ID:" + dto.getRoleId() + "】");

        // 添加岗位角色的关联
        Long[] ids = dto.getPositionIds();
        for (Long id : ids) {
            SysPositionRole e = new SysPositionRole();
            e.setPositionId(id);
            e.setRoleId(dto.getRoleId());
            e.setCreateTime(new Date());
            e.setUpdateTime(new Date());
            e.setCreateUser(dto.getCreateUser());
            temp.add(e);
        }
        // 保存整个当前选中的。
        Integer count = sysPositionRoleDao.saveList(temp);

        WayLogger.access(" sysPositionRoleDao.saveList 添加角色岗位关联成功！【count:" + count + "】条");

        return ServiceResult.newSuccess(count);
    }

    @Override
    public ServiceResult<List<RolePermissionTreeDto>> getPermissionTree(Long roleId, Long menuId) {
        WayLogger.debug(
                " sysRoleDao.getRoleSelectedPermission 获取角色权限 输入参数 roleId【" + roleId + "】menuId【" + menuId + "】");

        List<Map<String, Object>> list = sysRoleDao.getRoleSelectedPermission(roleId, menuId);

        WayLogger.access(" sysRoleDao.getRoleSelectedPermission 获取角色权限【并查询出来是否被选择了】【" + list + "】");

        List<RolePermissionTreeDto> dtos = new ArrayList<>();
        RolePermissionTreeDto dto = null;
        for (Map<String, Object> map : list) {
            dto = new RolePermissionTreeDto();
            dto.setId((Long) map.get("id"));
            dto.setName((String) map.get("permissionName"));
            dto.setIsSelected(Integer.valueOf(map.get("isSelected").toString()));
            dtos.add(dto);
        }
        return ServiceResult.newSuccess(dtos);
    }

    @Override
    public ServiceResult<Object> insertListSelective(List<RolePermissionSimpleTreeDto> saves,
            List<RolePermissionSimpleTreeDto> removes) {
        WayLogger.debug(
                " SysRoleServiceImpl.insertListSelective 获取角色权限 输入参数 saves【" + saves + "】menuId【" + removes + "】");
        // 添加删除有值才进行
        if (saves.size() > 0) {
            List<SysRolePermission> list = new ArrayList<>();

            for (RolePermissionSimpleTreeDto saveDto : saves) {
                SysRolePermission entity = new SysRolePermission();
                entity.setPermissionId(saveDto.getPermissionId());
                entity.setRoleId(saveDto.getRoleId());
                entity.setUpdateTime(new Date());
                entity.setCreateuser(saveDto.getCreateUser());
                entity.setCreateTime(new Date());
                list.add(entity);
            }
            sysRolePermissionDao.insertListSelective(list);
        }

        for (RolePermissionSimpleTreeDto removeEntity : removes) {
            sysRolePermissionDao.deleteByRolePermission(removeEntity.getRoleId(), removeEntity.getPermissionId());
        }
        return ServiceResult.newSuccess();
    }

    /*
     * (非 Javadoc) <p>Title: isExistRoleCode</p> <p>Description: </p>
     * @param roleCode
     * @return
     * @see com.myph.role.service.SysRoleService#isExistRoleCode(java.lang.String)
     */
    @Override
    public ServiceResult<Boolean> isExistRoleCode(String roleCode,String roleOldCode) {
        SysRole role = sysRoleDao.selectByCode(roleCode,roleOldCode);
        if (null != role) {
            return ServiceResult.newSuccess(false);
        }
        return ServiceResult.newSuccess(true);
    }

    /*
     * (非 Javadoc) <p>Title: isExistRoleName</p> <p>Description: </p>
     * @param roleName
     * @return
     * @see com.myph.role.service.SysRoleService#isExistRoleName(java.lang.String)
     */
    @Override
    public ServiceResult<Boolean> isExistRoleName(String roleName,String roleOldName) {
        SysRole role = sysRoleDao.selectByName(roleName,roleOldName);
        if (null != role) {
            return ServiceResult.newSuccess(false);
        }
        return ServiceResult.newSuccess(true);
    }

    @Override
    public ServiceResult<Integer> checkRoleByPositionId(Long id) {
        Integer result = sysRoleDao.checkRoleByPositionId(id);
        return ServiceResult.newSuccess(result);
    }

    @Override
    public ServiceResult<List<SysRoleDto>> selectRolesByType(Integer roleType) {
        List<SysRoleDto> result = sysRoleDao.selectRolesByType(roleType);
        return ServiceResult.newSuccess(result);
    }

    @Override
    public ServiceResult<Integer> savePositionRoleList(List<SysPositionRoleDto> temp) {
        List<SysPositionRole> param = new ArrayList<SysPositionRole>();
        for(SysPositionRoleDto dto:temp){
            SysPositionRole sysPositionRole = new SysPositionRole();
            BeanUtils.copyProperties(dto, sysPositionRole);
            param.add(sysPositionRole);
        }
        Integer count = sysPositionRoleDao.saveList(param);
        return ServiceResult.newSuccess(count);
    }

    @Override
    public ServiceResult<Integer> delPositionRoleByPositionId(long id) {
        sysPositionRoleDao.delPositionRoleByPositionId(id);
        return ServiceResult.newSuccess();
    }

    @Override
    public ServiceResult<List<SysPositionRoleDto>> getPositionRoleByPositionId(long id) {
        List<SysPositionRoleDto> result =  sysPositionRoleDao.getPositionRoleByPositionId(id);
        return ServiceResult.newSuccess(result);
    }

    @Override
    public ServiceResult<List<String>> getRoleNameByPositionId(long id) {
        List<String> result = sysRoleDao.getRoleNameByPositionId(id);
        return ServiceResult.newSuccess(result);
    }

    @Override
    public ServiceResult<List<Long>> selectRoleIds(Long positionId, Integer roleType) {
        List<Long> result = sysPositionRoleDao.selectRoleIds(positionId,roleType);
        return ServiceResult.newSuccess(result);
    }
}
