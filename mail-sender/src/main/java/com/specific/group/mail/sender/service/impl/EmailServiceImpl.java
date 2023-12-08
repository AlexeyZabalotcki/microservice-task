package com.specific.group.mail.sender.service.impl;

import com.specific.group.mail.sender.dto.BaseMessage;
import com.specific.group.mail.sender.dto.MessagePayload;
import com.specific.group.mail.sender.service.EmailService;
import com.specific.group.mail.sender.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.subject}")
    private String subject;

    @Override
    public void sendMessage(BaseMessage<? extends MessagePayload> message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setTo(message.getEmail());
        mailMessage.setText(EmailUtils.getEmailMessage(message).toString());
        mailSender.send(mailMessage);

        log.info("Message {} to {} sent", mailMessage, message.getEmail());
    }
}
