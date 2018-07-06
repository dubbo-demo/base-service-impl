package com.way.base.menu.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -1736118408350207806L;
    private Long id;
    private String menuName;// 菜单名称
    private String menuIcon;// 菜单图标
    private String menuUrl;// 菜单url
    private String menuCode;// 菜单编号
    private Long parentId;// 上级菜单
    private Integer menuLevel;// 菜单等级
    private Integer orderColumn;// 排列序号
    private Date updateTime;
    private Date createTime;
    private String createUser;

}
