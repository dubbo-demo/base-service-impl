package com.way.base.sms.dao;

import com.way.base.sms.entity.NotifyTemplate;
import com.way.common.rom.IBaseMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @ClassName: NotifyTempletDao 
 * @Description: 通知模板表DAO
 * @Author：xinpei.xu
 * @Date：2017年08月19日 11:03
 *
 */
@Repository
public interface NotifyTempletDao extends IBaseMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(NotifyTemplate record);

    int insertSelective(NotifyTemplate record);

    NotifyTemplate selectByCode(String code);

    int updateByPrimaryKeySelective(NotifyTemplate record);

    int updateByPrimaryKey(NotifyTemplate record);
}