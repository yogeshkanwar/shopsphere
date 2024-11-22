package com.example.ShopSphere.file;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	default String getFileName(MultipartFile file) {
		return file.getOriginalFilename()
				.replaceAll(" ", "-")
				.replaceAll("[\\\\s\\u2009\\u200A\\u200B\\u2007\\u2008\\u202F]", "-");//other space like characters e.g. (Thin | Hair | Zero Width | Figure | Punctuation) space
	}
	
	default String getPath(String appendPath,String fileName) {
		return "static/"+appendPath+"/"+fileName;
	}
	 
	public String save(MultipartFile file,String appendPath) throws IOException; 
	
	public void delete(String filePath) throws IOException; 

}
