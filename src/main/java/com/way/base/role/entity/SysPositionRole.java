package com.way.base.role.entity;

import com.way.common.bean.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysPositionRole extends BaseInfo {

    private static final long serialVersionUID = -5464928813725784141L;

    private Long id;

    private Long positionId;

    private Long roleId;

    private Date updateTime;

    private Date createTime;

    private String createUser;

}