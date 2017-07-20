package de.deutschestheater.welchezukunft;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class RestServices {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
	
	@RequestMapping("/admin/changecontents/")
	public List<Workshop> createUser(@RequestBody Workshop workshop) {
        System.out.println("Creating Workshop with LeiterIn " + workshop.getLeiterIn());
  
        List<Workshop> entities = new ArrayList<Workshop>();
       
        for (int i = 0; i<10; ++i){
        	entities.add(workshop);
        }
        
        return entities;
    }
	
}
