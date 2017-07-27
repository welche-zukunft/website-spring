package de.deutschestheater.welchezukunft;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		

		/*
		ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "echo Hallo | mail -s test2 l.parmakerli@googlemail.com");
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
		}*/

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
	public String addUser(@RequestBody User user){
		
		userRepository.save(user);
		
		return "success";
		
	}
	


}
