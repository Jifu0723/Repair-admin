package com.gxuwz.wyrepair.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gxuwz.wyrepair.domain.CdWxMsgVo;
import com.gxuwz.wyrepair.domain.TemplateDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 微信小程序消息推送工具
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class WxMsgUtil {

    private static final String ACCESS_TOKEN_KEY = "accessToken";

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<String, String> string;

    /** AppID(小程序ID) */
    public final static String APP_ID = "wx4503f571afd62b99";
    /** 密钥 */
    public final static String SECURITY = "8a5f732646eff7df31363357994a1714";
    /** 字符集 */
    private final static String GRANT_TYPE = "authorization_code";
    /** 微信小程序首页面 */
    private final static String WX_INDEX_PAGE = "pages/repair/repairIndex";
    /** 消息模板id */
    private final static String TEMPLATE_ID = "CVfEzOzQjnxwbYNs-raTR8dRI2OxfRahoCuI-HV7R6M";
    /** 维护一个本类的静态变量 */
    private static WxMsgUtil wxMsgUtil;



    /**
     * 静态方法调用@Autowired和@Resource注解对象的解决方法：
     * 执行顺序Constructor（构造方法）—> @Autowired（依赖注入）—> @PostConstruct（注释的方法）
     * 通过使用本类中维护的静态变量来使用redisTemplate和string
     */
    @PostConstruct
    public void init() {
        wxMsgUtil = this;
    }

    /**
     * @param code 登录时获取的code
     */
    public static String getWxOpenId(String code) {
        String param = "appid=" + APP_ID
                + "&secret=" + SECURITY
                + "&js_code=" + code
                + "&grant_type=" + GRANT_TYPE;
        String s = HttpUtils.sendGet("https://api.weixin.qq.com/sns/jscode2session", param);
        JSONObject res = JSONObject.parseObject(s);
        return res.getString("openid");
    }

    /**
     * 获取小程序全局唯一后台接口调用凭据（access_token）
     *
     */
    public static String getAccessToken() {
        if (Boolean.TRUE.equals(wxMsgUtil.redisTemplate.hasKey(ACCESS_TOKEN_KEY))) {
            return wxMsgUtil.string.get(ACCESS_TOKEN_KEY);
        } else {
            String param = "grant_type=client_credential"
                    + "&appid=" + APP_ID
                    + "&secret=" + SECURITY;
            String s = HttpUtils.sendGet("https://api.weixin.qq.com/cgi-bin/token", param);
            JSONObject myJson = JSONObject.parseObject(s);
            String accessTokenVal = myJson.get("access_token").toString();
            wxMsgUtil.string.set(ACCESS_TOKEN_KEY, accessTokenVal);
            //设置过期时间,微信官方文档有规定过期时间,这里设置提前一点
            wxMsgUtil.redisTemplate.expire(ACCESS_TOKEN_KEY, 110, TimeUnit.MINUTES);
            return accessTokenVal;
        }
    }

    /** ================================================================================================================
     * 模板消息发送
     * 示例：
     * Map<String, Object> map = new HashMap<>(4);
     * // 业务类型
     * map.put("thing1",new TemplateDataVo("社保卡申请"));
     * // 当前进度
     * map.put("thing2",new TemplateDataVo("初审通过"));
     * // 申请时间
     * map.put("date4", new TemplateDataVo(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm").format(LocalDateTime.now())));
     * // 提示说明
     * map.put("thing5",new TemplateDataVo("二审时间为5-7个工作日"));
     * =================================================================================================================
     */

    public static void pushOneUser(String wxId, String repairType,
                                   String repairAddress,String repairName,String repairContent,String phonenumber,String page) {
        // 获取请求token
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
        CdWxMsgVo wxMsgVo = new CdWxMsgVo();
        wxMsgVo.setTouser(wxId);
        wxMsgVo.setTemplate_id(TEMPLATE_ID);
        wxMsgVo.setPage(page);

        Map<String, Object> map = new HashMap<>(4);
        // 报修类型
        map.put("thing1",new TemplateDataVo(repairType));
        // 报修地址
        map.put("thing5",new TemplateDataVo(repairAddress));
        // 故障申报人
        map.put("name6",new TemplateDataVo(repairName));
        // 故障描述
        map.put("thing8",new TemplateDataVo(repairContent));
        // 联系人电话
        map.put("phone_number7",new TemplateDataVo(phonenumber));

        wxMsgVo.setData(map);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMsgVo, String.class);
        JSONObject myJson = JSONObject.parseObject(responseEntity.getBody());
        if (!"ok".equals(myJson.get("errmsg").toString())) {
            System.out.println("=======================================================");
            System.out.println(myJson + "wxId: " + wxId);
            System.out.println("=======================================================");
        }
    }

}
