package com.pinkdumbell.cocobob.common;


import com.pinkdumbell.cocobob.common.dto.EmailSendResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtilImpl implements EmailUtil {
    @Autowired
    private JavaMailSender sender;
    int status = 404;
    @Override
    public EmailSendResultDto sendEmail(String toAddress, String subject, String body) {


        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(body);
            status = HttpStatus.OK.value();
        } catch (MessagingException e) {
            e.printStackTrace();
            status = HttpStatus.NOT_FOUND.value();

        }
        EmailSendResultDto result = new EmailSendResultDto(status);
        sender.send(message);
        return result;
    }
}