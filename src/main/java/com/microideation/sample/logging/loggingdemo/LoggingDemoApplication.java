package com.microideation.sample.logging.loggingdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class LoggingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoggingDemoApplication.class, args);
	}


	@Service
	class GreetService {

		private Logger log = LoggerFactory.getLogger(GreetService.class);

		public String greet(String name) {

			log.info("greetService -> greet -> Received : name " + name);
			if ( name.equals("test")) {
				log.warn("greetService -> greet -> No name specified");
			}
			String upperName = name.toUpperCase();
			log.debug("greetService -> Name converted to upperCase: " + upperName);
			String response = "Hello!, " + upperName;
			log.debug("greetService -> greet -> Sending response : " + response);
			return response;

		}
	}

	@RestController
	class GreetController {

		private Logger log = LoggerFactory.getLogger(GreetService.class);

		private final GreetService greetService;

		GreetController(GreetService greetService) {
			this.greetService = greetService;
		}

		@GetMapping("/greet/{name}")
		public String greet(@PathVariable String name, HttpServletRequest request) {
			log.info("greetController -> greet -> Received : name " + name);
			log.debug("greetController -> greet -> Received request IP: " + request.getRemoteAddr());
			return greetService.greet(name);
		}
	}
}
