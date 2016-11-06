package com.thom.hystrix.service;

import com.thom.hystrix.common.IWeiXinUrl;
import com.thom.hystrix.dto.AccessToken;
import com.thom.hystrix.lang.PropertyPlaceholder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * token 服务类
 *
 * @author wumou
 */
@Service("wechatTokenService")
public class WechatTokenService {

    @Qualifier("restTemplate")
    @Resource
    private RestTemplate restTemplate;

    /**
     * 验证token
     *
     * @param token     token
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return 是否验证通过
     */
    public boolean check(String token, String signature, String timestamp, String nonce) {
        String[] values = {token, timestamp, nonce};
        Arrays.sort(values); // 字典序排序
        String value = values[0] + values[1] + values[2];
        String sign = DigestUtils.shaHex(value);

        return signature.equals(sign);
    }

    /**
     * 获取accessToken
     *
     * @return accessToken
     */
    public String getAccessToken() {
        String appId = PropertyPlaceholder.getProperty("appId");
        String appSerect = PropertyPlaceholder.getProperty("appSecret");
        String url = String.format(IWeiXinUrl.access_token, appId, appSerect);
        AccessToken accessToken = restTemplate.getForObject(url, AccessToken.class);

        if (accessToken != null && StringUtils.isNotBlank(accessToken.getAccess_token())) {
            return accessToken.getAccess_token();
        }

        return null;
    }
}
