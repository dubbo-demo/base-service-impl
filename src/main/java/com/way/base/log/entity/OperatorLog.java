package com.way.base.log.entity;

import com.way.common.bean.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OperatorLog extends BaseInfo {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */ 
    private static final long serialVersionUID = -976096502895199806L;

    private Long id;

    private Long userId;

    private String userName;

    private String operatorIp;

    private String operatorDesc;

    private Date operatorTime;

   
}