package de.deutschestheater.welchezukunft;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import enumutils.AGB;
import enumutils.Status;

@RestController
public class RestServices {
	
	@Autowired
	private WorkshopsManager workshops;
	
	@Autowired 
	private UserRepository userRepository;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/admin/changecontents/")
	public Workshop createUser(@RequestBody Workshop workshop) {
		
		System.out.println("Creating Workshop with LeiterIn " + workshop.getLeiterIn());

		workshops.setWorkshop(workshop);	
		

		return workshop;
	}
	
	
	@RequestMapping("/admin/getworkshop/{id}")
	public Workshop getWorkshop( @PathVariable Long id) {
		
		System.out.println("Send back workshop " + id);

		Workshop result = workshops.getWorkshop(id);	
		
		return result;
	}
	
	
	@RequestMapping("/admin/getworkshops/")
	public List<Workshop> getWorkshops() {
		
		System.out.println("Send back workshops List");

		List<Workshop> result = workshops.getWorkshops();	
		
		return result;
	}
	
	
	@RequestMapping("/adduser/")
	public  ResponseEntity<String> addUser(@RequestBody User user){
		System.out.println("Save new user...");
		
		//	Check AGB
		
		if ( !user.getAgb().equals(AGB.YES)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("agb not accepted");
		}
		
		//	Validate email
		
		System.out.println(user.getMail());
		System.out.println(user.getMailConfirm());
		
		if (!user.getMail().equals(user.getMailConfirm())){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("mail addresses do not match");
		}
		
		boolean valid = EmailValidator.getInstance().isValid(user.getMail());
		
		if (!valid){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not a valid mail address");
		}
		
		//	Create an ID
		
		user.setId( (long) (user.getMail().toLowerCase().hashCode()));
		
		//	Set status to pending
		
		user.setStatus(Status.ANGEMELDET);	
		
		ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "echo Hallo | mail -s test " + user.getMail());
		Map<String, String> env = pb.environment();
		//env.put("VAR1", "myValue");
		//env.remove("OTHERVAR");
		//env.put("VAR2", env.get("VAR1") + "suffix");
		pb.directory(new File("/root"));
		//File log = new File("log");
		pb.redirectErrorStream(true);
		//pb.redirectOutput(Redirect.appendTo(log));
		Process p;
		try {
			p = pb.start();
			assert pb.redirectInput() == Redirect.PIPE;
			//assert pb.redirectOutput().file() == log;
			assert p.getInputStream().read() == -1;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		userRepository.save(user);
		
		return ResponseEntity.status(HttpStatus.OK).body("success");
		
	}
	
	
	
	@RequestMapping("/confirmregistration/")
	public  ResponseEntity<String> confirmRegistration(@RequestBody String mail){
		System.out.println("User ID from Mail hash " + mail );
		
		User user = userRepository.findOne((long)mail.hashCode());
		
		if (user == null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user did not apply for registration");
		}
		
		user.setStatus(Status.BESTÄTIGT);
		
		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

}
