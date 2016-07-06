package online.test.controllers;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.mail.javamail.MimeMessageHelper;

@Controller
public class MailController{
	
	@Autowired
	private JavaMailSender javaMailSender;

	@RequestMapping("/sendMail")
	@ResponseBody
	public boolean send( String email, String password ) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(email);
            helper.setReplyTo("accbootcamp2016@gmail.com");
            helper.setFrom("accbootcamp2016@gmail.com");
            helper.setSubject("Jūsu piekļuve Accenture testam");
            helper.setText("");
        } catch (MessagingException e) {
        	e.printStackTrace();
            return false;
        }
        javaMailSender.send(mail);
        return true;
    }

}