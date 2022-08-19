package com.proj.junit5.util;

import org.springframework.stereotype.Component;

@Component
public class MailSenderStub implements MailSender {

    @Override
    public boolean send() {
        return true;
    }
}
