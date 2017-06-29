package de.deutschestheater.welchezukunft;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.deutschestheater.welchezukunft.Subscriber;
import de.deutschestheater.welchezukunft.SubscriberRepository;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/") 
public class MainController {

	
	@Autowired
	private MailSubscriberRepository mailSubscriberRepository;


	@Autowired
    private JavaMailSender javaMailSender;
	
	
	

	@PostMapping(path="/addMailSubscriber") 
	public @ResponseBody String addSubscriber (@RequestParam String email,
			@RequestParam String firstName,
			@RequestParam String lastName) {
		
		MailSubscriber n = new MailSubscriber();
		n.setEmail(email);
		n.setFirstName(firstName);
		n.setLastName(lastName);
		mailSubscriberRepository.save(n);
		
		send(email);
					
		return "Saved";
	}

		
	
	/*@GetMapping(path="/all")
	public @ResponseBody Iterable<JavaMailSenderUser> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}*/
	
	
	
	private void send(String email) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(email);
            helper.setFrom("l.parmakerli@googlemail.com");
            helper.setSubject("Lorem ipsum");
            helper.setText("Lorem ipsum dolor sit amet [...]");
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {}
        javaMailSender.send(mail);
        //return helper;
    }
	
	
	
	
}
