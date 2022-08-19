package com.proj.junit5.util;

import org.springframework.stereotype.Component;

//@Component
public class MailSenderAdaptor implements MailSender {

    private final Mail mail;

    public MailSenderAdaptor() {
        mail = new Mail();
    }

    @Override
    public boolean send() {
        return mail.send(); // adapter
    }

    /*@Override
    public boolean send() {
        return false;
    }*/
}
