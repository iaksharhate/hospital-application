package com.example.util;

import com.example.dto.MailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class EmailService {

    @Autowired
    JavaMailSender javaMail;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value(value = "${spring.mail.username}")
    private String sender;

    public String sendMail(String email, String mailBody){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject("Registration successful!");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText(mailBody);
        javaMail.send(simpleMailMessage);
        return "mail send successfully";
    }

    public void sendHtmlMail(MailRequest mailRequest){

        MimeMessage message = javaMail.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();

            // model.addAttribute("data", mailRequest.getFcrDetail());

//            context.setVariables(mailRequest.getFcrDetailsMap());
//            System.out.println(mailRequest.getFcrDetailsMap());

            context.setVariable( "details", mailRequest.getAppointmentDetails());
            log.info("Appointment Details {}", mailRequest.getAppointmentDetails());

            String html = templateEngine.process("appointment", context);

            helper.setTo(mailRequest.getTo());
            helper.setCc(mailRequest.getCc());
            helper.setFrom(sender);
            helper.setSubject(mailRequest.getSubject());
            helper.setText(html, true);

            javaMail.send(message);

        } catch (MessagingException exception) {
            log.info("Error while sending mail!!");
        }
    }
}
