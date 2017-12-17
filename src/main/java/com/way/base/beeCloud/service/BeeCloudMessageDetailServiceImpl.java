package com.way.base.beeCloud.service;

import com.way.base.beeCloud.dao.BeeCloudMessageDetailDao;
import com.way.base.beeCloud.dto.BeeCloudMessageDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BeeCloud回调信息ServiceImpl
 */
@Service
public class BeeCloudMessageDetailServiceImpl implements BeeCloudMessageDetailService {

    @Autowired
    private BeeCloudMessageDetailDao beeCloudMessageDetailDao;

    /**
     * 查询BeeCloud回参记录
     * @param message_detail
     * @return
     */
    @Override
    public BeeCloudMessageDetailDto getBeeCloudMessageDetailDto(BeeCloudMessageDetailDto message_detail) {
        return beeCloudMessageDetailDao.getBeeCloudMessageDetailDto(message_detail);
    }

    /**
     * 保存BeeCloud回参信息
     * @param message_detail
     */
    @Override
    public void saveBeeCloudMessageDetailDto(BeeCloudMessageDetailDto message_detail) {
        beeCloudMessageDetailDao.saveBeeCloudMessageDetailDto(message_detail);
    }

    /**
     * 更新BeeCloud回参信息
     * @param message_detail
     */
    @Override
    public void updateFlag(BeeCloudMessageDetailDto message_detail) {
        beeCloudMessageDetailDao.updateFlag(message_detail);
    }
}
