package de.deutschestheater.welchezukunft.restservices;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.deutschestheater.welchezukunft.AttachmentRepository;
import de.deutschestheater.welchezukunft.Event;
import de.deutschestheater.welchezukunft.EventManager;
import de.deutschestheater.welchezukunft.UserManager;
import de.deutschestheater.welchezukunft.Workshop;
import de.deutschestheater.welchezukunft.storage.StorageFileNotFoundException;
import de.deutschestheater.welchezukunft.storage.StorageService;
import de.deutschestheater.welchezukunft.Attachment;





@RestController
public class EventRestServices {
	
	
	@Autowired 
	EventManager events;

	
	private AttachmentRepository fileRepository;

    private final StorageService storageService;

    @Autowired
    public EventRestServices(StorageService storageService) {
        this.storageService = storageService;
    }

    /*
    @GetMapping("/")
    public @ResponseBody String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "success";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }*/
    
    
    

    @PostMapping("/admin/changeEvent")
    public @ResponseBody String handleFileUpload(@RequestPart("file") Optional<MultipartFile> optFile, @RequestPart("event") Event event, RedirectAttributes redirectAttributes) {
    	
    	if(!optFile.isPresent()) {
    		System.out.println("No file present");
            events.updateEvent(event);
            return "success";
    	}
    	
    	MultipartFile file = optFile.get();
    	    	
    	System.out.println(file.getOriginalFilename());
    	System.out.println(event.getUeberschrift());
    	
    	Attachment attachment = new Attachment();
    	attachment.setName(file.getOriginalFilename());
    	
    	String location = "/var/www/html/uploads/" + event.getUeberschrift();
    	
    	new File(location).mkdirs();
    
        storageService.store(file, Paths.get("/var/www/html/uploads/" + event.getUeberschrift()));
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        
        events.updateEvent(event);

        return "success";
    }
    
    
    
    @RequestMapping("/admin/getevents/{id}")
	public List<Event> getWorkshop(@PathVariable long id) {

		System.out.println("Send back workshop " + id);

		List<Event> result = events.getAll(id);

		return result;
	}
    
    

    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
    
    
    @PostMapping("/admin/getfiles2/")
    public List<Attachment> getFiles() {
    	
    	List<Attachment> result = new ArrayList<Attachment>();
    	
    	for (Attachment file :fileRepository.findAll()) {
    		result.add(file);
    	}
       
        return result;
    }
    

}