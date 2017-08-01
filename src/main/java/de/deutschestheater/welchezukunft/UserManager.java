package de.deutschestheater.welchezukunft;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import enumutils.Status;

@Configuration
public class UserManager {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkshopsManager workshops;

	public void deleteUserHandler(long id) {

		User oldUser = userRepository.findOne(id);
		Workshop oldWorkshop;
		
		userRepository.delete(oldUser);
		
		updateWorkshops();

	}

	public void deleteUserHandler(User user) {
		deleteUserHandler(user.getId());
	}

	public void addUser(User user) {
		
		System.out.println("Add user...");


		// Set Date

		LocalDateTime now = LocalDateTime.now();
		user.setDatum(java.sql.Timestamp.valueOf(now));

		userRepository.save(user);
		
		updateWorkshops();
	}

	public void updateUser(User user) {
		
		System.out.println("Update user");
		
		userRepository.save(user);

		updateWorkshops();
		
		Workshop workshop = workshops.getWorkshop(user.getWorkshopId());
			
		if ( (workshop.getBelegt() + workshop.getBlockiert()) > workshop.getMax()) {
				user.setStatus(Status.WARTELISTE);
				
				updateWorkshops();
		}
				
	}

	public User getUser(long id) {

		return userRepository.findOne(id);
	}

	public User getUser(String mail) {

		return getUser((long) mail.hashCode());
	}

	public List<User> getAll(long workshopId) {

		List<User> res = new ArrayList<User>();

		for (User user : userRepository.findAll()) {

			System.out.println("Param      : " + workshopId);
			System.out.println("User WS ID : " + user.getWorkshopId());

			if (user.getWorkshopId().equals(workshopId)) {
				System.out.println("EQUAL!!");
				res.add(user);
			}
		}

		return res;
	}

	public List<User> getAll(Workshop workshop) {

		return getAll(workshop.getId());
	}

	public List<User> getAll() {
		List<User> res = new ArrayList<User>();

		for (User user : userRepository.findAll()) {

			System.out.println("User WS ID : " + user.getWorkshopId());

			res.add(user);

		}

		return res;
	}

	public synchronized void updateWorkshops() {
		
		System.out.println("update Workshops...");
		
		System.out.println("Set Belegung Null");
		for (Workshop workshop : workshops.getWorkshops()) {
			workshop.setBelegt(0);
			workshop.setWarteliste(0);
		}
		
		for (User user : userRepository.findAll()) {

			Workshop workshop = workshops.getWorkshop(user.getWorkshopId());

			switch (user.getStatus()) {

			case ANGEMELDET:
				System.out.println("ANGEMELDET!");
				break;
			case ZUZUTEILEN:
				System.out.println("ZUZUTEILEN!");
				break;
			case WARTELISTE:
				System.out.println("WARTELISTE!");
				workshop.setWarteliste(workshop.getWarteliste() + 1);
				break;
			case ZUGELASSEN:
				System.out.println("ZUGELASSEN!");
				workshop.setBelegt(workshop.getBelegt() + 1);
				break;
			case ZURÜCKGEMELDET:
				System.out.println("ZURÜCKGEMELDET!");
				workshop.setBelegt(workshop.getBelegt() + 1);
			default:
				break;
			}
			
			System.out.print("BELEGT :::::::: :::::::::   " + workshop.getBelegt());

		}
		

	}

}
