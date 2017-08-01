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

		if (oldUser != null) {
			userRepository.delete(oldUser);

			switch (oldUser.getStatus()) {
			case ZUGELASSEN:
				oldWorkshop = workshops.getWorkshop(oldUser.getWorkshopId());
				oldWorkshop.setBelegt(oldWorkshop.getBelegt() - 1);
				workshops.setWorkshop(oldWorkshop);
				break;
			case ZURÜCKGEMELDET:
				oldWorkshop = workshops.getWorkshop(oldUser.getWorkshopId());
				oldWorkshop.setBelegt(oldWorkshop.getBelegt() - 1);
				workshops.setWorkshop(oldWorkshop);
				break;
			default:
				break;
			}

			userRepository.delete(oldUser);

		}
	}

	public void deleteUserHandler(User user) {
		deleteUserHandler(user.getId());
	}

	public void addUser(User user) {

		// Set Date

		LocalDateTime now = LocalDateTime.now();
		user.setDatum(java.sql.Timestamp.valueOf(now));

		userRepository.save(user);
	}
	

	public void updateUser(User user) {
		
		
		User oldUser = userRepository.findOne(user.getId());

		if (oldUser == null) {
			// ToDo Error
		} else {
			
			// update belegte Plätze
			
			if (user.getStatus() == Status.ZUGELASSEN || user.getStatus() == Status.ZURÜCKGEMELDET) {
				Workshop newWorkshop = workshops.getWorkshop(user.getWorkshopId());
				newWorkshop.setBelegt(newWorkshop.getBelegt()+1);
			}
			if (oldUser.getStatus() == Status.ZUGELASSEN || oldUser.getStatus() == Status.ZURÜCKGEMELDET) {
				Workshop oldWorkshop = workshops.getWorkshop(oldUser.getWorkshopId());
				oldWorkshop.setBelegt(oldWorkshop.getBelegt()-1);
			}
		}

		userRepository.save(user);
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

	// ToDo Status angemeldet rauslassen

}
