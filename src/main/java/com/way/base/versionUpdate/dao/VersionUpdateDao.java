package com.way.base.versionUpdate.dao;


import com.way.base.versionUpdate.entity.VersionUpdateEntity;

import java.util.List;

/**
 * 功能描述：版本升级Dao
 *
 * @Author：xinpei.xu
 */
public interface VersionUpdateDao {

    /**
     * @Title: findVersionList
     * @Description: 查询版本列表
     * @return: List<VersionUpgrade>
     */
    List<VersionUpdateEntity> findVersionList();
}
