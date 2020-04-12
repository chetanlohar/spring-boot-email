package com.geekychetan;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class EmailIntegration implements CommandLineRunner {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration fmConfiguration;

    void send() throws IOException, TemplateException, MessagingException {
        Map<String, String > model = new HashMap<>();
        model.put("firstName","Chetan");
        model.put("lastName","Lohar");

        String content = FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-template.txt"),model);

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo("chetan.lohar@enterprisedb.com");
        helper.setSubject("Email Integration Test");
        helper.setText(content,true);

        Resource resource = new ClassPathResource("docker.jpeg");
        File file = new File(resource.getURI());

        helper.addAttachment("docker.jpeg",file);
        sender.send(helper.getMimeMessage());
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
