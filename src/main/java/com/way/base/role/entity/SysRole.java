package com.way.base.role.entity;

import com.myph.common.bean.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseInfo{
    private static final long serialVersionUID = -2923867569056778465L;
    private Long id;
    private String roleCode;
    private String roleName;
    private Integer state;
    private Date updateDate;
    private Date createDate;
    private String createUser;
    private Integer roleType;//角色类型：0菜单权限角色，1数据权限角色
}