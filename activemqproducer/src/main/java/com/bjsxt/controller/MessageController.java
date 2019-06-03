package com.bjsxt.controller;

import com.bjsxt.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     *
     * @param from 发件人
     * @param to 收件人
     * @param subject 邮件主题
     * @param content 邮件正文
     * @return
     */
    @RequestMapping("/sendMessage")
    public String sendMessage(String from,String to,String subject,String content){
        this.messageService.sendMessage(from,to,subject,content);
        return "/ok.jsp";
    }
}
