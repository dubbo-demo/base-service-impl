package com.way.base.sms.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.way.base.common.SmsTemplateEnum;
import com.way.base.sms.cache.SmsRedisNameSpace;
import com.way.base.sms.dao.NotifyTempletDao;
import com.way.base.sms.dto.AlyunAccessDto;
import com.way.common.cache.RedisRootNameSpace;
import com.way.common.log.WayLogger;
import com.way.common.redis.CacheService;
import com.way.common.result.ServiceResult;
import com.way.common.util.Validater;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

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

	@Autowired
	private AlyunAccessService alyunAccessService;

	@Value("${sms_url}")
	private String sms_url;

	@Value("${isSend}")
	private String isSend;// 默认false:代表发送短信 true:不发送短信

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
			WayLogger.info("================在哪儿系统登录流程-开始发送短信开始================");
			WayLogger.info("发送短信的手机号:{}", sendMobile);
			String smsCountDownRedisKey = CacheService.generateCacheKey(SmsRedisNameSpace.LOGIN_COUNTDOWN, sendMobile);
			// 判断发送短信时间间隔(默认1分钟)
			if (!CacheService.KeyBase.isExistsKey(smsCountDownRedisKey)) {
				// 生成短信验证码
				String smsCode = generateSmsCode();
				WayLogger.info("在哪儿,系统生成短信验证码:{}", smsCode);
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
		WayLogger.info("================在哪儿系统登录流程-开始发送短信结束================");
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
		if ("true".equals(isSend)) {
			WayLogger.access("根据环境配置，不用直接调用短信服务,配置参数：{}", isSend);
			return ServiceResult.newSuccess();
		}
		try {
			// 阿里云短信发送
			SendSmsResponse sendSmsResponse = aliyunSendSms(smsCode, sendMobile, smsTemplate);
			if(sendSmsResponse.getCode() == null || !sendSmsResponse.getCode().equals("OK")) {
				//请求失败
				return ServiceResult.newFailure();
			}
//			SmsServiceDto sms = new SmsServiceDto();
//			sms.setSMobile(sendMobile);
//			sms.setServiceType(SmsTypeEnum.BUSINESS_SMS.getType());
//			sms.setSmsID(UUID.randomUUID().toString().replaceAll("-", ""));
//			sms.setSmsContent(String.format(template, smsCode));
//			sms.setChannelNo(null);
//			sms.setSplatform(SPLATFORM);
//			sms.setSmsAbstract(SmsTypeEnum.BUSINESS_SMS.getDesc());
//			WayLogger.access("[开始]调用短信服务接口,sms：{}", sms.toString());
//			String result = ApiConnector.postJson(sms_url, (JSON) JSONObject.toJSON(sms), "UTF-8");
//			WayLogger.debug("短信服务返回结果:{}", result);
//			if (result == null) {
//				return ServiceResult.newFailure("短信服务异常或网络问题,请稍后重试");
//			}
//			Map<String, Object> smsResult = new HashMap<>();
//			smsResult = JSON.parseObject(result);
//			String status = String.valueOf(smsResult.get("status"));
//			String resMsg = String.valueOf(smsResult.get("resMsg"));
//			WayLogger.access("[结束]调用短信服务接口返回,status:{},resMsg:{}", status, resMsg);
//			if (!Constants.YES.equals(status)) {
//				String errCode = String.valueOf(smsResult.get("errCode"));
//				WayLogger.error("短信服务返回发送短息失败,errCode:{},resMsg:{}", errCode, resMsg);
//				return ServiceResult.newFailure(resMsg);
//			}
		} catch (Exception e) {
			WayLogger.error("调用实时短信服务中心异常,返回结果：{}", e);
			return ServiceResult.newFailure("短信服务异常或网络问题,请稍后重试");
		}
		return ServiceResult.newSuccess();
	}

	/**
	 * 阿里云短信发送
	 * @param smsCode
	 * @param sendMobile
	 * @param smsTemplate
	 */
	public SendSmsResponse aliyunSendSms(String smsCode, String sendMobile, SmsTemplateEnum smsTemplate) throws ClientException {
		// 获取阿里云key
		AlyunAccessDto alyunAccessDto = alyunAccessService.getAlyunAccessKey();
		//设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		//初始化ascClient需要的几个参数
		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		//替换成你的AK
		final String accessKeyId = alyunAccessDto.getAccessKeyId();//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = alyunAccessDto.getAccessKeySecret();//你的accessKeySecret，参考本文档步骤2
		//初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
				accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		//组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		//使用post提交
		request.setMethod(MethodType.POST);
		//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(sendMobile);
		//必填:短信签名-可在短信控制台中找到
		request.setSignName("在哪儿");
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(smsTemplate.getCode());
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam("{\"code\":\""+ smsCode +"\"}");
		//可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");
		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		//request.setOutId("yourOutId");
		//请求失败这里会抛ClientException异常
		return acsClient.getAcsResponse(request);
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
