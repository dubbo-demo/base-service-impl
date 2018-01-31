package com.way.base.file.dao;

import com.way.base.file.entity.FileInfoEntity;
import com.way.common.rom.IBaseMapper;

/**
 * 功能描述：文件信息Dao
 *
 * @author xinpei.xu
 * @date 2017/08/21 22:09
 */
public interface FileInfoDao extends IBaseMapper {

    /**
     * 根据手机号查出用户头像是否存在
     * @param invitationCode
     * @return
     */
    FileInfoEntity getFileInfoByInvitationCode(String invitationCode);

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

    /**
     * 根据图片ID下载图片信息
     * @param fileId
     * @return
     */
    FileInfoEntity getFileInfoByFileId(String fileId);
}
