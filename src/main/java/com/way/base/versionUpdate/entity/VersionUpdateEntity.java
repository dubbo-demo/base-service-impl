package com.way.base.versionUpdate.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 客户端版本服务<br>
 * 〈功能详细描述〉
 *
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VersionUpdateEntity extends BaseEntity {
	
	private static final long serialVersionUID = -8116334110712751605L;

	// 版本号
	private String versionNo;
	// 终端类型（1：IOS；2：ANDROID
	private Integer terminalType;
	// 是否强制升级（0：否；1：是）
	private Integer mandatory;
	// 说明
	private String comment;
	// 下载地址
	private String downLoadAddr;

}
