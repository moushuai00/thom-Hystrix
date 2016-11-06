package com.thom.hystrix.controller;

import com.thom.hystrix.dto.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * Created by ThinkPad on 2016/11/6.
 */
@RequestMapping(value = "message")
@Controller
public class WechatMessageController {

    @RequestMapping(value = "reply", method = RequestMethod.POST)
    public void get(String input, PrintWriter writer) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Message.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Message inputMessage = (Message) unmarshaller.unmarshal(new StringReader(input));

        String fromUserName = inputMessage.getFromUserName();
        String toUserName = inputMessage.getToUserName();

        String content = inputMessage.getContent();
        if (StringUtils.isNotBlank(content)) {
            inputMessage.setContent("我要打10个");
        } else {
            inputMessage.setContent("No CAN NO BB");
        }
        writer.write(formatXmlAnswer(toUserName, fromUserName, content));
        writer.flush();
        writer.close();
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
}
