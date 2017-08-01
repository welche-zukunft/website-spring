package de.deutschestheater.welchezukunft.restservices;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.deutschestheater.welchezukunft.User;
import de.deutschestheater.welchezukunft.UserManager;
import de.deutschestheater.welchezukunft.WorkshopsManager;
import enumutils.AGB;
import enumutils.Modus;
import enumutils.Status;

@RestController
public class PublicRestServices {

	@Autowired
	private WorkshopsManager workshops;
	
	@Autowired
	private UserManager users;

	
	@RequestMapping("/adduser/")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		System.out.println("Save new user...");
		
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
				
		// clean remove if user is already in repository

		users.deleteUserHandler(user.getId());

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
		
				  		  
		users.addUser(user);

		return ResponseEntity.status(HttpStatus.OK).body("success");

	}
	
	
	
	
	


	@RequestMapping("/confirmregistration/")
	public ResponseEntity<String> confirmRegistration(@RequestBody String mail) {
		System.out.println("User ID from Mail hash " + mail);

		User user = users.getUser(mail);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user did not apply for registration");
		}

		if (user.getModus().equals(Modus.OLYMPISCH)) {
			user.setStatus(Status.ZUZUTEILEN);
		} else if (user.getModus().equals(Modus.NORMAL)) {
			
			
			if (workshops.isFull(user.getWorkshopId())) {
				user.setStatus(Status.WARTELISTE);
			} else {
				user.setStatus(Status.ZUGELASSEN);
			}

		}

		users.updateUser(user);

		return ResponseEntity.status(HttpStatus.OK).body("success");
	}
	
	
	@RequestMapping("/removeuser/")
	public ResponseEntity<String> removeuser(@RequestBody String mail) {
		System.out.println("Save new user...");
		
		// get User ID from mail
		
		Long id = (long) mail.hashCode();
		
		// clean remove 
		
		users.deleteUserHandler(id);    // ToDo  User not there Exception

		return ResponseEntity.status(HttpStatus.OK).body("success");

	} 
	
	
	@RequestMapping("/freeslot/")
	public ResponseEntity<String> freeslot(@RequestBody Long workshopId) {
		System.out.println("Save new user...");
				
		if (workshops.isFull(workshopId)) {
			return ResponseEntity.status(HttpStatus.OK).body("full");

		} else {
			return ResponseEntity.status(HttpStatus.OK).body("free");

		}
		
	}
	
	
	
	

}
