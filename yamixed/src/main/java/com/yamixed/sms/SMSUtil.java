/**
 * 
 */
package com.yamixed.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;

/**
 * @author xiaxue
 * 
 */
public class SMSUtil {

	public static void sendSMS() {
		try {
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
					"", "");
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",
					"sms.aliyuncs.com");
			IAcsClient client = new DefaultAcsClient(profile);
			SingleSendSmsRequest request = new SingleSendSmsRequest();
			request.setSignName("邻答Linda");// 控制台创建的签名名称
			request.setTemplateCode("SMS_58125120");// 控制台创建的模板CODE
			request.setParamString("{\"name\":\"aqua\",\"communityName\":\"邻答的家\"}");// 短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"
			// request.setParamString("{}");
			request.setRecNum("15080315017");// 接收号码
			SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		sendSMS();
	}

}
