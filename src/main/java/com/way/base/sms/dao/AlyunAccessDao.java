package com.way.base.sms.dao;

import com.way.base.sms.entity.AlyunAccessEntity;
import com.way.common.rom.IBaseMapper;

/**
 * 功能描述：阿里云keyDao
 *
 * @Author：xinpei.xu
 * @Date：2018年01月08日 15:54
 */
public interface AlyunAccessDao extends IBaseMapper {

    /**
     * 获取阿里云key
     * @return
     */
    AlyunAccessEntity getAlyunAccessKey();
}
