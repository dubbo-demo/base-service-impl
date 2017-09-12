package com.way.base.sms.entity;

import com.way.common.bean.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 *
 * @ClassName: NotifyTemplet 
 * @Description: 通知模板表
 * @author: xinpei.xu
 * @date: 2017/08/19 16:18
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NotifyTemplate extends BaseInfo {
    /** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = -4110462815712442182L;

	private Long id;

    private String description;

    private String code;

    private String content;

    private Date createtime;

    private Date updatetime;
}