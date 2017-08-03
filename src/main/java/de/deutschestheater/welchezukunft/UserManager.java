package de.deutschestheater.welchezukunft;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.nio.file.Files;
import java.nio.file.Paths;

import enumutils.Status;

@Configuration
public class UserManager {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkshopsManager workshops;
	
	@Autowired
	private JavaMailSender javaMailSender;

	public synchronized void deleteUserHandler(long id) {

		User oldUser = userRepository.findOne(id);
		Workshop oldWorkshop;
		
		userRepository.delete(oldUser);
		
		updateWorkshops();

	}

	public synchronized void deleteUserHandler(User user) {
		deleteUserHandler(user.getId());
	}

	public synchronized void addUser(User user) {
		
		System.out.println("Add user...");


		// Set Date

		LocalDateTime now = LocalDateTime.now();
		user.setDatum(java.sql.Timestamp.valueOf(now));

		userRepository.save(user);
		
		updateWorkshops();
	}

	public synchronized void updateUser(User user) {
		
		boolean sendZugeteiltMail = false;
		
		System.out.println("Update user");
		
		if (user.getStatus() == Status.ZUZUTEILEN && user.getWorkshopId() != 0) {
			user.setStatus(Status.ZUGELASSEN);
			
			sendZugeteiltMail = true;
		}
		
		userRepository.save(user);

		updateWorkshops();
		
		Workshop workshop = workshops.getWorkshop(user.getWorkshopId());
			
		if ( (workshop.getBelegt() + workshop.getBlockiert()) > workshop.getMax()) {
				user.setStatus(Status.WARTELISTE);
				
				sendZugeteiltMail = false;
				
				userRepository.save(user);

				updateWorkshops();
				
				String inhalt;
				try {
					String workshopname = workshop.getTitel();
					inhalt = new String(Files.readAllBytes(Paths.get("/uploads/zukunft/mails/warteliste.html")));
					String adresse = user.getMail();
					String betreff = "Warteliste";
					send(adresse, betreff, inhalt);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
		}
		
		
		if (sendZugeteiltMail) {
			
			String inhalt;
			try {
				String workshopname = workshop.getTitel();
				inhalt = new String(Files.readAllBytes(Paths.get("/uploads/zukunft/mails/zugeteilt.html")))
						.replace("REPLACEWORKSHOP", workshopname);
				String adresse = user.getMail();
				String betreff = "Zugeteilt";
				send(adresse, betreff, inhalt);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
				
	}

	public synchronized User getUser(long id) {

		return userRepository.findOne(id);
	}

	public synchronized User getUser(String mail) {

		return getUser((long) mail.hashCode());
	}

	public synchronized List<User> getAll(long workshopId) {

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

	public synchronized List<User> getAll(Workshop workshop) {

		return getAll(workshop.getId());
	}

	public synchronized List<User> getAll() {
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
			workshops.setWorkshop(workshop);

		}
		

	}
	
	
	private void send(String adresse, 
				String betreff,
				String inhalt) {
		MimeMessage mim = javaMailSender.createMimeMessage();
		
		try {
			
			
			
			MimeMessageHelper helper = new MimeMessageHelper(mim, false, "utf-8");
			
			mim.setContent(inhalt, "text/html; charset=utf-8");
			
			helper.setTo(adresse);
			
			helper.setSubject(betreff);
			
			helper.setFrom("info@welchezukunft.org");
			
			javaMailSender.send(mim);
		
			
			//helper.addAttachment("Anhang.txt", file);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		//javaMailSender.send(mim);
		// return helper;
	}
	
	

}
