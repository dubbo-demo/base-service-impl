package com.way.base.sms.service;

import com.way.base.sms.dao.AlyunAccessDao;
import com.way.base.sms.dto.AlyunAccessDto;
import com.way.base.sms.entity.AlyunAccessEntity;
import com.way.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述：阿里云keyServiceImpl
 *
 * @Author：xinpei.xu
 * @Date：2018年01月08日 15:52
 */
@Service
public class AlyunAccessServiceImpl implements AlyunAccessService {

    @Autowired
    private AlyunAccessDao alyunAccessDao;

    /**
     * 获取阿里云key
     * @return
     */
    @Override
    public AlyunAccessDto getAlyunAccessKey() {
        AlyunAccessEntity alyunAccessEntity = alyunAccessDao.getAlyunAccessKey();
        return CommonUtils.transform(alyunAccessEntity, AlyunAccessDto.class);
    }
}
