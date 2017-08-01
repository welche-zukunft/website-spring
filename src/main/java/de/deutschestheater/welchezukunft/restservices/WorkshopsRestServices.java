package de.deutschestheater.welchezukunft.restservices;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.deutschestheater.welchezukunft.UserRepository;
import de.deutschestheater.welchezukunft.Workshop;
import de.deutschestheater.welchezukunft.WorkshopsManager;

@RestController
public class WorkshopsRestServices {

	@Autowired
	private WorkshopsManager workshops;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/admin/changeworkshop/")
	public Workshop createUser(@RequestBody Workshop workshop) {

		System.out.println("Creating Workshop with LeiterIn " + workshop.getLeiterIn());

		workshops.setWorkshop(workshop);

		return workshop;
	}

	@RequestMapping("/admin/getworkshop/{id}")
	public Workshop getWorkshop(@PathVariable Long id) {

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
	
	
}
