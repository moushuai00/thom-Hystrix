package com.thom.hystrix.service;

import com.thom.hystrix.common.IWeiXinUrl;
import com.thom.hystrix.dto.MenuCreate;
import com.thom.hystrix.vo.menu.Button;
import com.thom.hystrix.vo.menu.CommonButton;
import com.thom.hystrix.vo.menu.ComplexButton;
import com.thom.hystrix.vo.menu.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by ThinkPad on 2016/11/5.
 */
@Service("wechatMenuService")
public class WechatMenuService {

    private static Logger logger = LoggerFactory.getLogger(WechatMenuService.class);

    @Resource
    private WechatTokenService wechatTokenService;

    @Qualifier("restTemplate")
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private HttpServletRequest request;

    /**
     * 创建菜单
     *
     * @return 是否创建成功
     * @throws IOException
     */
    public boolean create() throws IOException {
        String accessToken = wechatTokenService.getAccessToken();

        logger.info("Token {}", accessToken);

        String uri = String.format(IWeiXinUrl.menu_create, accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Menu> entity = new HttpEntity<Menu>(getMenu(), headers);

        ResponseEntity<MenuCreate> response = restTemplate
                .exchange(uri, HttpMethod.POST, entity, MenuCreate.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            MenuCreate body = response.getBody();
            if (body != null && "0".equals(body.getErrcode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("天气预报");
        btn11.setType("view");
        btn11.setKey("11");
        btn11.setUrl("http://1.thom.applinzi.com/appointment/fetch");


        CommonButton btn21 = new CommonButton();
        btn21.setName("歌曲点播");
        btn21.setType("view");
        btn21.setKey("21");
        btn21.setUrl("http://1.thom.applinzi.com/appointment/fetch");

        CommonButton btn31 = new CommonButton();
        btn31.setName("Q友圈");
        btn31.setType("view");
        btn31.setKey("31");
        btn31.setUrl("http://1.thom.applinzi.com/appointment/fetch");

        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
         */
        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("生活助手");
        //一级下有4个子菜单
        mainBtn1.setSub_button(new CommonButton[]{btn11});


        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("休闲驿站");
        mainBtn2.setSub_button(new CommonButton[]{btn21});


        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多体验");
        mainBtn3.setSub_button(new CommonButton[]{btn31});


        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        menu.setButton(new Button[]{mainBtn1, mainBtn2, mainBtn3});

        return menu;
    }
}
