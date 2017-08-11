package de.deutschestheater.welchezukunft;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import java.nio.file.Files;
import java.nio.file.Paths;

import enumutils.Status;

@Configuration
public class EventManager {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private WorkshopsManager workshops;

	@Autowired
	private JavaMailSender javaMailSender;

	public synchronized void deleteEventHandler(int id) {

		Event oldEvent = eventRepository.findOne(id);
		Workshop oldWorkshop;

		eventRepository.delete(oldEvent);


	}

	public synchronized void deleteUserHandler(Event event) {
		deleteEventHandler(event.getId());
	}

	public synchronized void addEvent(Event event) {

		System.out.println("Add event...");

		// Set Date
		eventRepository.save(event);

	}

	public synchronized void updateEvent(Event event) {
		
		System.out.println("Update Event ...");
		eventRepository.save(event);


	}

	public synchronized Event getEvent(int id) {

		return eventRepository.findOne(id);
	}

	public synchronized Event getUser(String mail) {

		return getEvent((int) mail.hashCode());
	}

	public synchronized List<Event> getAll(long workshopId) {

		List<Event> res = new ArrayList<Event>();

		for (Event event : eventRepository.findAll()) {

			System.out.println("Param      : " + workshopId);
			System.out.println("User WS ID : " + event.getWorkshopId());

			if (event.getWorkshopId().equals(workshopId)) {
				System.out.println("EQUAL!!");
				res.add(event);
			}
		}

		return res;
	}

	public synchronized List<Event> getAll(Workshop workshop) {

		return getAll(workshop.getId());
	}

	public synchronized List<Event> getAll() {
		List<Event> res = new ArrayList<Event>();

		for (Event event : eventRepository.findAll()) {

			System.out.println("User WS ID : " + event.getWorkshopId());

			res.add(event);

		}

		return res;
	}


}
