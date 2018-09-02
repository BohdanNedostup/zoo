package zoowebapp.service;


import zoowebapp.mail.Mail;

public interface EmailService {

    void sendMessage(Mail mail);
}
