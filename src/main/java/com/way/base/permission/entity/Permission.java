/** 
 * Purview.java
 * create on 2011-1-17
 * Copyright 2011-2015 Todaysteel All Rights Reserved.
 */
package com.way.base.permission.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * 
 * @ClassName: Permission 
 * @Description: 权限实体(这里用一句话描述这个类的作用) 
 * @author 罗荣 
 * @date 2016年8月30日 上午10:37:18 
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -8198664666683421314L;
    private Long id;
    private String permissionName;// 权限名称
    private String permissionUrl;// 权限url
    private Long menuId;// 所属菜单
    private String permissionCode;// 权限码
    private Date updateTime;
    private Date createTime;
    private String createUser;

}