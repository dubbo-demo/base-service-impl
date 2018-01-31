package com.way.base.file.service;

import com.way.base.file.dao.FileInfoDao;
import com.way.base.file.dto.FileInfoDto;
import com.way.base.file.entity.FileInfoEntity;
import com.way.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述：
 *
 * @Author：xinpei.xu
 * @Date：2017/08/21 21:39
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {

    @Autowired
    private FileInfoDao fileInfoDao;

    /**
     * 根据手机号查出用户头像是否存在
     * @param invitationCode
     * @return
     */
    @Override
    public FileInfoDto getFileInfoByInvitationCode(String invitationCode) {
        FileInfoEntity entity = fileInfoDao.getFileInfoByInvitationCode(invitationCode);
        return CommonUtils.transform(entity, FileInfoDto.class);
    }

    /**
     * 保存用户头像信息
     * @param dto
     */
    @Override
    public void saveFileInfo(FileInfoDto dto) {
        FileInfoEntity entity = CommonUtils.transform(dto, FileInfoEntity.class);
        fileInfoDao.saveFileInfo(entity);
    }

    /**
     * 根据手机号更新用户头像
     * @param dto
     */
    @Override
    public void updateFileInfo(FileInfoDto dto) {
        FileInfoEntity entity = CommonUtils.transform(dto, FileInfoEntity.class);
        fileInfoDao.updateFileInfo(entity);
    }

    /**
     * 根据图片ID下载图片信息
     * @param fileId
     * @return
     */
    @Override
    public FileInfoDto getFileInfoByFileId(String fileId) {
        FileInfoEntity entity = fileInfoDao.getFileInfoByFileId(fileId);
        return CommonUtils.transform(entity, FileInfoDto.class);
    }


}
