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


			try (BufferedReader br = new BufferedReader(new FileReader( "postcode-outcodes.csv"))) {
				String line;
				while ((line = br.readLine()) != null) {
					log.info("store line into repository = "+line);
					String[] values = line.split(",");
					repository.save(new Postcode(values[0],values[1],values[2]));
				}
			}

			// verify all postcodes
			log.info("Postcodes found with findAll():");
			log.info("-------------------------------");
			repository.findAll().forEach(postcode -> {
				log.info(postcode.toString());
			});
			log.info("");



			log.info("--load repo");
		};
	}

}
