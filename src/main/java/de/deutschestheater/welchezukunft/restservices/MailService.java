package de.deutschestheater.welchezukunft.restservices;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.time.LocalDateTime;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.deutschestheater.welchezukunft.Mail;
import de.deutschestheater.welchezukunft.MailSubscriberRepository;
import de.deutschestheater.welchezukunft.User;
import de.deutschestheater.welchezukunft.UserRepository;
import de.deutschestheater.welchezukunft.Workshop;
import de.deutschestheater.welchezukunft.WorkshopsManager;
import enumutils.AGB;
import enumutils.Modus;
import enumutils.Status;


@RestController
public class MailService {
	
	@Autowired
	private MailSubscriberRepository mailSubscriberRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private WorkshopsManager workshops;

	@Autowired
	private UserRepository userRepository;


	@RequestMapping("/admin/sendmail/")
	public ResponseEntity<String> confirmRegistration(@RequestBody Mail mail) {
		
		System.out.println("User ID from Mail hash " + mail);

		send(mail);

		return ResponseEntity.status(HttpStatus.OK).body("success");
	}
	
	
	
	private void send(Mail mail) {
		MimeMessage mim = javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mim, true);
			helper.setTo(mail.getZiel());
			helper.setFrom("info@welchezukunft.org");
			helper.setSubject("Lorem ipsum");
			helper.setText("Lorem ipsum dolor sit amet [...]");
			
			FileSystemResource file = new FileSystemResource(new File("/root/uploads/Anhang.txt"));
			
			helper.addAttachment("Anhang.txt", file);
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
		}
		javaMailSender.send(mim);
		// return helper;
	}


}