package de.deutschestheater.welchezukunft;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

		for (int i = 0; i < 10; ++i) {
			entities.add(workshop);
		}

		ProcessBuilder pb = new ProcessBuilder("touch", "testtesttest");
		Map<String, String> env = pb.environment();
		env.put("VAR1", "myValue");
		env.remove("OTHERVAR");
		env.put("VAR2", env.get("VAR1") + "suffix");
		pb.directory(new File("/root"));
		File log = new File("log");
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(log));
		Process p;
		try {
			p = pb.start();
			assert pb.redirectInput() == Redirect.PIPE;
			assert pb.redirectOutput().file() == log;
			assert p.getInputStream().read() == -1;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return entities;
	}

}
