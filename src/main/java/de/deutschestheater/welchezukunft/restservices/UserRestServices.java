package de.deutschestheater.welchezukunft.restservices;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.deutschestheater.welchezukunft.User;
import de.deutschestheater.welchezukunft.UserRepository;
import de.deutschestheater.welchezukunft.Workshop;
import de.deutschestheater.welchezukunft.WorkshopsManager;
import enumutils.AGB;
import enumutils.FILTER;
import enumutils.Modus;
import enumutils.Sprache;
import enumutils.Status;

@RestController
public class UserRestServices {

	@Autowired
	private WorkshopsManager workshops;

	@Autowired
	private UserRepository userRepository;
	
	
	
	

	@RequestMapping(value = { "/admin/getusers/{filter}/", "/admin/getusers/{filter}/{workshopId}" })
	public List<User> getUser(@PathVariable FILTER filter, @PathVariable Optional<Integer> workshopId) {

		System.out.println("get anmeldungen");
		System.out.println(filter);

		List<User> baseList = new ArrayList<User>();

		if (workshopId.isPresent()) {
			
			System.out.println("Workshop ID : " + workshopId );
			
			for (User user : userRepository.findAll()) {
				
				System.out.println( "Param      : " + workshopId.get());
				System.out.println( "User WS ID : " + user.getWorkshopId());
				
				if (user.getWorkshopId().equals(workshopId.get())) {
					System.out.println("EQUAL!!");
					baseList.add(user);
				}
			}
		} else {
			for (User user : userRepository.findAll()) {
				baseList.add(user);
			}
		}

		List<User> users = new ArrayList<User>();
		for (User user : baseList) {

			switch (filter) {
			case WORKSHOP:
				if (user.getStatus().equals(Status.ZUGELASSEN)) {
					users.add(user);
				}
				if (user.getStatus().equals(Status.ZURÜCKGEMELDET)){
					users.add(user);
				}
				break;
			case WARTELISTE:
				if (user.getStatus().equals(Status.WARTELISTE)) {
					users.add(user);
				}
				break;
			case ZURÜCKGEMELDET:
				if (user.getStatus().equals(Status.ZURÜCKGEMELDET)) {
					users.add(user);
				}
				break;
			case NICHTZURÜCKGEMELDET:
				if (user.getStatus().equals(Status.ZUGELASSEN)) {
					users.add(user);

				}
				break;
			case ZUZUTEILEN:
				if (user.getStatus().equals(Status.ZUZUTEILEN)) {
					users.add(user);
				}
				break;
			case ALL:
				users.add(user);
				break;
			default:
				System.out.println("Send back users : " + users);
				break;
			}
		}
		
		
		for (User user: users){
			System.out.println("Status : " + user.getStatus());
		}

		return users;
	}

	@RequestMapping("/admin/changeuser/")
	public ResponseEntity<String> changeUser(@RequestBody User user) {
		System.out.println("change user...");

		// ToDo: Add data validation

		userRepository.save(user);

		return ResponseEntity.status(HttpStatus.OK).body("success");
	}
	
	
	
	@RequestMapping("/admin/deleteuser/")
	public ResponseEntity<String> deleteUser(@RequestBody User user) {
		System.out.println("change user...");

		// ToDo: Add data validation

		userRepository.delete(user);

		return ResponseEntity.status(HttpStatus.OK).body("success");
	}
	
	
	
	
	

}
