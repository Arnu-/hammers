///*
//#     __
//#    /  |  ____ ___  _
//#   / / | / __//   // / /
//#  /_/`_|/_/  / /_//___/
//#@2021-06-10
//*/
//
//
//package me.arnu.common.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import com.aliyuncs.CommonRequest;
//import com.aliyuncs.CommonResponse;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.exceptions.ServerException;
//import com.aliyuncs.http.MethodType;
//import com.aliyuncs.profile.DefaultProfile;
//import me.arnu.common.config.AliSmsConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 阿里短信发送类
// */
//@Configuration
//public class AliSmsUtils {
//
//    // 注册阿里短信配置
//    @Autowired
//    private AliSmsConfig aliSmsConfig;
//
//    /**
//     * 发送短信验证码
//     *
//     * @param code   6位数验证码
//     * @param mobile 手机号码
//     * @return
//     */
//    public boolean sendSms(String code, String mobile) {
//        Map<String, String> map = new HashMap<>();
//        map.put("RegionId", aliSmsConfig.getRegionId());
//        map.put("PhoneNumbers", mobile);
//        map.put("SignName", aliSmsConfig.getSignName());
//        map.put("TemplateCode", aliSmsConfig.getTemplateCode());
//        map.put("TemplateParam", "{\"code\":\"" + code + "\"}");
//        String result = this.sendSdk(map);
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        return jsonObject.get("Message").equals("OK");
//    }
//
//    /**
//     * 发送短信验证码
//     *
//     * @param map 参数
//     * @return
//     */
//    public boolean sendSms(Map<String, String> map) {
//        String result = this.sendSdk(map);
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        return jsonObject.get("Message").equals("OK");
//    }
//
//    /**
//     * 调用发送短信SDK
//     *
//     * @param map
//     * @return
//     */
//    private String sendSdk(Map<String, String> map) {
//        DefaultProfile profile = DefaultProfile.getProfile(aliSmsConfig.getRegionId(), aliSmsConfig.getAccessKeyId(), aliSmsConfig.getAccessKeySecret());
//        IAcsClient client = new DefaultAcsClient(profile);
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//        if (!map.isEmpty()) {
//            for (Map.Entry<String, String> entity : map.entrySet()) {
//                request.putQueryParameter(entity.getKey(), entity.getValue());
//            }
//        }
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//            return response.getData();
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
