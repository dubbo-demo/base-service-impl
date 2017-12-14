package com.way.base.beeCloud.service;

import com.way.base.beeCloud.dao.BeeCloudMessageDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BeeCloud回调信息ServiceImpl
 */
@Service
public class BeeCloudMessageDetailServiceImpl implements BeeCloudMessageDetailService {

    @Autowired
    private BeeCloudMessageDetailDao beeCloudMessageDetailDao;
}
