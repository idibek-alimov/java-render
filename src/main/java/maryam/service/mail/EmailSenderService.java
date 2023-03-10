package maryam.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    public  void sendMail(String toEmail,String subject,String body){
        System.out.println(1);
        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println(2);
        message.setFrom("alicey.2001@gmail.com");
        System.out.println(3);
        message.setTo(toEmail);
        System.out.println(4);
        message.setSubject(subject);
        System.out.println(5);
        message.setText(body);
        System.out.println(6);
        
        mailSender.send(message);
        System.out.println("mail sent to "+toEmail);
    }
}
