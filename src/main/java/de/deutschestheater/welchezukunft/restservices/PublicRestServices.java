package de.deutschestheater.welchezukunft.restservices;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.deutschestheater.welchezukunft.User;
import de.deutschestheater.welchezukunft.UserRepository;
import de.deutschestheater.welchezukunft.Workshop;
import de.deutschestheater.welchezukunft.WorkshopsManager;
import enumutils.AGB;
import enumutils.Modus;
import enumutils.Status;

@RestController
public class PublicRestServices {

	@Autowired
	private WorkshopsManager workshops;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/adduser/")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		System.out.println("Save new user...");
		
		
		// clean remove if user is already in repository
		
		User oldUser = userRepository.findOne(user.getId());
		Workshop oldWorkshop;
		
		if (oldUser != null) {
			userRepository.delete(oldUser);
			
			switch (user.getStatus()) {
			case ZUGELASSEN : 	
				oldWorkshop = workshops.getWorkshop(oldUser.getWorkshopId());
				oldWorkshop.setBelegt(oldWorkshop.getBelegt() - 1);
				workshops.setWorkshop(oldWorkshop);
				userRepository.delete(oldUser);
				break;
			case ZURÜCKGEMELDET : 	
				oldWorkshop = workshops.getWorkshop(oldUser.getWorkshopId());
				oldWorkshop.setBelegt(oldWorkshop.getBelegt() - 1);
				workshops.setWorkshop(oldWorkshop);
				userRepository.delete(oldUser);
				break;
			default : 
				break;
			}
		
		}

		// Check AGB

		if (!user.getAgb().equals(AGB.YES)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("agb not accepted");
		}

		// Validate email

		System.out.println(user.getMail());
		System.out.println(user.getMailConfirm());

		if (!user.getMail().equals(user.getMailConfirm())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("mail addresses do not match");
		}

		boolean valid = EmailValidator.getInstance().isValid(user.getMail());

		if (!valid) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not a valid mail address");
		}

		// Create an ID

		user.setId((long) (user.getMail().toLowerCase().hashCode()));

		// Set status to pending

		user.setStatus(Status.ANGEMELDET);
		
		// Send Mail

		ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "echo Hallo | mail -s test " + user.getMail());
		Map<String, String> env = pb.environment();
		// env.put("VAR1", "myValue");
		// env.remove("OTHERVAR");
		// env.put("VAR2", env.get("VAR1") + "suffix");
		pb.directory(new File("/root"));
		// File log = new File("log");
		pb.redirectErrorStream(true);
		// pb.redirectOutput(Redirect.appendTo(log));
		Process p;
		try {
			p = pb.start();
			assert pb.redirectInput() == Redirect.PIPE;
			// assert pb.redirectOutput().file() == log;
			assert p.getInputStream().read() == -1;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		//  Check and set Modus
		
		if (user.getWorkshopId() == 14){
			user.setModus(Modus.OLYMPISCH);
			user.setWorkshopId(null);
		} else {
			user.setModus(Modus.NORMAL);
		}
		
		
		//  Set Date
				  
		LocalDateTime now = LocalDateTime.now();
		user.setDatum(java.sql.Timestamp.valueOf(now));
				  		  
		userRepository.save(user);

		return ResponseEntity.status(HttpStatus.OK).body("success");

	}

	@RequestMapping("/confirmregistration/")
	public ResponseEntity<String> confirmRegistration(@RequestBody String mail) {
		System.out.println("User ID from Mail hash " + mail);

		User user = userRepository.findOne((long) mail.hashCode());

		if (user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user did not apply for registration");
		}

		if (user.getModus().equals(Modus.OLYMPISCH)) {
			user.setStatus(Status.ZUZUTEILEN);
		} else if (user.getModus().equals(Modus.NORMAL)) {

			Workshop workshop = workshops.getWorkshop(user.getWorkshopId());

			if (workshop.getMax() == workshop.getBelegt()) {
				user.setStatus(Status.WARTELISTE);
			} else if (workshop.getMax() > workshop.getBelegt()) {
				user.setStatus(Status.ZUGELASSEN);
				
				workshop.setBelegt(workshop.getBelegt() + 1);
			}

		}

		userRepository.save(user);

		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

}
