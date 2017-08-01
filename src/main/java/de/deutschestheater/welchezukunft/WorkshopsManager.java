package de.deutschestheater.welchezukunft;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkshopsManager {

	@Autowired
	private WorkshopRepository workshopRepository;

	@Autowired
	private EventRepository eventRepository;

	public WorkshopsManager() {

	}

	public synchronized List<Workshop> getWorkshops() {

		List<Workshop> result = new ArrayList<Workshop>();

		for (Workshop workshop : workshopRepository.findAll()) {
			result.add(workshop);
		}

		if (result.isEmpty()) {

			System.out.println("Init Workshops...");

			for (int i = 0; i < 14; i++) {
				Workshop workshop = new Workshop();
				workshop.setId((long) i);

				workshop.setTitel("Workshop " + workshop.getId());

				this.setWorkshop(workshop);
				result.add(workshop);

			}
		}

		return result;

	}

	public synchronized Workshop getWorkshop(Long id) {
		System.out.println("Get Workshop " + id);
		return workshopRepository.findOne(id);
	}

	public synchronized void setWorkshop(Workshop workshop) {
		System.out.println("Set Workshop " + workshop.getId());

		workshopRepository.save(workshop);
	}

	public synchronized List<Event> getEvents(Workshop workshop) {
		List<Event> events = new ArrayList<Event>();

		for (Event event : eventRepository.findAll()) {
			if (event.getWorkshop().equals(workshop)) {
				events.add(event);
			}
		}

		return events;

	}

	public synchronized boolean isFull(long workshopId) {
		Workshop workshop = workshopRepository.findOne(workshopId);

		boolean res = workshop.getMax() <= (workshop.getBelegt() + workshop.getBlockiert());

		return res;

	}

	public synchronized boolean isFull(Workshop workshop) {
		return isFull(workshop.getId());
	}
	
	
	

}
