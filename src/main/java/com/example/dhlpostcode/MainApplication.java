package com.example.dhlpostcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.FileSystems;


@SpringBootApplication
public class MainApplication {

	private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class);
	}

	@Bean
	public CommandLineRunner dhlPostcodes(PostcodeRepository repository) {
		return (args) -> {
			log.info("++load repo");
			String projectDirectory = FileSystems.getDefault()
					.getPath("")
					.toAbsolutePath()
					.toString();

			int totalLine = 1801375;
			try (BufferedReader br = new BufferedReader(new FileReader( "filtered_ukpostcodes.csv"))) {
				String line;
				int counter = 0;
				while ((line = br.readLine()) != null) {
					counter++;
					log.info("store line into repository {} out of {}",counter,totalLine);
					try {
						String[] values = line.split(",");
						repository.save(new Postcode(values[0], values[1], values[2]));
					}catch(Exception e) {
						//  Block of code to handle errors
						continue;
					}
				}
			}

			// verify all postcodes
			/*log.info("Postcodes found with findAll():");
			log.info("-------------------------------");
			repository.findAll().forEach(postcode -> {
				log.info(postcode.toString());
			});
			log.info("");*/



			log.info("--load repo");
		};
	}

}
