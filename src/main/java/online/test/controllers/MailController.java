package online.test.controllers;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import online.test.models.Tests;

import org.springframework.mail.javamail.MimeMessageHelper;

@Controller
public class MailController{
	

@RequestMapping("/sendMail")
@ResponseBody
public String send(String email, String password) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
//        	        	    
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(email);
            helper.setReplyTo("accbootcamp2016@gmail.com");
            helper.setFrom("accbootcamp2016@gmail.com");
            helper.setSubject("Jūsu piekļuve Accenture testam");
            helper.setText("Jūsu piekļuves dati ir: \n\nLietotājvārds: "+email+"\nParole: "+password+"\n\nVeiksmi testā!");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Email could not be sent!";
        } finally {}
        javaMailSender.send(mail);
        return "Email sent!";
        //return helper;
    }
@Autowired
private JavaMailSender javaMailSender;
}