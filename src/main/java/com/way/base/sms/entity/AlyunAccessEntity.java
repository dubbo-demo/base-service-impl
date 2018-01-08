package com.way.base.sms.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 功能描述：阿里云KeyEntity
 *
 * @Author：xinpei.xu
 * @Date：2018年01月08日 15:51
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AlyunAccessEntity extends BaseEntity {

    private static final long serialVersionUID = -3105552572533687210L;

    private String accessKeyId;
    private String accessKeySecret;
    private String enable; // 是否启用 1:是,2:否
}
