package com.way.base.sms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.way.base.common.SmsTemplateEnum;
import com.way.base.common.SmsTypeEnum;
import com.way.base.sms.cache.SmsRedisNameSpace;
import com.way.base.sms.dao.NotifyTempletDao;
import com.way.base.sms.dto.SmsServiceDto;
import com.way.base.sms.entity.NotifyTemplate;
import com.way.common.cache.RedisRootNameSpace;
import com.way.common.constant.Constants;
import com.way.common.http.ApiConnector;
import com.way.common.log.WayLogger;
import com.way.common.redis.CacheService;
import com.way.common.result.ServiceResult;
import com.way.common.util.Validater;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @ClassName: SmsServiceImpl
 * @Description: 发送短信服务类
 * @Author：xinpei.xu
 * @Date：2017年08月19日 11:03
 *
 */
@Service
public class SmsServiceImpl implements SmsService {

	private final static String SPLATFORM = "way";

	@Autowired
	private NotifyTempletDao notifyTempletDao;

	@Value("${sms_url}")
	private String sms_url;

	@Value("${isSend}")
	private boolean isSend;// 默认false:代表发送短信 true:不发送短信

	/**
	 *
	 * @名称 sendMobile
	 * @描述 发送短信服务
	 * @返回类型 ServiceResult
	 *
	 */
	@Override
	public ServiceResult<String> sendLoginMessage(String sendMobile, SmsTemplateEnum smsTemplate) {
		if (StringUtils.isNotBlank(sendMobile) && Validater.isMobileNew(sendMobile)) {
			WayLogger.info("================麦芽普惠信贷新系统登录流程-开始发送短信开始================");
			WayLogger.info("发送短信的手机号:{}", sendMobile);
			String smsCountDownRedisKey = CacheService.generateCacheKey(SmsRedisNameSpace.LOGIN_COUNTDOWN, sendMobile);
			// 判断发送短信时间间隔(默认1分钟)
			if (!CacheService.KeyBase.isExistsKey(smsCountDownRedisKey)) {
				// 生成短信验证码
				String smsCode = generateSmsCode();
				WayLogger.info("麦芽普惠信贷系统,生成短信验证码:{}", smsCode);
				// 短信发送间隔时间已结束，可以继续发送
				ServiceResult<String> result = sendSms(smsCode, sendMobile, smsTemplate);
				if (result.success()) {
					String smsCodeRedisKey = CacheService.generateCacheKey(SmsRedisNameSpace.LOGIN, sendMobile);
					// 设置短信有效时间
					CacheService.StringKey.set(smsCodeRedisKey, smsCode, RedisRootNameSpace.UnitEnum.FIFTEEN_MIN);
					// 设置短信发送间隔时间
					CacheService.StringKey.set(smsCountDownRedisKey, smsCode, RedisRootNameSpace.UnitEnum.ONE_MIN);
				} else {
					return ServiceResult.newFailure(result.getMessage());
				}
			} else {
				return ServiceResult.newFailure("1分钟只能发送一次短信");
			}
		} else {
			return ServiceResult.newFailure("用户名不存在或手机号码格式不正确");
		}
		WayLogger.info("================麦芽普惠信贷新系统登录流程-开始发送短信结束================");
		return ServiceResult.newSuccess("短信验证码已发送成功.");
	}

	/**
	 *
	 * @名称 sendSms
	 * @描述 发送短信
	 * @返回类型 void
	 *
	 */
	public ServiceResult<String> sendSms(String smsCode, String sendMobile, SmsTemplateEnum smsTemplate) {
		if (isSend) {
			WayLogger.access("根据环境配置，不用直接调用短信服务,配置参数：{}", isSend);
			return ServiceResult.newSuccess();
		}
		// 获取短信模板
		NotifyTemplate notifyTemplate = notifyTempletDao.selectByCode(smsTemplate.getCode());
		if (notifyTemplate == null) {
			return ServiceResult.newFailure("短信模板配置异常,模板编码:" + smsTemplate.getCode());
		}
		try {
			String template = notifyTemplate.getContent();
			SmsServiceDto sms = new SmsServiceDto();
			sms.setSMobile(sendMobile);
			sms.setServiceType(SmsTypeEnum.BUSINESS_SMS.getType());
			sms.setSmsID(UUID.randomUUID().toString().replaceAll("-", ""));
			sms.setSmsContent(String.format(template, smsCode));
			sms.setChannelNo(null);
			sms.setSplatform(SPLATFORM);
			sms.setSmsAbstract(SmsTypeEnum.BUSINESS_SMS.getDesc());
			WayLogger.access("[开始]调用短信服务接口,sms：{}", sms.toString());
			String result = ApiConnector.postJson(sms_url, (JSON) JSONObject.toJSON(sms), "UTF-8");
			WayLogger.debug("短信服务返回结果:{}", result);
			if (result == null) {
				return ServiceResult.newFailure("短信服务异常或网络问题,请稍后重试");
			}
			Map<String, Object> smsResult = new HashMap<>();
			smsResult = JSON.parseObject(result);
			String status = String.valueOf(smsResult.get("status"));
			String resMsg = String.valueOf(smsResult.get("resMsg"));
			WayLogger.access("[结束]调用短信服务接口返回,status:{},resMsg:{}", status, resMsg);
			if (!Constants.YES.equals(status)) {
				String errCode = String.valueOf(smsResult.get("errCode"));
				WayLogger.error("短信服务返回发送短息失败,errCode:{},resMsg:{}", errCode, resMsg);
				return ServiceResult.newFailure(resMsg);
			}
		} catch (Exception e) {
			WayLogger.error("调用实时短信服务中心异常,返回结果：{}", e);
			return ServiceResult.newFailure("短信服务异常或网络问题,请稍后重试");
		}
		return ServiceResult.newSuccess();
	}

	/**
	 *
	 * @名称 checkLoginSms
	 * @描述 验证短信服务
	 * @返回类型 ServiceResult<String>
	 *
	 */
	@Override
	public ServiceResult<String> checkLoginSms(String phone, String smsCode) {
		/*String smsCodeRedisKey = CacheService.generateCacheKey(SmsRedisNameSpace.LOGIN, phone);
		String redisSmsCode = CacheService.StringKey.getObject(smsCodeRedisKey, String.class);
		WayLogger.info("登录后台，获取缓存里面的短信验证码smsCode=" + redisSmsCode);
		// 判断缓存里是否有短信验证码，若已失效，删除该验证码对应的失败次数
		if (StringUtils.isBlank(redisSmsCode)) {
			return ServiceResult.newFailure("短信验证码已失效，请重新发送");
		}
		// 短信验证码正确
		if (!redisSmsCode.equals(smsCode)) {
			return ServiceResult.newFailure("短信验证码不正确，请重新填写验证码");
		}*/
		return ServiceResult.newSuccess();
	}

	/**
	 *
	 * @名称 generateSmsCode
	 * @描述 生成短信验证码
	 * @返回类型 String
	 *
	 */
	public String generateSmsCode() {
		StringBuffer smsCode = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			smsCode.append(random.nextInt(10));
		}
		return smsCode.toString();
	}
}
