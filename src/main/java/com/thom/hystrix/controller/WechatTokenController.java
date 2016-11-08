package com.thom.hystrix.controller;

import com.thom.hystrix.dto.Message;
import com.thom.hystrix.lang.PropertyPlaceholder;
import com.thom.hystrix.service.WechatTokenService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * 微信Token
 */
@Controller
@RequestMapping("weixin")
public class WechatTokenController {

    private final Logger logger = LoggerFactory.getLogger(WechatTokenController.class);

    @Resource
    private WechatTokenService wechatTokenService;

    @RequestMapping(value = {"signature"}, method = RequestMethod.GET)
    public void signature(
            @RequestParam(value = "signature", required = true) String signature,
            @RequestParam(value = "timestamp", required = true) String timestamp,
            @RequestParam(value = "nonce", required = true) String nonce,
            @RequestParam(value = "echostr", required = true) String echostr,
            PrintWriter writer) throws IOException {

        boolean result = wechatTokenService.check(PropertyPlaceholder.getProperty("WEIXIN_TOKEN"), signature, timestamp, nonce);

        if (result) {
            writer.print(echostr);
        } else {
            writer.print("error");
        }
        writer.flush();
        writer.close();
    }

    @RequestMapping(value = {"signature"}, method = RequestMethod.POST)
    public void signaturePost(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, JAXBException {

        ServletInputStream inputStream = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        String xml = sb.toString(); //次即为接收到微信端发送过来的xml数据
        logger.info("接收到信息{}", xml);

        Message inputMessage = getMsgEntity(xml);

        String content = inputMessage.getContent();
        if (StringUtils.isNotBlank(content)) {
            content = "我要打10个<a href=\"http://1.thom.applinzi.com/appointment/fetch\">点我</a>";
        } else {
            content = "No CAN NO BB";
        }

        String reply = formatXmlAnswer(inputMessage.getFromUserName(), inputMessage.getToUserName(), content);
        logger.info("返回信息{}", xml);

        OutputStream os = response.getOutputStream();
        os.write(reply.getBytes("utf-8"));
        os.flush();
        os.close();
    }

    /**
     * 封装文字类的返回消息
     *
     * @param to
     * @param from
     * @param content
     * @return
     */
    public String formatXmlAnswer(String to, String from, String content) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(System.currentTimeMillis());
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
        sb.append(content);
        sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
        return sb.toString();
    }

    /**
     * 解析微信xml消息
     *
     * @param strXml
     * @return
     */
    public Message getMsgEntity(String strXml) {
        Message msg = null;
        try {
            if (strXml.length() <= 0 || strXml == null)
                return null;

            // 将字符串转化为XML文档对象
            Document document = DocumentHelper.parseText(strXml);
            // 获得文档的根节点
            Element root = document.getRootElement();
            // 遍历根节点下所有子节点
            Iterator<?> iter = root.elementIterator();

            // 遍历所有结点
            msg = new Message();
            //利用反射机制，调用set方法
            //获取该实体的元类型
            Class<?> c = Class.forName("com.thom.hystrix.dto.Message");
            msg = (Message) c.newInstance();//创建这个实体的对象

            while (iter.hasNext()) {
                Element ele = (Element) iter.next();
                //获取set方法中的参数字段（实体类的属性）
                Field field = c.getDeclaredField(ele.getName());
                //获取set方法，field.getType())获取它的参数数据类型
                Method method = c.getDeclaredMethod("set" + ele.getName(), field.getType());
                //调用set方法
                method.invoke(msg, ele.getText());
            }
        } catch (Exception e) {
            System.out.println("xml 格式异常: " + strXml);
            e.printStackTrace();
        }

        return msg;
    }

}
