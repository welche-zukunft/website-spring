package de.deutschestheater.welchezukunft;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Workshops {
	
	
	@Autowired
	private WorkshopsRepository workshopsRepository;
	
	private List<Workshop> workshops = new ArrayList<Workshop>();

	public Workshops(){
		for (int i = 0; i<12 ; i++){
			workshops.add(new Workshop());
		}
	}
	
	@Bean
    public Workshops getWorkshops(){
		return new Workshops();
	}
	
	public Workshop getWorkshop(int id){
		System.out.println("Get Workshop " + id);
		return workshops.get(id);
	}
	
	public void setWorkshop(Workshop workshop){
		System.out.println("Set Workshop " + workshop.getId());
		workshops.set(workshop.getId(), workshop);
		
		workshopsRepository.save(workshop);
		
		System.out.println("Workshop " + workshop.getId() + " has been saved!");
		System.out.println(workshop);
		
	}
    
    
	
}
