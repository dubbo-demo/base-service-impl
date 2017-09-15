package com.way.base.file.dao;

import com.way.base.file.entity.FileInfoEntity;
import com.way.common.rom.IBaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 功能描述：
 *
 * @Author：xinpei.xu
 * @Date：2017年09月15日 15:30
 */
@Repository
public interface FileInfoDao extends IBaseMapper {

    /**
     * 根据手机号查出用户头像是否存在
     * @param phoneNo
     * @return
     */
    FileInfoEntity getFileInfoByPhoneNo(String phoneNo);

    /**
     * 保存用户头像信息
     * @param entity
     */
    void saveFileInfo(FileInfoEntity entity);

    /**
     * 根据手机号更新用户头像
     * @param entity
     */
    void updateFileInfo(FileInfoEntity entity);
}
