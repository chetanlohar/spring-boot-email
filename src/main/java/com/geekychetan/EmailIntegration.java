package com.geekychetan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class EmailIntegration implements CommandLineRunner {

    @Autowired
    private JavaMailSender sender;

    void send(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("chetanlohar@outlook.com");
        message.setSubject("EmailIntegration test");
        message.setText("This is test automated email message.");
        sender.send(message);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("sending email...");
        send();
        System.out.println("done...");
    }

    public static void main(String[] args) {
        SpringApplication.run(EmailIntegration.class, args);
    }
}
