package com.way.base.versionUpdate.service;

import com.way.base.versionUpdate.dao.VersionUpdateDao;
import com.way.base.versionUpdate.dto.VersionUpdateDto;
import com.way.base.versionUpdate.entity.VersionUpdateEntity;
import com.way.common.constant.NumberConstants;
import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: VersionUpdateServiceImpl
 * @Description: 版本升级serviceImpl
 * @author: xinpei.xu
 */
@Service
public class VersionUpdateServiceImpl implements VersionUpdateService {
	
	@Autowired
	private VersionUpdateDao versionUpdateDao;

//	@Override
//	public Map<String, String> versionUpdate(Map<String, String> params) {
//		Map<String, String> map = new HashMap<>();
//
//		String currentVersion = params.get("currentVersion");
//		String endType = params.get("client");
//		String platform = params.get("platform");
//		List<VersionUpgrade> versionList = versionUpgradeDao.findByEndType(Integer.parseInt(endType), currentVersion, platform);
//
//		if (null == versionList || versionList.size() == 0) {
//			map.put("isupdate",  "0");
//			map.put("ismandatory", "0");
//			map.put("comment", "");
//			return map;
//		}
//
//		map.put("isupdate",  "1");
//		map.put("ismandatory", ""+versionList.get(0).getbIsEnUpgrade());
//		map.put("comment", versionList.get(0).getsComment());
//		map.put("appurl", versionList.get(0).getsDownloadAddr());
//		for (VersionUpgrade versionUpgrade : versionList) {
//			if (versionUpgrade.getbIsEnUpgrade() == 1) {// 存在强制升级的版本
//				map.put("ismandatory", "1");
//				return map;
//			}
//		}
//		return map;
//	}
	
	/**
	 * @Title: versionUpdateList
	 * @Description: 版本升级列表查询
	 * @return: Map<String,Object>
	 */
	public ServiceResult<Map<String, Object>> versionUpdateList() {
		ServiceResult<Map<String, Object>> serviceResult = ServiceResult.newSuccess();
		Map<String, Object> map = new HashMap<String, Object>();
		// IOS版本升级列表
		List<VersionUpdateDto> iosConfigs = new ArrayList<VersionUpdateDto>();
		// 安卓版本升级列表
		List<VersionUpdateDto> androidConfigs = new ArrayList<VersionUpdateDto>();
		// 版本升级列表查询
		List<VersionUpdateEntity> versionEntityList = versionUpdateDao.findVersionList();
		List<VersionUpdateDto> versionDtoList = CommonUtils.transformList(versionEntityList, VersionUpdateDto.class);
		for (VersionUpdateDto versionUpdateDto : versionDtoList) {
			if (versionUpdateDto.getTerminalType() == NumberConstants.NUM_ONE) {
				iosConfigs.add(versionUpdateDto);
			} else if (versionUpdateDto.getTerminalType() == NumberConstants.NUM_TWO) {
				androidConfigs.add(versionUpdateDto);
			}
		}
		map.put("ios", iosConfigs);
		map.put("android", androidConfigs);
		serviceResult.setData(map);
		return serviceResult;
	}

}
