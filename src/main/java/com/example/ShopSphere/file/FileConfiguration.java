package com.example.ShopSphere.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FileConfiguration {
	
private Logger logger = LoggerFactory.getLogger(FileConfiguration.class);
	
	@Bean
	@Profile({"local","staging"})
	public FileService localFileService() {
		logger.info("local file service initialized..");
		return new LocalFileService();
	}

}
