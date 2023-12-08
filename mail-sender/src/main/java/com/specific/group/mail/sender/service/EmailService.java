package com.specific.group.mail.sender.service;

import com.specific.group.mail.sender.dto.BaseMessage;
import com.specific.group.mail.sender.dto.MessagePayload;

public interface EmailService {

    void sendMessage(BaseMessage<? extends MessagePayload> message);
}
